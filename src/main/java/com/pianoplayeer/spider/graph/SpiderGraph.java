package com.pianoplayeer.spider.graph;

import com.pianoplayeer.spider.graph.context.SpiderContext;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @date 2025/11/29
 * @package com.pianoplayeer.spider.graph
 */
public class SpiderGraph extends BaseOperator {
	
	private Map<String, BaseOperator> operatorMap;
	
	private Map<String, Map<String, Object>> processorConfigs;
	
	private SpiderOperator entry;
	
	@Override
	public void process(SpiderContext context) {
	
	}
}
