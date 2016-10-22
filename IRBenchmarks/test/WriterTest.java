import java.io.BufferedWriter;

import benchmarks.connection.TrecWriter;


public class WriterTest {
	public static void main(String args[]) throws Exception{
		TrecWriter tw = new TrecWriter();
		tw.prepareWriter("D:\\thisisgoingtobeworstfilename.txt");
		BufferedWriter wt=tw.getWriter();
		wt.write("Hello, Blah");
		wt.write("\n");
		wt.write("Blah, Bowlllll");
		tw.close();
	}

}
