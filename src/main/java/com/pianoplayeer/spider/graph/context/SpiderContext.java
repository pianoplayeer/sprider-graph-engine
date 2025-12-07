package com.pianoplayeer.spider.graph.context;

import com.pianoplayeer.spider.common.enums.Attr;
import com.pianoplayeer.spider.common.enums.Param;
import com.pianoplayeer.spider.graph.BaseOperator;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @date 2025/11/29
 * @package com.pianoplayeer.spider.graph.context
 */
@Getter
public class SpiderContext {
	private Map<Param, Object> requestParams;
	
	private Map<Attr, Object> attrs;
	protected Map<BaseOperator, CompletableFuture<?>> opFutureMap = new ConcurrentHashMap<>();
	
}
