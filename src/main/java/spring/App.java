package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import spring.beans.Client;
import spring.beans.Event;
import spring.loggers.EventLogger;
import spring.loggers.EventType;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class App {

    @Autowired
    private Client client;

    @Value("Hi to every one with @Autowired!")
    private String startupMessage;

    @Autowired
    @Qualifier("consoleEventLoggerLogger")
    private EventLogger defaultLogger;

    //@Resource(name="loggerMap")
    @Value("#{loggerMap}")
    private Map<EventType, EventLogger> loggers;

    /*public App(Client client, EventLogger cel, Map<EventType, EventLogger> loggers) {
        this.client = client;
        this.defaultLogger = cel;
        this.loggers = loggers;
    }*/

    public static void main(String[] args) {
        ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring.xml");
        App app = appCtx.getBean(App.class);

        System.out.println(app.startupMessage);

        Client client = appCtx.getBean(Client.class);
        System.out.println("Client says: " + client.getGreeting());

        Event event = appCtx.getBean(Event.class);
        app.logEvent(EventType.INFO, event, "Some event for 1");

        event = appCtx.getBean(Event.class);
        app.logEvent(EventType.ERROR, event, "Some event for 2");

        event = appCtx.getBean(Event.class);
        app.logEvent(null, event, "Some event for 3");

        appCtx.close();
    }

    private void logEvent(EventType eventType, Event event, String msg){
        String message = msg.replaceAll(client.getId(), client.getName());
        event.setMessage(message);
        EventLogger logger = loggers.get(eventType);
        if(logger == null) logger = defaultLogger;
        logger.logEvent(event);
    }

    public void setStartupMessage(String startupMessage) {
        this.startupMessage = startupMessage;
    }

    public EventLogger getDefaultLogger() {
        return defaultLogger;
    }

}
