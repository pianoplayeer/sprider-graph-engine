package com.pianoplayeer.spider.graph;

import com.pianoplayeer.spider.common.log.SpiderLogger;
import com.pianoplayeer.spider.graph.config.GraphExecutorConfig;
import com.pianoplayeer.spider.graph.context.SpiderContext;
import com.pianoplayeer.spider.graph.exception.MissingOpFutureException;
import com.pianoplayeer.spider.graph.future.SpiderLazyFuture;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @date 2025/11/29
 * @package com.pianoplayeer.spider.graph
 */
@Getter
@Setter
public class SpiderGraph extends BaseOperator {
	
	private Map<String, BaseOperator> operatorMap = new HashMap<>();
	
	private Map<String, Map<String, Object>> processorConfigs = new HashMap<>();
	
	private Map<String, String> processorTypeMap = new HashMap<>();
	
	private Map<String, String> graph = new HashMap<>();
	
	private List<BaseOperator> entryList = new ArrayList<>();
	
	private List<BaseOperator> endList = new ArrayList<>();
	
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
		
	}
	
	@Override
	public boolean isTimeoutEnable() {
		return false;
	}
	
}
