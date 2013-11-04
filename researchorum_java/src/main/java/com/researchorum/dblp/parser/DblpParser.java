package com.researchorum.dblp.parser;

import com.google.gson.Gson;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.researchorum.dblp.records.Article;
import com.researchorum.dblp.records.Inproceedings;
import com.researchorum.dblp.records.Proceedings;
import com.researchorum.dblp.records.Record;
import com.researchorum.dblp.util.Util;
import org.json.simple.parser.ParseException;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

public class DblpParser {

    private final int maxAuthorsPerPaper = 200;

    private class ConfigHandler extends DefaultHandler {

        private MongoClient mongoClient;

        private Locator locator;

        private String Value;
        private String key;
        private String recordTag;
        private Record record;
        private Article article;
        private Proceedings proceedings;
        private Inproceedings inproceedings;

        private DB db;

        private DBCollection mongoCollection;

        private boolean isJournal;

        private int counter = 0;

        public ConfigHandler() throws UnknownHostException {
            mongoClient = new MongoClient();
            db = mongoClient.getDB("dblp");
            mongoCollection = db.getCollection("records");
        }

        public void setDocumentLocator(Locator locator) {
            this.locator = locator;
        }

        public void startElement(String namespaceURI, String localName,
                                 String rawName, Attributes atts) throws SAXException {

            if (Util.ARTICLE_TAG.equals(rawName)) {
                article = new Article();
                System.out.println(counter++);
                return;
            }
            if (Util.PROCEEDINGS_TAG.equals(rawName)) {
                proceedings = new Proceedings();
                return;
            }
            if (Util.INPROCEEDINGS_TAG.equals(rawName)) {
                inproceedings = new Inproceedings();
                return;
            }
            if (article != null) {
                article.processTags(rawName);
                return;
            }
            if (proceedings != null) {
                proceedings.processTags(rawName);
                return;
            }
            if (inproceedings != null) {
                inproceedings.processTags(rawName);
                return;
            }

        }

        public void endElement(String namespaceURI, String localName,
                               String rawName) throws SAXException {
            if (Util.ARTICLE_TAG.equals(rawName)) {
                Gson g = new Gson();
                String jsonString = g.toJson(article);
                try {
                    Util.writeRecordToMongo(jsonString, mongoCollection);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //article.setArticleTag(false);
                article = null;
            }
            if (Util.PROCEEDINGS_TAG.equals(rawName)) {
                Gson g = new Gson();
                String jsonString = g.toJson(proceedings);
                try {
                    Util.writeRecordToMongo(jsonString, mongoCollection);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //proceedings.setProceedingsTag(false);
                proceedings = null;
            }
            if (Util.INPROCEEDINGS_TAG.equals(rawName)) {
                Gson g = new Gson();
                String jsonString = g.toJson(inproceedings);
                try {
                    Util.writeRecordToMongo(jsonString, mongoCollection);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //inproceedings.setInproceedingsTag(false);
                inproceedings = null;
            }
        }

        public void characters(char[] ch, int start, int length)
                throws SAXException {

            if (article != null) {
                article.populateFields(new String(ch, start, length));
                return;
            }
            if (proceedings != null) {
                proceedings.populateFields(new String(ch, start, length));
                return;
            }
            if (inproceedings != null) {
                inproceedings.populateFields(new String(ch, start, length));
                return;
            }

        }

        private void Message(String mode, SAXParseException exception) {
            System.out.println(mode + " Line: " + exception.getLineNumber()
                    + " URI: " + exception.getSystemId() + "\n" + " Message: "
                    + exception);
        }

        public void warning(SAXParseException exception) throws SAXException {

            Message("**Parsing Warning**\n", exception);
            throw new SAXException("Warning encountered");
        }

        public void error(SAXParseException exception) throws SAXException {

            Message("**Parsing Error**\n", exception);
            throw new SAXException("Error encountered");
        }

        public void fatalError(SAXParseException exception) throws SAXException {

            Message("**Parsing Fatal Error**\n", exception);
            throw new SAXException("Fatal Error encountered");
        }
    }

    DblpParser(String uri) {
        try {
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            SAXParser parser = parserFactory.newSAXParser();
            ConfigHandler handler = new ConfigHandler();
            //parser.getXMLReader().setFeature("http://xml.org/sax/features/validation", true);
            parser.parse(new File(uri), handler);
        } catch (IOException e) {
            System.out.println("Error reading URI: " + e.getMessage());
        } catch (SAXException e) {
            System.out.println("Error in parsing: " + e.getMessage());
        } catch (ParserConfigurationException e) {
            System.out.println("Error in XML parser configuration: " +
                    e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.setProperty("entityExpansionLimit", String.valueOf(Integer.MAX_VALUE));
        if (args.length < 1) {
            System.out.println("Usage: java Parser [input]");
            System.exit(0);
        }
        DblpParser p = new DblpParser(args[0]);
    }
}


