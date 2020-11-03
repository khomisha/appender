/* 
 * Copyright 2020 Mikhail Khodonov.
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

package org.homedns.mkh.appender;

import java.util.Date;
import org.apache.log4j.Level;
import org.apache.log4j.spi.ThrowableInformation;

/**
 * The log4j event wrapper
 *
 */
public class LogEvent {
    private Level level;
    private Object message;
    private Date timestamp;
    private Throwable exception;

    public LogEvent( ) {
    }

    /**
     * @param level the event level
     * @param message the message
     * @param timestamp the event timestamp
     * @param exInfo the throwable information if any
     */
    public LogEvent( Level level, Object message, Date timestamp, ThrowableInformation exInfo ) {
        this.level = level;
        this.message = message;
        this.timestamp = timestamp;
        this.exception = ( exInfo != null ) ? exInfo.getThrowable( ) : null;
    }

    /**
     * @return the event level
     */
    public Level getLevel( ) {
        return( level );
    }

    /**
     * @return the message
     */
    public Object getMessage( ) {
        return( message );
    }

    /**
     * @return the timestamp
     */
    public Date getTimestamp( ) {
        return( timestamp );
    }

	/**
	 * @return the exception or null
	 */
	public Throwable getException( ) {
		return( exception );
	}
}
