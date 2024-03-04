package com.efficient.common.auth;

import com.alibaba.ttl.TransmittableThreadLocal;

import javax.servlet.http.HttpServletRequest;

/**
 * @author TMW
 * @since 2022/4/26 11:39
 */
public class RequestHolder {

    private static final TransmittableThreadLocal<UserTicket> USER_INFO_THREAD_LOCAL = new TransmittableThreadLocal<>();
    private static final TransmittableThreadLocal<HttpServletRequest> HTTP_SERVLET_REQUEST_THREAD_LOCAL = new TransmittableThreadLocal<>();
    private static final TransmittableThreadLocal<String> SYSTEM_ID_THREAD_LOCAL = new TransmittableThreadLocal<>();

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

    public static String getCurrSystemId() {
        return SYSTEM_ID_THREAD_LOCAL.get();
    }

    public static void setCurrSystemId(String systemId) {
        SYSTEM_ID_THREAD_LOCAL.set(systemId);
    }

    public static void remove() {
        USER_INFO_THREAD_LOCAL.remove();
        HTTP_SERVLET_REQUEST_THREAD_LOCAL.remove();
    }
}
