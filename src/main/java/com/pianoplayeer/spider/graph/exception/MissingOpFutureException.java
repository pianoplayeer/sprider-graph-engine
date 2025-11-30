package com.pianoplayeer.spider.graph.exception;

/**
 * @date 2025/11/30
 * @package com.pianoplayeer.spider.graph.exception
 */
public class MissingOpFutureException extends RuntimeException {
	public MissingOpFutureException(String msg) {
		super(msg);
	}
	
	public MissingOpFutureException(String msg, Throwable throwable) {
		super(msg, throwable);
	}
}
