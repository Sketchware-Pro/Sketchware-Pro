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
package kellinwood.logging.log4j;

import kellinwood.logging.AbstractLogWriter;
import kellinwood.logging.LogManager;
import kellinwood.logging.Logger;
import kellinwood.logging.LoggerFactory;

public class Log4jLoggerFactory implements LoggerFactory {

    private static final boolean isActivated = false;

    private static class Log4jLogWriter extends AbstractLogWriter {
        @Override
        public void writeImpl(String level, String category, String message, Throwable t) {
            org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(category);

            if (Logger.ERROR.equals(level)) {
                if (t != null) log.error(message, t);
                else log.error(message);
            }
            else if (Logger.DEBUG.equals(level)) {
                if (t != null) log.debug(message, t);
                else log.debug(message);
            }
            else if (Logger.WARN.equals(level)) {
                if (t != null) log.warn(message, t);
                else log.warn(message);
            }
            else if (Logger.INFO.equals(level)) {
                if (t != null) log.info(message, t);
                else log.info(message);
            }
        }
    }

    public static void activate() {
        if (isActivated) {
            return;
        }

        LogManager.addLogWriter(LogManager.DEFAULT_WRITER, new Log4jLogWriter());
        LogManager.setLoggerFactory(new Log4jLoggerFactory());
    }

    public Logger getLogger(String category) {
        return new Log4jLogger( category);
    }
}

