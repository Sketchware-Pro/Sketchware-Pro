/*
 * Copyright (C) 2010 Ken Ellinwood.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kellinwood.logging;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class LogManager {

	static LoggerFactory factory = new DeaultLoggerFactory();
	
	static Map<String,Logger> loggers = new TreeMap<String,Logger>();

    static Map<String,LogWriter> logWriters = new HashMap<String,LogWriter>();

    public static final String DEFAULT_WRITER = "DEFAULT";
    static {
        logWriters.put(DEFAULT_WRITER, new StreamWriter(System.out));
    }

    static String overrideCategory = null;

    public static void addLogWriter( String id, LogWriter logWriter) {
        logWriters.put(id, logWriter);
    }

	public static void setLoggerFactory( LoggerFactory f) {
		factory = f;
	}

    /** Allow the logging category to be globally overridden with a single value.   This is helpful on android
     * in order to filter log messages using a single 'tag', i.e., category value.
     * @param category value to use globally.
     */
    public static void overrideCategory( String category) {
        overrideCategory = category;
    }

    public static boolean isCategoryOverridden() {
        return overrideCategory != null;
    }

    public static String getCategory( String category) {
        if (overrideCategory != null) return overrideCategory;
        else return category;
    }

	public static Logger getLogger(String category) {
		
		Logger logger = loggers.get( category);
		if (logger == null) {
			logger = factory.getLogger(category);
			loggers.put( category, logger);
		}
		return logger;
	}

    public static Logger getLogger( Class clazz)
    {
        return getLogger(clazz.getName());
    }

    public static void write( String level, String category, String message, Throwable t) {
        for (LogWriter writer : logWriters.values()) {
            if (writer != null) writer.write( level, category, message, t);
        }
    }

    public static void main(String[] args) {
        Logger log = getLogger(LogManager.class);

        log.info("It works!");
    }
}
