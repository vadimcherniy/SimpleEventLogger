package spring.loggers;


import spring.beans.Event;

import java.util.List;

public class CombinedEventLogger implements EventLogger {
    private List<EventLogger> loggerList;

    public CombinedEventLogger(List<EventLogger> loggerList) {
        this.loggerList = loggerList;
    }

    public void logEvent(Event event) {
        for(EventLogger eventLogger : loggerList){
            eventLogger.logEvent(event);
        }
    }
}
