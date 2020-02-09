package ua.edu.sumdu.j2se.pyrih.tasks.util;

import ua.edu.sumdu.j2se.pyrih.tasks.model.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * Util class for a Notification manager.
 */
public class NotificationUtil {

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
}
