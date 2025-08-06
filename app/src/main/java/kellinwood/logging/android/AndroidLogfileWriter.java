package kellinwood.logging.android;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import kellinwood.logging.AbstractLogWriter;

/**
 * User: ken
 * Date: 1/30/13
 */
public class AndroidLogfileWriter extends AbstractLogWriter {

    File externalFile;

    public AndroidLogfileWriter( File externalFile) {
        this.externalFile = externalFile;
    }

    @Override
    public void writeImpl(String level, String category, String message, Throwable t) {
        try {
            synchronized (AndroidLogfileWriter.class) {
                PrintWriter pw = new PrintWriter( new FileWriter(externalFile, true));
                pw.print( format(level, category, message));
                pw.flush();
                if (t != null) t.printStackTrace( pw);
                pw.close();
            }
        } catch (Throwable x) {

        }
    }
}
