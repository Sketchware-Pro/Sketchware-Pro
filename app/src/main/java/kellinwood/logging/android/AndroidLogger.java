package kellinwood.logging.android;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import kellinwood.logging.DefaultLogger;
import kellinwood.logging.LogManager;


// enable output via 'adb shell setprop log.tag.<YOUR TAG> <LEVEL>'
public class AndroidLogger extends DefaultLogger
{

	// Constructs the logger so that only the last component of the class name is used for the logging tag.
	// E.g., A parameter value of kellinwood.zipsigner.ZipPickerActivity results in a tag value of "ZipPickerActivity".
	public AndroidLogger(String c) {
		super(c);
		int pos = category.lastIndexOf('.');
		if (pos > 0) category = category.substring(pos+1);
		
	}

	Context toastContext;
	
	boolean isErrorToastEnabled = true;
	boolean isWarnToastEnabled = true;
	boolean isInfoToastEnabled = false;
	boolean isDebugToastEnabled = false;

	public Context getToastContext() {
		return toastContext;
	}

	public void setToastContext(Context toastContext) {
		this.toastContext = toastContext;
	}
	
	public boolean isErrorToastEnabled() {
		return isErrorToastEnabled;
	}

	public void setErrorToastEnabled(boolean isErrorToastEnabled) {
		this.isErrorToastEnabled = isErrorToastEnabled;
	}

	public boolean isWarnToastEnabled() {
		return isWarnToastEnabled;
	}

	public void setWarnToastEnabled(boolean isWarnToastEnabled) {
		this.isWarnToastEnabled = isWarnToastEnabled;
	}

	public boolean isInfoToastEnabled() {
		return isInfoToastEnabled;
	}

	public void setInfoToastEnabled(boolean isInfoToastEnabled) {
		this.isInfoToastEnabled = isInfoToastEnabled;
	}

	public boolean isDebugToastEnabled() {
		return isDebugToastEnabled;
	}

	public void setDebugToastEnabled(boolean isDebugToastEnabled) {
		this.isDebugToastEnabled = isDebugToastEnabled;
	}

    // error, log only (no toast)
	public void errorLO(String message, Throwable t) {
        boolean toastState = isErrorToastEnabled;
        isErrorToastEnabled = false;
		error( message, t);
        isErrorToastEnabled = toastState;
	}

    // warn, log only (no toast)
	public void warnLO(String message, Throwable t) {
        boolean toastState = isWarnToastEnabled;
        isWarnToastEnabled = false;
        warn(message,t);
        isWarnToastEnabled = toastState;
	}
    
    // info, log only (no toast)
	public void infoLO(String message, Throwable t) {
        boolean toastState = isInfoToastEnabled;
        isInfoToastEnabled = false;
        info(message,t);
        isInfoToastEnabled = toastState;
	}
    
    // debug, log only (no toast)
	public void debugLO(String message, Throwable t) {
        boolean toastState = isDebugToastEnabled;
        isDebugToastEnabled = false;
        debug(message,t);
        isDebugToastEnabled = toastState;
	}

    protected void toast( String message) 
    {
        try {
            if (toastContext != null) Toast.makeText(toastContext, message, Toast.LENGTH_LONG).show();
        }
        catch (Throwable t) {
            Log.e(getCategory(), message, t);
        }
    }

    String formatMessage( String message) {
        if (LogManager.isCategoryOverridden()) {
            return category + "- " + message;
        } else {
            return message;
        }
    }

	@Override
	protected void write(String level, String category, String message, Throwable t) {
        super.write(level, category, formatMessage(message), t);
		if (ERROR.equals(level) && isErrorToastEnabled) toast( message);
        else if (DEBUG.equals(level) && isDebugToastEnabled) toast(message);
		else if (WARN.equals(level) && isWarnToastEnabled) toast(message);
		else if (INFO.equals(level) && isInfoToastEnabled) toast( message);
	}

	@Override
	public boolean isDebugEnabled() {
		boolean enabled = Log.isLoggable(getCategory(), Log.DEBUG);
		return enabled;
	}

	@Override
	public boolean isErrorEnabled() {
		return Log.isLoggable(getCategory(), Log.ERROR);
	}

	@Override
	public boolean isInfoEnabled() {
		return Log.isLoggable(getCategory(), Log.INFO);
	}

	@Override
	public boolean isWarnEnabled() {
		return Log.isLoggable(getCategory(), Log.WARN);
	}

	
}
