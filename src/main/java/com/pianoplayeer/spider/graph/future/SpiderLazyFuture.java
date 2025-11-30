package com.pianoplayeer.spider.graph.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;

/**
 * @date 2025/11/30
 * @package com.pianoplayeer.spider.graph.future
 */
public class SpiderLazyFuture<T> {
	private final Supplier<T> supplier;
	private final ExecutorService executorService;
	private volatile CompletableFuture<T> future;
	
	public SpiderLazyFuture(Supplier<T> supplier, ExecutorService executorService) {
		this.supplier = supplier;
		this.executorService = executorService;
	}
	
	public CompletableFuture<T> supplyAsync() {
		if (future == null) {
			synchronized (this) {
				if (future == null) {
					future = CompletableFuture.supplyAsync(supplier, executorService);
				}
			}
		}
		return future;
	}
	
	public CompletableFuture<T> get() {
		return future;
	}
}
