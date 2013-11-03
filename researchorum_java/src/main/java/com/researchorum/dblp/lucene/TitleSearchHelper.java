package com.researchorum.dblp.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.researchorum.util.ConfigDictionary;

public class TitleSearchHelper {
	
	public void search() throws IOException, ParseException {
		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(
				ConfigDictionary.getInstance().LUCENE_INDEX_PATH)));
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_45);

		String queries = null;
		String queryString = null;
		String field = "title";
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		QueryParser parser = new QueryParser(Version.LUCENE_45, field, analyzer);
		while (true) {
			if (queries == null && queryString == null) { // prompt the user
				System.out.println("Enter query: ");
			}

			String line = queryString != null ? queryString : in.readLine();

			if (line == null || line.length() == -1) {
				break;
			}

			line = line.trim();
			if (line.length() == 0) {
				break;
			}

			Query query = parser.parse(line);
			System.out.println("Searching for: " + query.toString(field));

			TopDocs docs = searcher.search(query, null, 100);
			System.out.println("Total Documents returned : " + docs.totalHits);
			for(ScoreDoc scoreDoc : docs.scoreDocs) {
				Document doc = searcher.doc(scoreDoc.doc);
				System.out.println(doc.get("id") + " : " + doc.get("title"));
			}

			if (queryString != null) {
				break;
			}
		}
		reader.close();
	}
	
	public static void main(String[] args) throws IOException, ParseException {
		TitleSearchHelper search = new TitleSearchHelper();
		search.search();
	}

}
