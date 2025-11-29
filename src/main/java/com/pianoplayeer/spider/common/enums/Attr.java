package com.pianoplayeer.spider.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * @date 2025/11/29
 * @package com.pianoplayeer.spider.common.enums
 */
@Getter
public enum Attr {
	
	GRAPH_ITEMS("graph_items", List.class, Collections.EMPTY_LIST)
	;
	
	private final String name;
	
	private final Class<?> type;
	
	private final Object defaultValue;
	
	Attr(String name, Class<?> type, Object value) {
		this.name = name;
		this.type = type;
		this.defaultValue = value;
	}
	
}
