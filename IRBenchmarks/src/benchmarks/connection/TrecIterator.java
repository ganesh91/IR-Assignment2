package benchmarks.connection;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TrecIterator{
	private String trecTestFile;
	protected boolean iterator_eof = true;
	private int currentIndex = 0;
	private int maxIndex = 0;
	private String subTag;
	NodeList iteratorDoc;
	
	public TrecIterator(String URI,String topTag,String subTag) throws ParserConfigurationException, SAXException, IOException{
		File trecFile = new File(URI);
		FileInputStream fis=new FileInputStream(trecFile);
//		System.out.println(file.getName());
		List<InputStream> streams = Arrays.asList(
				new ByteArrayInputStream("<root>".getBytes()),
				fis,
				new ByteArrayInputStream("</root>".getBytes()));
		InputStream cntr = 
				new SequenceInputStream(Collections.enumeration(streams));
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(cntr);
		NodeList docList = doc.getElementsByTagName(topTag);
		iteratorDoc = docList;
		maxIndex=docList.getLength();
		this.subTag=subTag;
	}
	
	
	public boolean hasNext(){
		return(iterator_eof);
	}
	
	public Map<String, String> next(){
	Map<String,String> hm;
		if(iterator_eof){
			Node doc=iteratorDoc.item(currentIndex);
			if(doc.getNodeType() == Node.ELEMENT_NODE){
				Element eElement = (Element) doc;
				String docno=eElement.getElementsByTagName("num").item(0).getTextContent();
				NodeList heads = eElement.getElementsByTagName(subTag);
				String head="";
				for (int j=0;j<heads.getLength();j++){
					Element el=((Element) heads.item(j));
					el.normalize();
					head+=el.getTextContent().trim();
				}
				head=head.replace("\\n","");
				hm = new HashMap<String,String>();
				hm.put(docno,head);
				currentIndex+=1;
				if (currentIndex==maxIndex){
					iterator_eof=false;
				}
				return hm;
			}
		}else{
			throw(new IllegalArgumentException());
		}
	// Create a dummy list to return
	hm = new HashMap<String,String>();
	return(hm);
	}
	

}
