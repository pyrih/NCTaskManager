package ua.edu.sumdu.j2se.pyrih.tasks;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.pyrih.tasks.controller.Controller;
import ua.edu.sumdu.j2se.pyrih.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.pyrih.tasks.model.ListTypes;
import ua.edu.sumdu.j2se.pyrih.tasks.model.TaskIO;
import ua.edu.sumdu.j2se.pyrih.tasks.model.TaskListFactory;
import ua.edu.sumdu.j2se.pyrih.tasks.view.ConsoleView;
import ua.edu.sumdu.j2se.pyrih.tasks.view.View;

/**
 * Console-based task manager realized via MVC pattern.
 */
public class TaskManager {
    public final static String DATA_JSON_PATH = "data.json";
    private static final Logger logger = Logger.getLogger(TaskManager.class);
    private AbstractTaskList list;

    /**
     * Constructs app, inits array task list.
     */
    public TaskManager() {
        this.list = TaskListFactory.createTaskList(ListTypes.types.ARRAY);
    }

    /**
     * Runs task manager. Deserializes task list from data.txt
     * When the application is correctly finished saves (serializes)
     * task list to the data.txt file.
     */
    public void launch() {
        TaskIO.loadFromFileStorage(list, DATA_JSON_PATH);
        View view = new ConsoleView();
        Controller controller = new Controller(list, view);
        controller.execute();
        TaskIO.saveToFileStorage(list, DATA_JSON_PATH);
        logger.info("The program is finished by user.");
    }
}
