package ua.edu.sumdu.j2se.pyrih.tasks;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.pyrih.tasks.controller.Controller;
import ua.edu.sumdu.j2se.pyrih.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.pyrih.tasks.model.ListTypes;
import ua.edu.sumdu.j2se.pyrih.tasks.model.TaskIO;
import ua.edu.sumdu.j2se.pyrih.tasks.model.TaskListFactory;
import ua.edu.sumdu.j2se.pyrih.tasks.view.ConsoleView;
import ua.edu.sumdu.j2se.pyrih.tasks.view.View;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);
    private AbstractTaskList list = TaskListFactory.createTaskList(ListTypes.types.ARRAY);

    /**
     * Main method. Execution of the program starts here.
     *
     * @param args the command line arguments.
     */
    public static void main(String[] args) {

/*        Properties properties = new Properties();
        try {
            //properties.load(Main.class.getResource("/mail.properties").openStream());
            properties.load(Main.class.getResource("/log4j.properties").openStream());

        } catch (IOException e) {
            e.printStackTrace();
        }*/

        Main taskManager = new Main();
        taskManager.launch();
    }

    public void launch() {
        load(list);
        View view = new ConsoleView();
        Controller controller = new Controller(list, view);
        controller.execute();
        save();
        logger.info("The program is finished by user.");
    }

    private void load(AbstractTaskList list) {
        Path currentPath = FileSystems.getDefault()
                .getPath("data.txt").toAbsolutePath();
        if (currentPath.toFile().exists()) {
            TaskIO.readText(list, new File(String.valueOf(currentPath.toFile())));
            logger.info("List of tasks is loaded from file: data.txt");
        } else {
            logger.error("File: \"data.txt\" not found.");
        }
    }

    private void save() {
        Path currentPath = FileSystems.getDefault().getPath("data.txt").toAbsolutePath();
        try {
            Files.deleteIfExists(currentPath);
        } catch (IOException e) {
            logger.error("File not found: " + e);
        }
        TaskIO.writeText(list, new File(String.valueOf(currentPath.toFile())));
        logger.info("List of tasks is saved to file: data.txt");
    }
}
