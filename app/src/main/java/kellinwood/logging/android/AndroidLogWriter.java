package kellinwood.logging.android;

import android.util.Log;

import kellinwood.logging.AbstractLogWriter;
import kellinwood.logging.Logger;


/**
 * User: ken
 * Date: 1/30/13
 */
public class AndroidLogWriter extends AbstractLogWriter {

    @Override
    public void writeImpl(String level, String category, String message, Throwable t) {
        if (Logger.ERROR.equals(level)) {
            if (t != null) Log.e(category, message, t);
            else Log.e(category, message);
        }
        else if (Logger.DEBUG.equals(level)) {
            if (t != null) Log.d(category, message, t);
            else Log.d(category, message);
        }
        else if (Logger.WARN.equals(level)) {
            if (t != null) Log.w(category, message, t);
            else Log.w(category, message);
        }
        else if (Logger.INFO.equals(level)) {
            if (t != null) Log.i(category, message, t);
            else Log.i(category, message);
        }
    }
}
