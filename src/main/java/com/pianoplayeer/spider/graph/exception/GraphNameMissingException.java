package com.pianoplayeer.spider.graph.exception;

/**
 * @date 2025/12/1
 * @package com.pianoplayeer.spider.graph.exception
 */
public class GraphNameMissingException extends RuntimeException {
	public GraphNameMissingException(String msg) {
		super(msg);
	}
	
	public GraphNameMissingException(String msg, Throwable throwable) {
		super(msg, throwable);
	}
}
