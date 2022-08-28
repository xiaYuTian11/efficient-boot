package com.efficient.task.api;

import com.sjr.common.result.Result;

/**
 * @author TMW
 * @since 2022/8/28 21:55
 */
public interface TaskExecuteService {
    Result start(String taskId) throws Exception;

    Result stop(String taskId) throws Exception;

    Result restart(String taskId) throws Exception;

    Result remove(String taskId) throws Exception;
}
