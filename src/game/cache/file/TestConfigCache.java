package game.cache.file;

import game.entity.file.TestConfig;

import java.util.HashMap;
import java.util.Map;

public class TestConfigCache {
	private static Map<Integer, TestConfig> map = new HashMap<>();

	public static void putTestConfig(TestConfig testConfig) {
		int id = testConfig.getId();

		map.put(id, testConfig);
	}
	
	public static Map<Integer,TestConfig> getMap(){
		return map;
	}
}
