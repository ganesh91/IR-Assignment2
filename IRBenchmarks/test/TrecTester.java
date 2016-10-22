import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import benchmarks.connection.TrecIterator;

public class TrecTester {

	public static void main(String args[]) throws ParserConfigurationException,
			SAXException, IOException {
		TrecIterator ti = new TrecIterator("D:\\trecTest.xml", "top", "title");
		while (ti.hasNext()) {
			Map<String, String> hm = ti.next();
			Iterator it = hm.entrySet().iterator();
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue());
		}
	}
}
