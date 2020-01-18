package ua.edu.sumdu.j2se.pyrih.tasks.controller.notification;

import ua.edu.sumdu.j2se.pyrih.tasks.model.Task;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedMap;

public interface Notification {
    void send(SortedMap<LocalDateTime, Set<Task>> calendar);
}
