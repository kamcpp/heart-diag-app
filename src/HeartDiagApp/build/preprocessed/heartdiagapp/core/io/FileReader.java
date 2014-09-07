package heartdiagapp.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

public class FileReader {

    private final InputStream is;
    private int bucket;

    public FileReader(InputStream is) {
        this.is = is;
        this.bucket = -1;
    }

    public boolean hasLine() throws IOException {
        if (bucket != -1) {
            return true;
        }
        bucket = is.read();
        return bucket != -1;
    }

    public String readLine() throws IOException {
        if (!hasLine()) {
            return null;
        }
        int temp;
        StringBuffer out = new StringBuffer();
        while (true) {
            if (bucket != -1) {
                temp = bucket;
                bucket = -1;
            } else {
                temp = is.read();
                if (temp == -1) {
                    break;
                }
            }
            if (temp == '\r') {
                int nextChar = is.read();
                if (temp != '\n') {
                    bucket = nextChar;
                }
                break;
            } else if (temp == '\n') {
                break;
            } else {
                out.append((char) temp);
            }
        }
        String str = out.toString().trim();
        return str;
    }

    public Vector readLines() throws IOException {
        Vector lines = new Vector();
        String line;
        while ((line = readLine()) != null) {
            lines.addElement(line);
        }
        return lines;
    }
}
