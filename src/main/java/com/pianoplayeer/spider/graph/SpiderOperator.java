package com.pianoplayeer.spider.graph;

import com.pianoplayeer.spider.common.log.BizLogger;
import com.pianoplayeer.spider.common.log.SpiderLogger;
import com.pianoplayeer.spider.graph.context.SpiderContext;
import com.pianoplayeer.spider.graph.op.BaseSpiderProcessor;
import lombok.Getter;
import org.springframework.util.StopWatch;

import java.util.List;

/**
 * @date 2025/11/29
 * @package com.pianoplayeer.spider.graph.op
 */
@Getter
public class SpiderOperator extends BaseOperator {
	
	private BaseSpiderProcessor processor;
	
	@Override
	public void doProcess(SpiderContext context) {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		processor.preProcess();
		processor.doProcess(context);
		processor.postProcess();
		
		stopWatch.stop();
		SpiderLogger.info(this, "processor has finished, cost: {} ms", stopWatch.getTotalTimeMillis());
	}
	
	@Override
	public boolean isTimeoutEnable() {
		return processor.isTimeoutEnable();
	}
	
}
