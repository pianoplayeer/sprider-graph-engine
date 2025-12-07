package com.pianoplayeer.spider.utils;

/**
 * @date 2025/12/4
 * @package com.pianoplayeer.spider.utils
 */
public class CommonUtils {
	public static <T> T defaultIfNull(T obj, T defaultValue) {
		return obj != null ? obj : defaultValue;
	}
}
