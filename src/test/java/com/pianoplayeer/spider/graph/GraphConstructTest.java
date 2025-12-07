package com.pianoplayeer.spider.graph;

import com.pianoplayeer.spider.graph.parser.SpiderGraphParser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @date 2025/12/1
 * @package com.pianoplayeer.spider.graph
 */
@Slf4j
public class GraphConstructTest {
	@Test
	public void testGraphConfig() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		SpiderGraphParser parser = new SpiderGraphParser();
		Method m = SpiderGraphParser.class.getDeclaredMethod("getConfigFiles");
		m.setAccessible(true);
		log.info("config files: {}", m.invoke(parser));
	}
}
