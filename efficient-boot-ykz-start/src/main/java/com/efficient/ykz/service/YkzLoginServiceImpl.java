package com.efficient.ykz.service;

import com.efficient.common.auth.UserTicket;
import com.efficient.common.result.Result;
import com.efficient.system.api.YkzLoginService;
import com.efficient.ykz.api.YkzApiService;
import com.efficient.ykz.model.vo.YkzLoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author TMW
 * @since 2024/4/25 16:04
 */
@Service
public class YkzLoginServiceImpl implements YkzLoginService {
    @Autowired
    private YkzApiService ykzApiService;

    @Override
    public UserTicket getUserTicket(String authCode) {
        Result<YkzLoginUser> userInfo = ykzApiService.getUserInfo(authCode);
        if (!Result.isSuccess(userInfo)) {
            return null;
        }
        YkzLoginUser loginUser = userInfo.getData();
        UserTicket userTicket = new UserTicket();
        userTicket.setZwddId(String.valueOf(loginUser.getAccountId()));
        userTicket.setAccount(loginUser.getAccount());
        userTicket.setUsername(loginUser.getLastName());
        return userTicket;
    }
}
