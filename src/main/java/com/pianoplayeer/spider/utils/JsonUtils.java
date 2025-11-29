package com.pianoplayeer.spider.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @date 2025/11/29
 * @package com.pianoplayeer.spider.utils
 */
public class JsonUtils {
	
	public static JSONObject readJsonFromResources(String filename) {
		try (InputStream is = JsonUtils.class.getClassLoader().getResourceAsStream(filename)) {
			if (is == null) {
				throw new RuntimeException("File not found in resources: " + filename);
			}
			String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
			return JSON.parseObject(content);
		} catch (Exception e) {
			throw new RuntimeException("Error reading JSON file: " + filename, e);
		}
	}
	
}
