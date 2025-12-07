package com.pianoplayeer.spider.graph.exception;

/**
 * @date 2025/12/1
 * @package com.pianoplayeer.spider.graph.exception
 */
public class ConflictOpNameException extends RuntimeException {
	public ConflictOpNameException(String msg) {
		super(msg);
	}
	
	public ConflictOpNameException(String msg, Throwable throwable) {
		super(msg, throwable);
	}
}
