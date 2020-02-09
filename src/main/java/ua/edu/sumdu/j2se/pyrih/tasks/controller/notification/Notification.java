package ua.edu.sumdu.j2se.pyrih.tasks.controller.notification;

import ua.edu.sumdu.j2se.pyrih.tasks.model.Task;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedMap;

/**
 * Describes notification.
 */
public interface Notification {

    /**
     * Represents notification sending to the task manager user.
     *
     * @param calendar map contains tasks in selected time period.
     */
    void send(SortedMap<LocalDateTime, Set<Task>> calendar);
}
