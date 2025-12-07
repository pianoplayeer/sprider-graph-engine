package com.pianoplayeer.spider.graph.parser;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.pianoplayeer.spider.common.log.SpiderLogger;
import com.pianoplayeer.spider.graph.BaseOperator;
import com.pianoplayeer.spider.graph.SpiderGraph;
import com.pianoplayeer.spider.graph.exception.ConflictOpNameException;
import com.pianoplayeer.spider.graph.exception.GraphNameMissingException;
import com.pianoplayeer.spider.utils.CommonUtils;
import com.pianoplayeer.spider.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @date 2025/11/29
 * @package com.pianoplayeer.spider.graph.parser
 */
@Slf4j
public class SpiderGraphParser {
	
	private static final String GRAPH_CONFIG_LOC_PATTERN = "classpath:graph/**/*.json";
	
	private static final String GRAPH_NAME = "name";
	
	private static final String PROCESSORS = "processors";
	
	private static final String PROCESSORS_CONFIG = "config";
	
	private static final String GRAPH = "graph";
	
	private static final String TIMEOUT_MS = "timeoutMs";
	
	private static final String NEED_TIMEOUT = "needTimeout";
	
	private Map<String, BaseOperator> operatorMap = new HashMap<>();
	
	@Autowired
	private ApplicationContext applicationContext;
	
	public SpiderGraphParser() {
		getConfigFiles().forEach(filename -> {
			SpiderGraph graph = parseGraph(filename);
			String graphName = graph.getName();
			
			if (operatorMap.get(graphName) != null) {
				throw new ConflictOpNameException("Conflict operator name: " + graphName);
			}
			
			operatorMap.put(graphName, graph);
		});
	}
	
	public SpiderGraph getGraph(String filename) {
		var op = operatorMap.get(getGraphName(filename));
		return (SpiderGraph) op;
	}
	
	private List<String> getConfigFiles() {
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = null;
		try {
			resources = resolver.getResources(GRAPH_CONFIG_LOC_PATTERN);
		} catch (IOException e) {
			log.error("error while get graph config resources", e);
		}
		
		if (resources != null) {
			return Arrays.stream(resources).map(Resource::getFilename).toList();
		}
		return Collections.emptyList();
	}
	
	private SpiderGraph parseGraph(String filename) {
		SpiderGraph graph = new SpiderGraph();
		JSONObject obj = JsonUtils.readJsonFromResources(filename);
		
		graph.setName(obj.getString(GRAPH_NAME));
		graph.setTimeoutMs(obj.getLong(TIMEOUT_MS));
		graph.setProcessorTypeMap(obj.getObject(PROCESSORS, new TypeReference<Map<String, String>>() {}));
		graph.setGraph(obj.getObject(GRAPH, new TypeReference<Map<String, String>>() {}));
		graph.setProcessorConfigs(obj.getObject(
				PROCESSORS_CONFIG,
				new TypeReference<Map<String, Map<String, Object>>>() {}
				)
		);
		
		constructDAG(graph);
		return graph;
	}
	
	private void constructDAG(SpiderGraph graph) {
	
	}
	
	private String getGraphName(String filename) {
		JSONObject obj = JsonUtils.readJsonFromResources(filename);
		var name = obj.getString(GRAPH_NAME);
		
		if (StringUtils.isBlank(name)) {
			throw new GraphNameMissingException("Graph name is missing for file: " + filename);
		}
		
		return name;
	}
	
}
