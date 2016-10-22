package benchmarks.similarities;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.xml.sax.SAXException;

import benchmarks.connection.IndexConnector;
import benchmarks.connection.TrecIterator;
import benchmarks.connection.TrecWriter;

public class OrchestrateComparison {
	public static void main(String args[]) throws ParserConfigurationException, SAXException, IOException, ParseException{
		TrecIterator ti = new TrecIterator("D:\\trecTest.xml", "top", "desc");
		TrecWriter tw = new TrecWriter();
		tw.prepareWriter("D:\\BM25_Long.txt");
		BufferedWriter writer = tw.getWriter();
		
		IndexConnector index = new IndexConnector("D:\\index");
		IndexReader reader = index.getReader();
		
		compareAlgorithms bm25 = new compareAlgorithms(reader);
		bm25.SetWriter(writer);
		
		compareAlgorithms cs = new compareAlgorithms(reader);
		cs.SetWriter(writer);
		
		compareAlgorithms ld = new compareAlgorithms(reader);
		ld.SetWriter(writer);
		
		compareAlgorithms jm = new compareAlgorithms(reader);
		jm.SetWriter(writer);
		
		while(ti.hasNext()){
			Map<String,String> hm = ti.next();
			Iterator it = hm.entrySet().iterator();
			Map.Entry pair = (Map.Entry)it.next();
			bm25.BM25Similarity((String) pair.getValue(),(String)pair.getKey(),"BM25_Long");
//			cs.ClassicSimilarity((String) pair.getValue(),(String)pair.getKey(),"Classic_Long");
//			ld.LMDirichlet((String) pair.getValue(),(String)pair.getKey(),"Dirichlet_Long");
//			jm.LMJMerck((String) pair.getValue(),(String)pair.getKey(),"JMerck_Long");
		}
		tw.close();
		index.closeReader();
		
	}
}
