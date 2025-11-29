package com.pianoplayeer.spider.graph.executor;

import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @date 2025/11/29
 * @package com.pianoplayeer.spider.graph.executor
 */
public class GraphExecutorConfig {
	
	@Getter
	private static final ExecutorService operatorExecuter =
			new ThreadPoolExecutor(16, 32,
					1, TimeUnit.SECONDS, new SynchronousQueue<>(),
					new ThreadPoolExecutor.CallerRunsPolicy());
}
