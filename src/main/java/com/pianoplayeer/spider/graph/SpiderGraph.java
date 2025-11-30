package com.pianoplayeer.spider.graph;

import com.pianoplayeer.spider.common.log.SpiderLogger;
import com.pianoplayeer.spider.graph.config.GraphExecutorConfig;
import com.pianoplayeer.spider.graph.context.SpiderContext;
import com.pianoplayeer.spider.graph.exception.MissingOpFutureException;
import com.pianoplayeer.spider.graph.future.SpiderLazyFuture;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @date 2025/11/29
 * @package com.pianoplayeer.spider.graph
 */
public class SpiderGraph extends BaseOperator {
	
	private Map<String, BaseOperator> operatorMap;
	
	private Map<String, Map<String, Object>> processorConfigs;
	
	private List<BaseOperator> entryList;
	
	private List<BaseOperator> endList;
	
	/**
	 * TODO: use topology sort to execute each operator
	 */
	@Override
	public void doProcess(SpiderContext context) {
		Set<BaseOperator> curExecOps = new HashSet<>(entryList);
		Set<BaseOperator> nextExecOps = new HashSet<>();
		
		while (!curExecOps.isEmpty()) {
			for (BaseOperator op : curExecOps) {
				op.process(context);
				nextExecOps.addAll(op.downstreamOps);
			}
			
			curExecOps = nextExecOps;
			nextExecOps = new HashSet<>();
		}
		
		CompletableFuture.allOf(
				endList.stream().map(e -> opFutureMap.get(e))
						.toArray(CompletableFuture[]::new)
		);
	}
	
	@Override
	public boolean isTimeoutEnable() {
		return false;
	}
	

}
