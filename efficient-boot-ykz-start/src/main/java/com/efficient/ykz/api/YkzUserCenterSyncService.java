package com.efficient.ykz.api;

import com.dcqc.uc.oauth.sdk.model.v3.SynchronizeV3DTO;

/**
 * @author TMW
 * @since 2024/1/15 15:11
 */
public interface YkzUserCenterSyncService {
    void ykzSync(SynchronizeV3DTO synchronizeV3DTO);
}
