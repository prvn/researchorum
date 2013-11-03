package com.researchorum.util;

public class ConfigDictionary {
	public final String LUCENE_INDEX_PATH = "lucene.index";
	public final boolean CREATE_NEW_INDEX = true;
	public final String MONGODB_NAME = "dblp";
	public final String MONGODB_COLLECTION_NAME = "records";
	
	private static ConfigDictionary configDictionary;;
	
	private ConfigDictionary() {
	}
	
	public static ConfigDictionary getInstance() {
		if(configDictionary == null) {
			configDictionary = new ConfigDictionary();
		}
		return configDictionary;
	}
}
