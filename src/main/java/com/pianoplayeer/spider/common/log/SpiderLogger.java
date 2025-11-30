package com.pianoplayeer.spider.common.log;

import com.pianoplayeer.spider.graph.BaseOperator;
import lombok.extern.slf4j.Slf4j;

/**
 * @date 2025/11/29
 * @package com.pianoplayeer.spider.common.log
 */
@Slf4j
public class SpiderLogger {
	public static void info(BaseOperator operator, String msg) {
		log.info("operator: {}, " + msg, operator);
	}
	
	public static void info(BaseOperator operator, String msg, Object ...objs) {
		log.info("operator: {}, " + msg, operator, objs);
	}
	
	public static void info(BaseOperator operator, String msg, Throwable throwable) {
		log.info("operator: {}, " + msg, operator, throwable);
	}
	
	public static void info(BaseOperator operator, Throwable throwable) {
		log.info("operator: " + operator, throwable);
	}
	
	
	public static void warn(BaseOperator operator, String msg) {
		log.warn("operator: {}, " + msg, operator);
	}
	
	public static void warn(BaseOperator operator, String msg, Object ...objs) {
		log.warn("operator: {}, " + msg, operator, objs);
	}
	
	public static void warn(BaseOperator operator, String msg, Throwable throwable) {
		log.warn("operator: {}, " + msg, operator, throwable);
	}
	
	public static void warn(BaseOperator operator, Throwable throwable) {
		log.warn("operator: " + operator, throwable);
	}
	
	public static void error(BaseOperator operator, String msg) {
		log.error("operator: {}, " + msg, operator);
	}
	
	public static void error(BaseOperator operator, String msg, Object ...objs) {
		log.error("operator: {}, " + msg, operator, objs);
	}
	
	public static void error(BaseOperator operator, String msg, Throwable throwable) {
		log.error("operator: {}, " + msg, operator, throwable);
	}
	
	public static void error(BaseOperator operator, Throwable throwable) {
		log.error("operator: " + operator, throwable);
	}
	
}
