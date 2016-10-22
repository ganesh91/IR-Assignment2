package benchmarks.connection;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TrecWriter {
	BufferedWriter writer;

	public TrecWriter() {
	}

	public void prepareWriter(String filePath) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
			this.writer = writer;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public BufferedWriter getWriter() {
		return (writer);
	}

	public void close() {
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
