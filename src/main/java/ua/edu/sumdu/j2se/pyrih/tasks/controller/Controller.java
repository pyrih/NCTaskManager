package ua.edu.sumdu.j2se.pyrih.tasks.controller;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.pyrih.tasks.controller.notification.NotificationManager;
import ua.edu.sumdu.j2se.pyrih.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.pyrih.tasks.model.Task;
import ua.edu.sumdu.j2se.pyrih.tasks.view.View;

import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * Responses for the relationship between model and view.
 * Starts Notification manager in daemon thread.
 */
public class Controller {
    private static final Logger logger = Logger.getLogger(Controller.class);
    private AbstractTaskList model;
    private View view;
    private Scanner scanner;

    /**
     * @param list model (task list).
     * @param view representation.
     */
    public Controller(AbstractTaskList list, View view) {
        this.model = list;
        this.view = view;
        scanner = new Scanner(System.in);
        NotificationManager notifications = new NotificationManager(list);
        notifications.setDaemon(true);
        notifications.start();
    }

    /**
     * Method starts task manager working.
     */
    public void execute() {
        while (true) {
            view.showMenu();
            String line = scanner.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(line);
            } catch (Exception e) {
                continue;
            }
            if (choice == 0) {
                view.print("Do you want to exit? (Yes/No): ");
                if (checkUserAnswer()) {
                    view.println("Program has been completed!");
                    break;
                }
            } else {
                switch (choice) {
                    case 1:
                        getList();
                        break;
                    case 2:
                        addTask();
                        break;
                    case 3:
                        editTask();
                        break;
                    case 4:
                        removeTask();
                        break;
                    case 5:
                        getCalendar();
                        break;
                    default:
                }
            }
        }
    }

    /**
     * Gets task list from model and represents into view.
     */
    public void getList() {
        view.showTaskList(model);
    }

    /**
     * Adds a task to the task list.
     */
    public void addTask() {
        Task task;

        String title = view.getTitle();
        boolean active = view.getActiveStatus();
        int repeated = view.getIsTaskRepeated();
        if (repeated == 2) {
            while (true) {
                view.print("\nStart date of the period: ");
                LocalDateTime start = view.parseDateTime();
                view.print("\nEnd date of the period: ");
                LocalDateTime end = view.parseDateTime();
                view.print("Enter the interval in minutes: ");
                int interval = view.getInterval();

                if (!((start.isAfter(end) || start.isEqual(end)) &&
                        start.plusSeconds(interval).isAfter(end))) {
                    task = new Task(title, start, end, interval);
                    task.setActive(active);
                    model.add(task);
                    view.displayTaskInfo(task);
                    view.print("The task was successfully added to the list.");
                    break;
                } else {
                    view.print("Wrong time period. Try again!");
                }
            }
        } else {
            view.print("\nTask completion time: ");
            LocalDateTime time = view.parseDateTime();
            task = new Task(title, time);
            task.setActive(active);
            model.add(task);
            view.displayTaskInfo(task);
            view.print("The task was successfully added to the list.");
        }
    }

    /**
     * Edits selected task from task list.
     */
    public void editTask() {
        int index = view.selectTask(model) - 1;
        Task task = model.getTask(index);
        LocalDateTime start, end;

        while (true) {
            view.showEditMenu();
            String line = scanner.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(line);
            } catch (Exception e) {
                view.print("Please enter a number: \n");
                continue;
            }

            if (choice == 0) {
                System.out.print("Do you want to return to previous menu? (Yes/No): ");
                if (checkUserAnswer()) {
                    break;
                }
            } else {
                switch (choice) {
                    case 1:
                        task.setTitle(view.getTitle());
                        view.print("Title was changed on: " + task.getTitle());
                        break;
                    case 2:
                        boolean aBoolean = view.getActiveStatus();
                        task.setActive(aBoolean);
                        view.print(" Activity status was changed on: " + task.isActive());
                        break;
                    case 3:
                        task.setTime(view.parseDateTime());
                        view.print("Time was changed on: " + task.getTime());
                        break;
                    case 4:
                        while (true) {
                            view.print("Enter start time: ");
                            start = view.parseDateTime();
                            view.print("Enter end time: ");
                            end = view.parseDateTime();
                            view.print("Enter interval time: ");
                            int interval = view.getInterval();

                            if (!((start.isAfter(end) || start.isEqual(end)) &&
                                    start.plusSeconds(interval).isAfter(end))) {
                                task.setTime(start, end, interval);
                                view.print("Start time was changed on: " + task.getStartTime() +
                                        "\nEnd time was changed on: " + task.getEndTime() +
                                        "\nInterval  was changed on: " + task.getRepeatInterval());
                                break;
                            } else {
                                view.print("Wrong time period. Please try again.\n");
                            }
                        }
                        break;
                    default:
                }
            }
        }

        view.println("The task has been successfully changed!");
    }

    /**
     * Removes selected task from task list.
     */
    public void removeTask() {
        int taskID = view.removeTask(model);
        Task task;

        if (taskID == 0) {
            view.print("The task list is empty! Add at least one task.");
        } else if (taskID == -1) {
            view.print("You did not confirm the deletion of the task.");
        } else if (taskID > 0) {
            task = model.getTask(taskID - 1);
            model.remove(task);
            view.print("Task №: " + taskID + " was deleted from controller!");
        }
    }

    /**
     * Gets task list from model by selected period and represents into view.
     */
    public void getCalendar() {
        view.getCalendar(model);
    }

    private boolean checkUserAnswer() {
        return userAnswer().equalsIgnoreCase("yes");
    }

    private String userAnswer() {
        String userAnswer = scanner.nextLine();
        while (!userAnswer.equalsIgnoreCase("yes") & !userAnswer.equalsIgnoreCase("no")) {
            System.out.print("You should select (Yes/No): ");
            userAnswer = scanner.nextLine();
        }
        return userAnswer;
    }
}
