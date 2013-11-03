package com.researchorum.dblp.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.researchorum.dblp.records.Inproceedings;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class Util {
    public static final String ARTICLE_TAG = "article";
    public static final String AUTHOR_TAG = "author";
    public static final String EDITOR_TAG = "editor";
    public static final String TITLE_TAG = "title";
    public static final String YEAR_TAG = "year";
    public static final String SOURCE_URL_TAG = "ee";
    public static final String JOURNAL_TAG = "journal";
	public static final String PROCEEDINGS_TAG = "proceedings";
	public static final String INPROCEEDINGS_TAG = "inproceedings";
	public static final String PUBLISHER_TAG = "publisher";
	public static final String BOOKTITLE_TAG = "booktitle";

	Util() {

	}

	public static void removeUnwantedTagsFromJson(JSONObject jsonObj) {
		jsonObj.remove("isInproceedingsTag");
		jsonObj.remove("isProceedingsTag");
		jsonObj.remove("isArticleTag");
		jsonObj.remove("isEditorTag");
		jsonObj.remove("isTitleTag");
		jsonObj.remove("isAuthorTag");
		jsonObj.remove("isJournalTag");
		jsonObj.remove("isYearTag");
		jsonObj.remove("isTitleTag");
		jsonObj.remove("isSourceUrlTag");
		jsonObj.remove("isTitleTag");
		jsonObj.remove("isPublisherTag");
		jsonObj.remove("isBookTitleTag");
	}

	public static void writeRecordToMongo(String jsonString, DBCollection mongoCollection) throws ParseException {
		
    	JSONParser parser = new JSONParser();
    	Object obj = parser.parse( jsonString );
		JSONObject jsonObj = (JSONObject) obj;
    	Util.removeUnwantedTagsFromJson(jsonObj);
    	//System.out.println(jsonObj.toJSONString());
    	Object mongoJson =  com.mongodb.util.JSON.parse(jsonObj.toJSONString());
    	BasicDBObject b = (BasicDBObject) mongoJson;
    	mongoCollection.insert(b);
	}

	public static void writeRecordToMongo(Inproceedings inproceedings) {
		// TODO Auto-generated method stub
		
	}
}
