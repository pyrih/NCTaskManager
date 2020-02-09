package ua.edu.sumdu.j2se.pyrih.tasks.view;

import ua.edu.sumdu.j2se.pyrih.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.pyrih.tasks.model.Task;

import java.time.LocalDateTime;

/**
 * View
 */
public interface View {

    /**
     * Displays message.
     *
     * @param prompt a message to be displayed to the user.
     */
    void print(String prompt);

    /**
     * Displays message and then terminate the line.
     *
     * @param prompt a message to be displayed to the user.
     */
    void println(String prompt);

    /**
     * Displays a main menu.
     */
    void showMenu();

    /**
     * Displays a menu to editing task.
     */
    void showEditMenu();

    /**
     * Displays task list.
     *
     * @param model task list.
     */
    void showTaskList(AbstractTaskList model);

    /**
     * Displays message to select task and return a number of
     * selected task to a controller.
     *
     * @param list task list.
     * @return index of element in task list.
     */
    int selectTask(AbstractTaskList list);

    /**
     * Displays message to remove task and requests a number of
     * task to delete. Returns number of element to a controller.
     *
     * @param model task list.
     * @return index of element in task list.
     */
    int removeTask(AbstractTaskList model);

    /**
     * Displays date request for a calendar. Then shows the calendar
     * to selected period.
     *
     * @param list task list.
     */
    void getCalendar(AbstractTaskList list);

    /**
     * Used to get title from representation.
     *
     * @return string title into a controller.
     */
    String getTitle();

    /**
     * Used to get activity status from representation.
     *
     * @return boolean. True if task is active.
     */
    boolean getActiveStatus();

    /**
     * Used to get task repetition status from representation.
     *
     * @return an integer value into a controller.
     */
    int getIsTaskRepeated();

    /**
     * Used to get time interval in minutes from representation.
     *
     * @return an integer value in minutes into a controller.
     */
    int getInterval();

    /**
     * Used to get date time from representation.
     *
     * @return date time.
     */
    LocalDateTime parseDateTime();

    /**
     * Displays an information about task to representation.
     *
     * @param task task object.
     */
    void displayTaskInfo(Task task);


}
