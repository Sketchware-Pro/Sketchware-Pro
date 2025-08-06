package kellinwood.logging;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: ken
 * Date: 1/30/13
 */
public abstract class AbstractLogWriter implements LogWriter {

    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


    public static String format( String level, String category, String message) {
        return String.format( "%s %5s %s: %s\n", dateFormat.format(new Date()), level, category, message);
    }

    @Override
    public void write(String level, String category, String message, Throwable t) {
        if (level == null) level = "Null-level";
        if (category == null) category = "Null-category";
        if (message == null) message = "Null-message";
        writeImpl( level, category, message, t);
    }

    public abstract void writeImpl( String level, String category, String message, Throwable t);
}
