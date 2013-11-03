package com.researchorum.dblp.lucene;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.researchorum.util.ConfigDictionary;

public class MongoDataLoader {
	
	private MongoClient mongoClient;
    private DB db;
    private DBCollection mongoCollection;
	private FSDirectory dir;
	private StandardAnalyzer analyzer;
	private IndexWriterConfig iwc;
	private IndexWriter writer;

    public MongoDataLoader() {
		 try {
			mongoClient = new MongoClient();
			db = mongoClient.getDB(ConfigDictionary.getInstance().MONGODB_NAME);
			mongoCollection = db.getCollection(ConfigDictionary.getInstance().MONGODB_COLLECTION_NAME);
			
			
			dir = FSDirectory.open(new File(
					ConfigDictionary.getInstance().LUCENE_INDEX_PATH));

			analyzer = new StandardAnalyzer(Version.LUCENE_45);
			iwc = new IndexWriterConfig(Version.LUCENE_45,
					analyzer);
			if (ConfigDictionary.getInstance().CREATE_NEW_INDEX && null != iwc) {
				// Create a new index in the directory, removing any
				// previously indexed documents:
				iwc.setOpenMode(OpenMode.CREATE);
			} else {
				// Add new documents to an existing index:
				iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
			}
			writer = new IndexWriter(dir, iwc);
				
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    public void readMongoDocuments() throws IOException {
    	DBCursor cursor = mongoCollection.find();
    	try {
    	   while(cursor.hasNext()) {
    		   indexDocument(cursor.next());
    	   }
    	} finally {
    	   cursor.close();
    	   writer.close();
    	}
    }
    
    private void indexDocument(DBObject mongoDoc) {

    	try {

			// Optional: for better indexing performance, if you
			// are indexing many documents, increase the RAM
			// buffer. But if you do this, increase the max heap
			// size to the JVM (eg add -Xmx512m or -Xmx1g):
			//
			// iwc.setRAMBufferSizeMB(256.0);

			Document doc = new Document();
			doc.add(new StringField("id", mongoDoc.get("_id").toString(), Field.Store.YES));
			doc.add(new TextField("title", mongoDoc.get("title").toString(), Field.Store.YES));

			// NOTE: if you want to maximize search performance,
			// you can optionally call forceMerge here. This can be
			// a terribly costly operation, so generally it's only
			// worth it when your index is relatively static (ie
			// you're done adding documents to it):
			//
			// writer.forceMerge(1);

			if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
				// New index, so we just add the document (no old document can
				// be
				// there):
				System.out.println("adding " + mongoDoc.get("title").toString());
				writer.addDocument(doc);
			}
//			 else {
//			 // Existing index (an old copy of this document may have been
//			 // indexed) so
//			 // we use updateDocument instead to replace the old one matching
//			 the
//			 // exact
//			 // path, if present:
//			 System.out.println("updating " + file);
//			 writer.updateDocument(new Term("path", file.getPath()), doc);
//			 }

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
        MongoDataLoader loader = new MongoDataLoader();
        try {
			loader.readMongoDocuments();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
