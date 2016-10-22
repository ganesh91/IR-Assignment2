package benchmarks.similarities;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.PostingsEnum;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

import benchmarks.connection.TrecWriter;

public class CustomSimilarity {
	private IndexSearcher indexSearcher;
	private IndexReader indexReader;

	public CustomSimilarity(IndexSearcher indexSearcher, IndexReader rdr) {
		this.indexSearcher = indexSearcher;
		this.indexReader = rdr;
	}

	public float tf_idf(int ct, float docLen, int N, int kt) {
		//Calculate tf-idf by the given formula. Add +1 to numerator and denominator for smoothing.
		return (((ct + 1) / (docLen + 1)) * (1 + ((N + 1) / (kt + 1))));
	}

	public void writeScore(String queryString, String comment, TrecWriter tw)
			throws ParseException {
		try {
			// Get the Query Terms
			List<Score> scores = new ArrayList<Score>();
			Analyzer analyser = new StandardAnalyzer();
			QueryParser parser = new QueryParser("TEXT", analyser);
			Query query = parser.parse(queryString);
			Set<Term> queryTerms = new LinkedHashSet<Term>();
			indexSearcher.createNormalizedWeight(query, false).extractTerms(
					queryTerms);

			// Get an Iterator for All documents on the index
			ClassicSimilarity dSimi = new ClassicSimilarity();
			List<LeafReaderContext> leafContexts = indexReader.getContext()
					.reader().leaves();

			// Total Number of documents in the collection
			int N = indexReader.maxDoc();

			// Iterate for Each document, find custom tf-idf score.
			for (int i = 0; i < leafContexts.size(); i++) {
				LeafReaderContext leafContext = leafContexts.get(i);
				int startDocNo = leafContext.docBase;
				int numberOfDoc = leafContext.reader().maxDoc();

				for (int docId = 0; docId < numberOfDoc; docId++) {
					float normDocLength = dSimi.decodeNormValue(leafContext
							.reader().getNormValues("TEXT").get(docId));

					// Document length of i-th document
					float docLength = 1 / (normDocLength * normDocLength);
					// Document Name of the ith-document
					String documentName = indexSearcher.doc(docId + startDocNo)
							.get("DOCNO");

					Document currDoc = indexSearcher.doc(docId + startDocNo);
					String CurrDocNo = currDoc.get("DOCNO");

					float score = 0f;

					for (Term t : queryTerms) {

						// Number of Documents having the term t
						int kt = indexReader
								.docFreq(new Term("TEXT", t.text()));

						// Get the frequency of the term in collection
						PostingsEnum de = MultiFields.getTermDocsEnum(
								leafContext.reader(), "TEXT",
								new BytesRef(t.text()));

						// Get the Frequency of the Term in the Given DOC
						int doc = 0;

						if (de != null) {
							while (de.nextDoc() != PostingsEnum.NO_MORE_DOCS) {
								//Check if the current document is the actual document.
								if (indexSearcher.doc(de.docID() + startDocNo)
										.get("DOCNO").equals(CurrDocNo)) {
									doc = de.freq();
									break;
								}
							}

						}
						//Calculate tf-idf score 
						score += tf_idf(doc, docLength, N, kt);

					}
					scores.add(new Score(score, documentName));

				}

			}
			//Sort the collection and get first 1000 documents
			Collections.sort(scores);
			String identifier = comment.replace("\n", "");
			String documentid = identifier.substring(identifier.length() - 4,
					identifier.length());
			System.out.println(documentid);
			for (int j = 0; j < 1000; j++) {
				tw.getWriter().write(
						documentid + " 0 " + scores.get(j).name + " " + j + " "
								+ scores.get(j).score + " Custom_Short");
				tw.getWriter().write("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

// http://stackoverflow.com/questions/4353572/how-can-i-create-a-sorted-list-of-integer-and-string-pairs
class Score implements Comparable<Score> {
	float score;
	String name;

	public Score(float score, String name) {
		this.score = score;
		this.name = name;
	}

	@Override
	public int compareTo(Score o) {
		//Reverse the signs from the reference
		return score > o.score ? -1 : score < o.score ? 1 : 0;
	}
}
