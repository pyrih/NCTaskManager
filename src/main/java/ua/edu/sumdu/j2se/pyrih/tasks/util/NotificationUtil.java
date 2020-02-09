package ua.edu.sumdu.j2se.pyrih.tasks.util;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.pyrih.tasks.model.Task;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;

/**
 * Util class for a Notification manager.
 */
public class NotificationUtil {
    private static final Logger logger = Logger.getLogger(NotificationUtil.class);

    /**
     * Generates html-format string for email message body.
     *
     * @param calendar map contains an incoming task.
     * @return string contains body message in html format.
     */
    public static String getHTMLContent(SortedMap<LocalDateTime, Set<Task>> calendar) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        StringBuilder h = new StringBuilder();
        h.append("<html><body>" +
                "<h2><b> Tasks for the next hour: </b></h2><br>" +
                "<table bordercolor=\"black\" border=\"1\" cellpadding=\"7\">");
        h.append("<th> Date: </th>").append("<th> Tasks: </th>");
        for (Map.Entry<LocalDateTime, Set<Task>> entry : calendar.entrySet()) {
            h.append("<tr>");
            h.append("<td> ").append(entry.getKey().format(formatter)).append(" </td>");
            h.append("<td> ");
            for (Task task : entry.getValue()) {
                h.append(task.getTitle()).append("<br>");
            }
            h.append(" </td>").append("</tr>");
        }
        h.append("</table></body></html>");
        return h.toString();
    }

    /**
     * Returns set of mail properties.
     *
     * @return set of mail properties.
     */
    public static Properties getConfigProperties() {
        Properties props = new Properties();
        InputStream fis = null;
        try {
            URL url = NotificationUtil.class.getResource("/mail.properties");
            fis = url.openStream();
            props.load(fis);
            logger.info("Property configuration file was successfully downloaded.");
        } catch (IOException e) {
            logger.error("Cannot find property file: " + fis, e);
        }
        return props;
    }
}
