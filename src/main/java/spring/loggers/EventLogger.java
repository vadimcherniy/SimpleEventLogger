package spring.loggers;

import spring.beans.Event;

public interface EventLogger {
    void logEvent(Event event);
}
