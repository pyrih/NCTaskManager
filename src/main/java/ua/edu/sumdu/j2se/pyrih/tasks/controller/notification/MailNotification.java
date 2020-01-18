package ua.edu.sumdu.j2se.pyrih.tasks.controller.notification;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.pyrih.tasks.model.Task;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MailNotification implements Notification {
    private static final Logger logger = Logger.getLogger(MailNotification.class);

    @Override
    public void send(SortedMap<LocalDateTime, Set<Task>> calendar) {
        Properties props = getConfigProperties();
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(props.getProperty("mail.smtp.username"),
                                props.getProperty("mail.smtp.password"));
                    }
                });
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
//            StringBuilder builder = new StringBuilder();

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(props.getProperty("set.from")));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(props.getProperty("set.recipients")));
            message.setSubject("Task Manager. You have upcoming tasks!");

/*            builder.append("Tasks for the next hour: ").append("\n").append("\n");

            for (Map.Entry<LocalDateTime, Set<Task>> entry : calendar.entrySet()) {
                String date = entry.getKey().format(formatter);
                builder.append("# Date: ").append(date).append("\n");
                for (Task task : entry.getValue()) {
                    builder.append("Task: ").append(task.getTitle()).append("\n");
                } builder.append("\n");
            }*/

            StringBuilder h = new StringBuilder();
            h.append("<html><body>" +
                    "<h2><b> Tasks for the next hour: </b></h2><br>" +
                    "<table bordercolor=\"black\" border=\"1\" cellpadding=\"7\">");
            h.append("<th> Date: </th>");
            h.append("<th> Tasks: </th>");
            for (Map.Entry<LocalDateTime, Set<Task>> entry : calendar.entrySet()) {
                h.append("<tr>");
                h.append("<td> " + entry.getKey().format(formatter) + " </td>");
                h.append("<td> ");
                for (Task task : entry.getValue()) {
                    h.append(task.getTitle()).append("<br>");
                }
                h.append(" </td>");
                h.append("</tr>");
            }
            h.append("</table></body></html>");
            String html = h.toString();

            // message.setText(builder.toString());

            message.setContent(html, "text/html");

            message.setSentDate(new Date());
            Transport.send(message);
            logger.info("Tasks for the next hour is sent on email: " + props.getProperty("set.recipients"));
        } catch (MessagingException e) {
            logger.error("Failed to send email.");
        }
    }

    private Properties getConfigProperties() {
        Properties props = new Properties();
        InputStream fis = null;
        try {
            URL url = getClass().getResource("/mail.properties");
            fis = url.openStream();
            //fis = new FileInputStream("mail.properties");
            props.load(fis);
            logger.info("Property configuration file was successfully downloaded.");
        } catch (IOException e) {
            logger.error("Cannot find property file: " + fis, e);
        }
        return props;
    }
}
