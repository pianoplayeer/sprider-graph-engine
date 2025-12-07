package com.pianoplayeer.spider.graph;

import com.pianoplayeer.spider.common.log.BizLogger;
import com.pianoplayeer.spider.common.log.SpiderLogger;
import com.pianoplayeer.spider.graph.config.GraphExecutorConfig;
import com.pianoplayeer.spider.graph.context.SpiderContext;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @date 2025/11/29
 * @package com.pianoplayeer.spider.graph
 */
@Getter
@Setter
public abstract class BaseOperator {
	protected String name;
	
	protected long timeoutMs = 10000L;
	
	protected List<BaseOperator> upstreamOps = new ArrayList<>();
	
	protected List<BaseOperator> downstreamOps = new ArrayList<>();
	
	@Resource(name = "operatorExecutorService")
	private ExecutorService operatorExecutorService;
	
	public void process(SpiderContext context) {
		handleUpstream(context);
		
		if (this instanceof SpiderOperator) {
			processSpiderOperator(context);
		} else {
			processSpiderGraph(context);
		}
		
		handleDownstream(context);
	}
	
	protected void processSpiderOperator(SpiderContext context) {
		var curFuture = CompletableFuture.runAsync(
				() -> {
					StopWatch stopWatch = new StopWatch();
					stopWatch.start();
					
					doProcess(context);
					stopWatch.stop();
					SpiderLogger.info(this, "processor has finished, cost: {} ms", stopWatch.getTotalTimeMillis());
				},
				operatorExecutorService
		);
		
		if (isTimeoutEnable()) {
			curFuture.orTimeout(timeoutMs, TimeUnit.MILLISECONDS);
		}
		curFuture.handle((res, throwable) -> {
			if (throwable == null) {
				return null;
			}
			
			if (throwable instanceof TimeoutException) {
				SpiderLogger.warn(this, "operator is timeout", throwable);
			} else if (throwable instanceof CancellationException) {
				SpiderLogger.warn(this, "operator is cancelled", throwable);
			} else {
				SpiderLogger.warn(this, throwable);
			}

			return null;
		});
		
		context.getOpFutureMap().put(this, curFuture);
		
	}
	
	protected void processSpiderGraph(SpiderContext context) {
		doProcess(context);
	}
	
	protected void handleUpstream(SpiderContext context) {
		List<SpiderOperator> operators = new ArrayList<>();
		
		for (BaseOperator op : upstreamOps) {
			if (op instanceof SpiderGraph subGraph) {
				operators.addAll(findEndSpiderOperators(subGraph));
			} else {
				operators.add((SpiderOperator) op);
			}
		}
		
		if (!CollectionUtils.isEmpty(operators)) {
			CompletableFuture.allOf(
					operators.stream()
							.map(e -> context.getOpFutureMap().get(e))
							.toArray(CompletableFuture[]::new)
			);
		}
	}
	
	private List<SpiderOperator> findEndSpiderOperators(SpiderGraph graph) {
		List<SpiderOperator> endOps = new ArrayList<>();
		
		for (BaseOperator op : graph.getEndList()) {
			if (op instanceof SpiderOperator spiderOp) {
				endOps.add(spiderOp);
			} else {
				endOps.addAll(findEndSpiderOperators((SpiderGraph) op));
			}
		}
		
		return endOps;
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
