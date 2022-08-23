package com.sjr.common.util;

import com.alibaba.ttl.threadpool.TtlExecutors;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * 线程工具类
 *
 * @author TMW
 * @since 2022/4/26 14:27
 */
public class ThreadUtil {

    private static final Logger LOGGER = Logger.getLogger(ThreadUtil.class.getName());
    public static final ExecutorService EXECUTOR_SERVICE = TtlExecutors.getTtlExecutorService(
            MoreExecutors.listeningDecorator(new ThreadPoolExecutor(
                    12, 12, 60, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(),
                    new ThreadFactoryBuilder().setDaemon(true)
                            .setNameFormat("ThreadUtil-%d")
                            .setUncaughtExceptionHandler((t, e) -> {
                                StringWriter sw = new StringWriter();
                                e.printStackTrace(new PrintWriter(sw));
                                LOGGER.info("任务处理异常!" + sw.toString());
                            })
                            .setThreadFactory(Thread::new).build()
            ))
    );
}
