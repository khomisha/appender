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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Custom appender implementation
 *
 */
public class CustomAppenderImpl {
    private static final CustomAppenderImpl INSTANCE = new CustomAppenderImpl( );
    
    private List< LogEventListener > listeners;
    private List< LogEvent > eventCache;

    private CustomAppenderImpl( ) {
        listeners = new ArrayList< >( );
        eventCache = new ArrayList< >( );
    }

    /**
     * @return the appender instance
     */
    public static CustomAppenderImpl getInstance( ) {
        return( INSTANCE );
    }

    /**
     * Appends logging event
     *  
     * @param le the logging event
     */
    public void append( LoggingEvent le ) {
        LogEvent event = new LogEvent( 
        	le.getLevel( ), 
        	le.getMessage( ), 
        	new Date( le.getTimeStamp( ) ), 
        	le.getThrowableInformation( )
        );
        if( listeners.isEmpty( ) ) {
            eventCache.add( event );
       } else {
			for( LogEventListener listener : listeners ) {
			    listener.handle( event );
			}
        }
    }

    /**
     * Adds log event listener
     * 
     * @param listener the listener to add
     */
    public void addListener( LogEventListener listener ) {
        listeners.add( listener );
        if( listeners.size( ) == 1 && !eventCache.isEmpty( ) ) {
            sendAndClearCache( );
        }
    }

    /**
     * Removes specified log event listener
     * 
     * @param listener the listener to remove
     */
    public void removeListener( LogEventListener listener ) {
        listeners.remove( listener );
    }

    /**
     * Removes all listeners
     */
    public void removeListeners( ) {
    	listeners.clear( );
    }
    
    /**
     * Sends accumulated events and clear vent cache
     */
    synchronized private void sendAndClearCache( ) {
        for( LogEvent event : eventCache ) {
        	listeners.get( 0 ).handle( event );
        }
        eventCache.clear( );
    }
}
