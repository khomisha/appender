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

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Custom appender implementation wrapper
 * How to use:
 * 1. implements log event handler and adds listener, e.g.
 * <pre>
 * <code> 
 * CustomAppenderImpl.getInstance( ).addListener( this::onLogEvent );
 * 			...
 *	private void onLogEvent( LogEvent event ) {
 *		try( 
 *			StringWriter sw = new StringWriter( );
 *			PrintWriter pw = new PrintWriter( sw ) 
 *		) {
 *			event.getThrowable( ).printStackTrace( pw );
 *			String sStack = sw.toString( );
 *			textIO.getTextTerminal( ).println( String.valueOf( event.getTimestamp( ) ) + " " + event.getMessage( ) + " " + sStack );
 *		}
 *		catch( IOException e ) {
 *			LOG.error( e.getMessage( ), e );
 *		}
 *	}
 * </code> 
 * </pre>
 * 2. defines appender in log4j.properties, e.g.
 * <pre>
 * <code>
 * log4j.rootLogger=ERROR, textio, file
 *          ... 
 * log4j.appender.textio=org.homedns.mkh.appender.CustomAppender 
 * </code> 
 * </pre>
 */
public class CustomAppender extends AppenderSkeleton {
    private CustomAppenderImpl appender = CustomAppenderImpl.getInstance( );
    
    /**
     * @see org.apache.log4j.AppenderSkeleton#append(org.apache.log4j.spi.LoggingEvent)
     */
    @Override
    protected void append( LoggingEvent event ) {
        appender.append( event );
    }

    /**
     * @see org.apache.log4j.Appender#close()
     */
    @Override
    public void close( ) {
    	appender.removeListeners( );
    }

    /**
     * @see org.apache.log4j.Appender#requiresLayout()
     */
    @Override
    public boolean requiresLayout( ) {
        return( false );
    }
}
