package com.pianoplayeer.spider.common.enums;

import lombok.Getter;

/**
 * @date 2025/11/29
 * @package com.pianoplayeer.spider.common.attrs
 */
@Getter
public enum Param {
	
	REQUEST_ID("request_id", Integer.class, null)
	;
	
	private final String name;
	
	private final Class<?> type;
	
	private final Object defaultValue;
	
	Param(String name, Class<?> type, Object value) {
		this.name = name;
		this.type = type;
		this.defaultValue = value;
	}
	
}
