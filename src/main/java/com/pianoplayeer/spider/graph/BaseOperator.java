package com.pianoplayeer.spider.graph;

import com.pianoplayeer.spider.graph.context.SpiderContext;

/**
 * @date 2025/11/29
 * @package com.pianoplayeer.spider.graph
 */
public abstract class BaseOperator {
	protected String name;
	
	public abstract void process(SpiderContext context);
}
