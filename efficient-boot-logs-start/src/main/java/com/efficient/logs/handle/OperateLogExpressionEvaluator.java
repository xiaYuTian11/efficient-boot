package com.efficient.logs.handle;

import org.springframework.aop.support.AopUtils;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用于计算和缓存SpEL表达式的共享实用程序类
 *
 * @author TMW
 * @since 2024/7/3 17:02
 */
public class OperateLogExpressionEvaluator extends CachedExpressionEvaluator {

    private final Map<ExpressionKey, Expression> keyCache = new ConcurrentHashMap<>(64);
    private Map<ExpressionKey, Expression> expressionCache = new ConcurrentHashMap<>();
    private Map<AnnotatedElementKey, Method> targetMethodCache = new ConcurrentHashMap<>();

    public EvaluationContext createEvaluationContext(Class<?> targetClass, Method method, Object[] args) {
        Method targetMethod = getTargetMethod(targetClass, method);
        return new MethodBasedEvaluationContext(null, targetMethod, args, getParameterNameDiscoverer());
    }

    private Method getTargetMethod(Class<?> targetClass, Method method) {
        AnnotatedElementKey methodKey = new AnnotatedElementKey(method, targetClass);
        Method targetMethod = this.targetMethodCache.get(methodKey);
        if (targetMethod == null) {
            targetMethod = AopUtils.getMostSpecificMethod(method, targetClass);
            this.targetMethodCache.put(methodKey, targetMethod);
        }
        return targetMethod;
    }

    public String parseExpression(EvaluationContext evaluationContext, AnnotatedElementKey methodKey, String conditionExpression) {
        return getExpression(this.expressionCache, methodKey, conditionExpression).getValue(evaluationContext, String.class);
    }

    public Object parseExpression(String expression, AnnotatedElementKey methodKey, EvaluationContext evalContext) {
        return this.getExpression(this.keyCache, methodKey, expression).getValue(evalContext);
    }
}
