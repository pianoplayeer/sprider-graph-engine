package com.pianoplayeer.spider.common.log;

import com.pianoplayeer.spider.graph.op.BaseSpiderProcessor;
import lombok.extern.slf4j.Slf4j;

/**
 * @date 2025/11/29
 * @package com.pianoplayeer.spider.common.log
 */
@Slf4j
public class SpiderLogger {
	public static void info(BaseSpiderProcessor processor, String msg) {
		log.info("processor: {}, " + msg, processor.getProcessorName());
	}
	
	public static void info(BaseSpiderProcessor processor, String msg, Object ...objs) {
		log.info("processor: {}, " + msg, processor.getProcessorName(), objs);
	}
	
	public static void info(BaseSpiderProcessor processor, String msg, Throwable throwable) {
		log.info("processor: {}, " + msg, processor.getProcessorName(), throwable);
	}
	
	
	public static void warn(BaseSpiderProcessor processor, String msg) {
		log.info("processor: {}, " + msg, processor.getProcessorName());
	}
	
	public static void warn(BaseSpiderProcessor processor, String msg, Object ...objs) {
		log.info("processor: {}, " + msg, processor.getProcessorName(), objs);
	}
	
	public static void warn(BaseSpiderProcessor processor, String msg, Throwable throwable) {
		log.info("processor: {}, " + msg, processor.getProcessorName(), throwable);
	}
	
	public static void error(BaseSpiderProcessor processor, String msg) {
		log.info("processor: {}, " + msg, processor.getProcessorName());
	}
	
	public static void error(BaseSpiderProcessor processor, String msg, Object ...objs) {
		log.info("processor: {}, " + msg, processor.getProcessorName(), objs);
	}
	
	public static void error(BaseSpiderProcessor processor, String msg, Throwable throwable) {
		log.info("processor: {}, " + msg, processor.getProcessorName(), throwable);
	}
	
}
