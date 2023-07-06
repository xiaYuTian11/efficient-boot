package com.efficient.common.util;

import com.efficient.common.result.Result;
import com.efficient.common.result.ResultConstant;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 返回json
 *
 * @author TMW
 * @since 2023/7/6 11:31
 */
public class RenderJson {
    public static void returnJson(HttpServletResponse response, ResultConstant resultEnum) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        out.append(JackSonUtil.toJson(Result.build(resultEnum)));
        out.close();
    }

}
