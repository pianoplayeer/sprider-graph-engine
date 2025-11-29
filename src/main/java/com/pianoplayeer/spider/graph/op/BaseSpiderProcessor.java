package com.pianoplayeer.spider.graph.op;

import com.pianoplayeer.spider.graph.context.SpiderContext;
import jakarta.annotation.PostConstruct;
import lombok.Getter;

/**
 * @date 2025/11/29
 * @package com.pianoplayeer.spider.graph.op
 */
public abstract class BaseSpiderProcessor {
	@Getter
	private String processorName;
	
	@Getter
	private int timeoutMs = 1000;
	
	public BaseSpiderProcessor() {
	}
	
	public abstract void doProcess(SpiderContext context);
	
	@PostConstruct
	public void init() {
	}
	
	public void preProcess() {
	}
	
	public void postProcess() {
	}
	
	public boolean isTimeoutEnable() {
		return true;
	}
	
}
