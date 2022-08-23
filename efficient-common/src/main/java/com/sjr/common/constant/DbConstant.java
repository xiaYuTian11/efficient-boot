package com.sjr.common.constant;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

/**
 * @author TMW
 * @since 2022/6/9 10:37
 */
public class DbConstant {
    /***
     * DSL 上下文
     * */
    public static final DSLContext DSL_CONTEXT = DSL.using(SQLDialect.POSTGRES);

}
