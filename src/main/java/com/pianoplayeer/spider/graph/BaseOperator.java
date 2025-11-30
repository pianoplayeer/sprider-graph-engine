package com.pianoplayeer.spider.graph;

import com.pianoplayeer.spider.common.log.BizLogger;
import com.pianoplayeer.spider.common.log.SpiderLogger;
import com.pianoplayeer.spider.graph.config.GraphExecutorConfig;
import com.pianoplayeer.spider.graph.context.SpiderContext;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @date 2025/11/29
 * @package com.pianoplayeer.spider.graph
 */
public abstract class BaseOperator {
	protected String name;
	
	protected long timeoutMs;
	
	protected Map<BaseOperator, CompletableFuture<?>> opFutureMap;
	
	protected List<BaseOperator> upstreamOps;
	
	protected List<BaseOperator> downstreamOps;
	
	public void process(SpiderContext context) {
		handleUpstream(context);
		var curFuture = CompletableFuture.runAsync(
				() -> {
					StopWatch stopWatch = new StopWatch();
					stopWatch.start();
					
					doProcess(context);
					stopWatch.stop();
					SpiderLogger.info(this, "processor has finished, cost: {} ms", stopWatch.getTotalTimeMillis());
				},
				GraphExecutorConfig.getOperatorExecutor()
		);
		
		if (isTimeoutEnable()) {
			curFuture.orTimeout(timeoutMs, TimeUnit.MILLISECONDS);
		}
		curFuture.exceptionally(throwable -> {
			SpiderLogger.error(this, throwable);
			return null;
		});
		
		opFutureMap.put(this, curFuture);
		handleDownstream(context);
	}
	
	protected void handleUpstream(SpiderContext context) {
		if (!CollectionUtils.isEmpty(upstreamOps)) {
			CompletableFuture.allOf(
					upstreamOps.stream()
							.map(e -> opFutureMap.get(e))
							.toArray(CompletableFuture[]::new)
			);
		}
	}
	
	protected void handleDownstream(SpiderContext context) {
	}
	
	public abstract void doProcess(SpiderContext context);
	
	public abstract boolean isTimeoutEnable();
	
	@Override
	public String toString() {
		String kind = this instanceof SpiderOperator ? "Processor" : "Graph";
		return name + "#" + kind;
	}
}
