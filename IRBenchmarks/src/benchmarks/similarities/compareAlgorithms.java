package benchmarks.similarities;

import java.io.BufferedWriter;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocsCollector;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.LMDirichletSimilarity;
import org.apache.lucene.search.similarities.LMJelinekMercerSimilarity;

public class compareAlgorithms {
	IndexSearcher searcher;
	IndexReader reader;
	BufferedWriter writer;
	
	public void SetWriter(BufferedWriter writer){
		this.writer=writer;
	}
	
	public compareAlgorithms(IndexReader reader){
		this.reader=reader;
	}
	
	public void writeScores(ScoreDoc[] docs,String identifier, String comment) throws IOException{
		for(int i=0;i < docs.length;i++){
			Document doc = searcher.doc(docs[i].doc);
			identifier=identifier.replace("\n","");
			this.writer.write(identifier.substring(identifier.length()-4, identifier.length())+" 0 "+doc.get("DOCNO")+" "+i+" "+docs[i].score + " "+ comment);
			this.writer.write("\n");
		}
	}
	
	public void BM25Similarity(String queryString,String identifier, String comment) throws ParseException, IOException{
		//BM25 Similarity
		IndexSearcher searcher = new IndexSearcher(reader);
		this.searcher=searcher;
		searcher.setSimilarity(new org.apache.lucene.search.similarities.BM25Similarity());
		StandardAnalyzer analyser = new StandardAnalyzer();
		QueryParser parser = new QueryParser("TEXT", analyser);
		
		Query query = parser.parse(queryString);
		TopScoreDocCollector collector = TopScoreDocCollector.create(1000);
		searcher.search(query, collector);
		
		ScoreDoc[] docs = collector.topDocs().scoreDocs;
		writeScores(docs,identifier,comment);
		
	}
	
	public void ClassicSimilarity(String queryString,String identifier, String comment) throws ParseException, IOException{
		//Classic similarity
		IndexSearcher searcher = new IndexSearcher(reader);
		this.searcher=searcher;
		searcher.setSimilarity(new ClassicSimilarity());
		StandardAnalyzer analyser = new StandardAnalyzer();
		QueryParser parser = new QueryParser("TEXT", analyser);
		
		Query query = parser.parse(queryString);
		TopScoreDocCollector collector = TopScoreDocCollector.create(1000);
		searcher.search(query, collector);
		
		ScoreDoc[] docs = collector.topDocs().scoreDocs;
		writeScores(docs,identifier,comment);
		
	}
	
	public void LMDirichlet(String queryString,String identifier, String comment) throws ParseException, IOException{
		//LM With Dirichlet smoothing
		IndexSearcher searcher = new IndexSearcher(reader);
		this.searcher=searcher;
		searcher.setSimilarity(new LMDirichletSimilarity());
		StandardAnalyzer analyser = new StandardAnalyzer();
		QueryParser parser = new QueryParser("TEXT", analyser);
		
		Query query = parser.parse(queryString);
		TopScoreDocCollector collector = TopScoreDocCollector.create(1000);
		searcher.search(query, collector);
		
		ScoreDoc[] docs = collector.topDocs().scoreDocs;
		writeScores(docs,identifier,comment);
		
	}
	
	public void LMJMerck(String queryString,String identifier, String comment) throws ParseException, IOException{
		//LM with JM smoothing with 0.7 lambda
		IndexSearcher searcher = new IndexSearcher(reader);
		this.searcher=searcher;
		searcher.setSimilarity(new LMJelinekMercerSimilarity(0.7f));
		StandardAnalyzer analyser = new StandardAnalyzer();
		QueryParser parser = new QueryParser("TEXT", analyser);
		
		Query query = parser.parse(queryString);
		TopScoreDocCollector collector = TopScoreDocCollector.create(1000);
		searcher.search(query, collector);
		
		ScoreDoc[] docs = collector.topDocs().scoreDocs;
		writeScores(docs,identifier,comment);
		
	}
}
