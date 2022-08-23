package com.sjr.common.util;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.sjr.common.entity.UserTicket;

import javax.servlet.http.HttpServletRequest;

/**
 * @author TMW
 * @since 2022/4/26 11:39
 */
public class RequestHolder {

    private static final TransmittableThreadLocal<UserTicket> USER_INFO_THREAD_LOCAL = new TransmittableThreadLocal<>();
    private static final TransmittableThreadLocal<HttpServletRequest> HTTP_SERVLET_REQUEST_THREAD_LOCAL = new TransmittableThreadLocal<>();

    public static void set(UserTicket userTicket) {
        USER_INFO_THREAD_LOCAL.set(userTicket);
    }

    public static void set(HttpServletRequest request) {
        HTTP_SERVLET_REQUEST_THREAD_LOCAL.set(request);
    }

    public static UserTicket getCurrUser() {
        return USER_INFO_THREAD_LOCAL.get();
    }

    public static HttpServletRequest getCurrRequest() {
        return HTTP_SERVLET_REQUEST_THREAD_LOCAL.get();
    }

    public static void remove() {
        USER_INFO_THREAD_LOCAL.remove();
        HTTP_SERVLET_REQUEST_THREAD_LOCAL.remove();
    }
}
