package ua.edu.sumdu.j2se.pyrih.tasks.controller.notification;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.pyrih.tasks.model.Task;
import ua.edu.sumdu.j2se.pyrih.tasks.util.NotificationUtil;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;

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

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(props.getProperty("set.from")));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(props.getProperty("set.recipients")));
            message.setSubject("Task Manager. You have upcoming tasks!");

            String htmlContent = NotificationUtil.getHTMLContent(calendar);
            message.setContent(htmlContent, "text/html");
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
