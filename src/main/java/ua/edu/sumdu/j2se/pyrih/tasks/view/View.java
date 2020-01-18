package ua.edu.sumdu.j2se.pyrih.tasks.view;

import ua.edu.sumdu.j2se.pyrih.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.pyrih.tasks.model.Task;

import java.time.LocalDateTime;

public interface View {
    void print(String prompt);
    void println(String prompt);

    void showMenu();
    void showEditMenu();

    void showTaskList(AbstractTaskList model);
    int selectTask(AbstractTaskList list);
    int removeTask(AbstractTaskList model);
    void getCalendar(AbstractTaskList list);

    String getTitle();
    boolean getActiveStatus();
    int getIsTaskRepeated();
    int getInterval();
    LocalDateTime parseDateTime();
    void displayTaskInfo(Task task);




}
