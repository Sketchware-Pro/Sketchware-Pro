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

public abstract class Logger {

	public static final String ERROR = "ERROR";
	public static final String WARN = "WARN";
	public static final String INFO = "INFO";
	public static final String DEBUG = "DEBUG";

	public abstract boolean isErrorEnabled();
	public abstract void error( String message);
	public abstract void error( String message, Throwable t);


	public abstract boolean isWarnEnabled();
	public abstract void warn( String message);
	public abstract void warn( String message, Throwable t);

	public abstract boolean isInfoEnabled();
	public abstract void info( String message);
	public abstract void info( String message, Throwable t);

	public abstract boolean isDebugEnabled();
	public abstract void debug( String message);
	public abstract void debug( String message, Throwable t);


    public void error( Object message) { error( toStringOrNull(message) ); }
    public void error( Object message, Throwable t) { error (toStringOrNull(message), t); }

    public void warn( Object message) { warn( toStringOrNull(message) );}
    public void warn( Object message, Throwable t) { warn(toStringOrNull(message), t);}

    public void info( Object message) { info( toStringOrNull(message) );}
    public void info( Object message, Throwable t) { info( toStringOrNull(message), t);}

    public void debug( Object message) { debug( toStringOrNull(message) ); }
    public void debug( Object message, Throwable t) { debug( toStringOrNull(message), t); }

	public static Logger getLogger( String name) {
        return LogManager.getLogger(name);
    }
    public static Logger getLogger( Class clazz) {
        return LogManager.getLogger(clazz);
    }

    private String toStringOrNull(Object message) {
        return (message != null) ? message.toString() : null;
    }
}
