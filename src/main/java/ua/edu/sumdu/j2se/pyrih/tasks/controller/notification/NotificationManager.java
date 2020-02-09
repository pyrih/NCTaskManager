package ua.edu.sumdu.j2se.pyrih.tasks.controller.notification;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.pyrih.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.pyrih.tasks.model.Task;
import ua.edu.sumdu.j2se.pyrih.tasks.model.Tasks;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedMap;

public class NotificationManager extends Thread {
    private static final Logger logger = Logger.getLogger(MailNotification.class);
    private final static long TIMER = 300000; // 5 MIN
    private AbstractTaskList list;
    private Notification notification;

    public NotificationManager(AbstractTaskList list) {
        this.list = list;
        notification = new MailNotification();
    }

    /**
     *
     */
    @Override
    public void run() {
        SortedMap<LocalDateTime, Set<Task>> map;
        while (true) {
            map = Tasks.calendar(list, LocalDateTime.now(), LocalDateTime.now().plusHours(1));
            if (map.isEmpty()) {
                logger.info("Map is empty! Calendar has no tasks for the next period.");
            } else {
                notification.send(map);
            }
            try {
                Thread.sleep(TIMER);
            } catch (InterruptedException e) {
                e.printStackTrace();
                logger.error("Interrupted exception.", e);
            }
        }
    }
}
