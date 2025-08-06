package kellinwood.logging.android;


import kellinwood.logging.LogManager;

/**
  * User: ken
  * Date: 1/8/13
  */
public class AndroidLogManager extends LogManager {

    static {
        setLoggerFactory( AndroidLoggerFactory.getInstance());
        addLogWriter(DEFAULT_WRITER, new AndroidLogWriter());
        //addLogWriter("FileWriter", new AndroidLogfileWriter( new File( Environment.getExternalStorageDirectory(), "MyApp.log")));
    }

    public static AndroidLogger getAndroidLogger( String name) {
        return (AndroidLogger)getLogger(name);
    }

    public static AndroidLogger getAndroidLogger( Class clazz) {
        return (AndroidLogger)getLogger(clazz);
    }
}
