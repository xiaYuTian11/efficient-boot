package com.efficient.system.api;

import com.efficient.common.auth.UserTicket;

/**
 * @author TMW
 * @since 2024/4/25 15:59
 */
public interface YkzLoginService {

    UserTicket getUserTicket(String authCode);

}
