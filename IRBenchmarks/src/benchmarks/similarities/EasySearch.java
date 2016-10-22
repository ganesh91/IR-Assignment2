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

public class EasySearch {
	public static void main(String args[]) throws ParserConfigurationException,
			SAXException, IOException, ParseException {
		//Read the Trec test file
		TrecIterator ti = new TrecIterator("D:\\trecTest.xml", "top", "title");
		
		//Read the Index
		IndexConnector index = new IndexConnector("D:\\index");
		IndexSearcher srch = index.getIndexSearcher();
		CustomSimilarity cs = new CustomSimilarity(srch, index.getReader());
		TrecWriter tw = new TrecWriter();
		tw.prepareWriter("D:\\Custom_short.txt");
		//Prepare Writer
		
		BufferedWriter writer = tw.getWriter();
		
		//For All Topics, Iterate and Write

		while (ti.hasNext()) {
			Map<String, String> hm = ti.next();
			Iterator it = hm.entrySet().iterator();
			Map.Entry pair = (Map.Entry) it.next();
			//Write the score to the file.
			cs.writeScore((String) pair.getValue(), (String) pair.getKey(), tw);
		}

	}

}
