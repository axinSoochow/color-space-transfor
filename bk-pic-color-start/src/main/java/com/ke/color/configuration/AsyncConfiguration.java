package com.ke.color.configuration;

import lombok.val;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步任务线程池配置
 *
 * @author keboot
 */
@Configuration
public class AsyncConfiguration extends AsyncConfigurerSupport {
	@Override
	public Executor getAsyncExecutor() {
		val executor = new ThreadPoolTaskExecutor();
		executor.setThreadNamePrefix("AsyncExecutor-");
		executor.setCorePoolSize(20);
		executor.setQueueCapacity(100);
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
		executor.initialize();

		return executor;
	}
}
