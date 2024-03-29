package com.efficient.file.aop;

import com.efficient.common.result.Result;
import com.efficient.file.api.SysFileInfoService;
import com.efficient.file.model.dto.FileBizRelation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

/**
 * 文件关联逻辑保存切面
 *
 * @author TMW
 * @since 2024/3/4 10:41
 */
@Aspect
@Component
@Slf4j
public class AutoSaveFileInfoAspect {
    @Autowired
    private SysFileInfoService fileInfoService;
    @Autowired
    private HttpServletRequest request;

    @Pointcut("@annotation(com.efficient.file.annotation.AutoSaveFileInfo)")
    public void pointCut() {
    }

    @AfterReturning(value = "pointCut()", returning = "resultObject")
    public void doAfterLog(JoinPoint joinPoint, Object resultObject) {
        try {
            if (resultObject instanceof Result) {
                Result result = (Result) resultObject;
                Integer code = result.getCode();
                if (!Objects.equals(code, Result.ok().getCode())) {
                    return;
                }
            }
            // 获取当前请求
            // HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

            // 判断请求是否为POST
            if ("POST".equalsIgnoreCase(request.getMethod())) {
                // 从请求参数中获取 fileIdList 和 bizId
                // 获取请求方法的参数
                Object[] args = joinPoint.getArgs();

                // 如果有参数
                if (args.length > 0) {
                    FileBizRelation firstArg = (FileBizRelation) args[0];

                    // 判断参数是否是预期的类型（这里简单地以 YourRequestObject 为例）
                    if (Objects.nonNull(firstArg)) {
                        // 使用反射获取字段值
                        // String bizId = getField(firstArg, "bizId");
                        String bizId = firstArg.getBizId();
                        // List<String> fileIdList = getField(firstArg, "fileIdList");
                        List<String> fileIdList = firstArg.getFileIdList();

                        // 进一步处理，注意判断空
                        if (bizId != null && fileIdList != null) {
                            // 调用全局接口的逻辑
                            boolean saved = fileInfoService.saveListByBizId(fileIdList, bizId);
                            if (!saved) {
                                log.info("文件关联失败");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.info("文件关联异常", e);
        }
    }

    // 通过反射获取字段值
    private <T> T getField(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Class<?> current = object.getClass();
        while (current != Object.class) {
            try {
                Field field = current.getDeclaredField(fieldName);
                field.setAccessible(true);
                return (T) field.get(object);
            } catch (NoSuchFieldException e) {
                // 如果当前类中没有该字段，继续在父类中查找
                current = current.getSuperclass();
            }
        }
        throw new NoSuchFieldException("The field '" + fieldName + "' was not found in " + object.getClass() + " or its superclasses.");
    }
}
