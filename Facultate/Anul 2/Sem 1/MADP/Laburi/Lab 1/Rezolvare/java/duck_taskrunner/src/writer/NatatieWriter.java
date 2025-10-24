package writer;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class NatatieWriter {
    private PrintWriter out;

    public NatatieWriter(String filename) throws IOException {
            out = new PrintWriter(new FileWriter(filename));
    }

    public void println(String text) {
        out.println(text);
    }

    public void close() {
        out.close();
    }
}
