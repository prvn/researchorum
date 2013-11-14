package com.researchorum.crawler;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.researchorum.utils.Config;
import com.researchorum.mongodb.UrlFecther;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * @author pbathala
 */
public class Crawler {

    private static final Logger logger = Logger.getLogger(Crawler.class);

    private UrlFecther urlFecther;
    private DBCollection dbCollection;

    public Crawler() throws Exception {
        this.dbCollection = new MongoClient().getDB(Config.MONGODB_DB).getCollection(Config.MONGODB_COLLECTION);
        this.urlFecther = new UrlFecther(Config.MONGODB_DB, Config.MONGODB_COLLECTION);
    }

    private void getAbstractsAndWrite(PublicationType[] types) {
        List<String> links = urlFecther.getLinks().getIeeeUrls();
        for (String link : links) {
            logger.info("Processing link: " + link);
            String _abstract = null;
            try {
                URL url = new URL(link);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(5000);
                connection.setInstanceFollowRedirects(true);

                int statusCode = connection.getResponseCode();
                if (statusCode == 200) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuffer html = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        html.append(inputLine);
                    }
                    in.close();

                    Document jsoup = Jsoup.parse(html.toString());
                    Elements divs = jsoup.getElementsByTag("div");
                    if (html.toString().contains("IEEE Xplore")) {
                        Elements articles = divs.get(0).getElementsByClass("article");
                        Elements p = null;
                        for (Element element : articles) {
                            p = element.getElementsByTag("p");
                            if (p != null) {
                                break;
                            }
                        }

                        if (p != null && !p.isEmpty()) {
                            Element paragraphs = p.get(0);
                            _abstract = paragraphs.html();
                        }
                    } else if (html.toString().contains("ieeecomputersociety")) {
                        Elements classes = divs.get(0).getElementsByClass("abs-articlesummary");
                        if (classes != null && !classes.isEmpty()) {
                            _abstract = classes.get(0).html();
                        }
                    }
                    // save abstract to mongodb
                    BasicDBObject searchQuery = new BasicDBObject("sourceUrl", link);
                    BasicDBObject fields = new BasicDBObject("publication", "IEEE");
                    BasicDBObject updateQuery = new BasicDBObject("$set", fields);
                    if (_abstract != null && !_abstract.isEmpty()) {
                        fields.append("abstract", _abstract);
                    } else {
                        logger.error("Abstract null for link - " + link);
                    }

                    WriteResult writeResult = dbCollection.update(searchQuery, updateQuery);
                    int docsUpdated = writeResult.getN();

                    if (docsUpdated > 0) {
                        logger.info("Saved abstract to mongo for link - " + link);
                    } else {
                        logger.error("Failed to save abstract to mongodb - " + link + "; Error: " + writeResult.getError());
                    }
                } else if (statusCode == 404) {
                    continue;
                } else {
                    logger.error("Fetching page failed with status " + statusCode + " - " + link);
                    break;
                }
            } catch (MalformedURLException e) {
                logger.error(e);
            } catch (IOException e) {
                logger.error(e);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new Crawler().getAbstractsAndWrite(new PublicationType[]{PublicationType.IEEE});
    }
}
