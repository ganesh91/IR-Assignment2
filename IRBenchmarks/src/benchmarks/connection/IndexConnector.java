package benchmarks.connection;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.FSDirectory;

public class IndexConnector {
	private IndexReader indexReader;
	public IndexConnector(String indexLocation){
		try {
			indexReader = DirectoryReader.open(FSDirectory.open(Paths.get(indexLocation)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public IndexReader getReader(){
		return(indexReader);
	}
	
	public IndexSearcher getIndexSearcher(){
		return(new IndexSearcher(indexReader));
	}
	public void closeReader(){
		try {
			indexReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
