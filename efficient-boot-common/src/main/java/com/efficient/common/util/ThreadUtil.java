package com.efficient.common.util;

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
                                LOGGER.warning("任务处理异常!" + sw);
                            })
                            .setThreadFactory(Thread::new).build(),
                    // 设置拒绝策略为CallerRunsPolicy
                    new ThreadPoolExecutor.CallerRunsPolicy()
            ))
    );

    /**
     * 增加了JVM关闭钩子，确保在应用退出时能尝试优雅地关闭线程池，避免资源泄露
     */
    static {
        Runtime.getRuntime().addShutdownHook(new Thread(ThreadUtil::shutdownThreadPoolGracefully));
    }

    private static void shutdownThreadPoolGracefully() {
        EXECUTOR_SERVICE.shutdown();
        try {
            if (!EXECUTOR_SERVICE.awaitTermination(60, TimeUnit.SECONDS)) {
                EXECUTOR_SERVICE.shutdownNow();
                if (!EXECUTOR_SERVICE.awaitTermination(60, TimeUnit.SECONDS))
                    LOGGER.warning("线程池未优雅关闭");
            }
        } catch (InterruptedException ie) {
            EXECUTOR_SERVICE.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
