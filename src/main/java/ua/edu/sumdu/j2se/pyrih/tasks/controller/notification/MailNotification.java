package ua.edu.sumdu.j2se.pyrih.tasks.controller.notification;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.pyrih.tasks.model.Task;
import ua.edu.sumdu.j2se.pyrih.tasks.util.NotificationUtil;
import ua.edu.sumdu.j2se.pyrih.tasks.util.PropertyLoader;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;

/**
 * Provides an implementation for sending notifications to a user by email via TLS protocol.
 */
public class MailNotification implements Notification {
    private static final Logger logger = Logger.getLogger(MailNotification.class);
    private static final MailNotification MAIL_NOTIFICATION = new MailNotification();
    private Session session;
    private Properties properties;

    private MailNotification() {
        properties = PropertyLoader.getProperties("/mail.properties");
        session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(properties.getProperty("username"),
                        properties.getProperty("password"));
            }
        });

    }

    /**
     * Singleton.
     *
     * @return MailNotification instance.
     */
    public static MailNotification getInstance() {
        return MAIL_NOTIFICATION;
    }

    /**
     * Sends email message to the user.
     *
     * @param calendar map contains an incoming tasks in selected period.
     */
    @Override
    public void send(SortedMap<LocalDateTime, Set<Task>> calendar) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(properties.getProperty("from")));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(properties.getProperty("recipients")));
            message.setSubject("Task Manager. You have upcoming tasks!");
            message.setContent(NotificationUtil.getHTMLContent(calendar), "text/html");
            message.setSentDate(new Date());
            Transport.send(message);
            logger.info("Tasks for the next hour is sent on email: " + properties.getProperty("recipients"));
        } catch (MessagingException e) {
            logger.error("Failed to send email.");
        }
    }
}
