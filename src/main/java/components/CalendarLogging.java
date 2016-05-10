package components;

import org.apache.log4j.Appender;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import java.io.IOException;

public class CalendarLogging {
    Logger logger;

    public CalendarLogging(String nameOfFile) {
        Layout lay = new PatternLayout("[%p] %c - %m - Data wpisu: %d %n");
        Appender app = null;
        try {
            app = new FileAppender(lay, "C:/Users/Pzmg/Desktop/Java projects/Calendar/" + nameOfFile + ".txt");
        } catch (IOException ex) {
            System.out.println("blad przy tworzeniu pliku");
        }
        BasicConfigurator.configure(app);
        logger = Logger.getRootLogger();
        logger.setLevel(Level.OFF);
    }

    public void sendLog(String messageAboutClick) {
        logger.setLevel(Level.INFO);
        logger.info(messageAboutClick);
        logger.setLevel(Level.OFF);
    }
}
