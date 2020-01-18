package ua.edu.sumdu.j2se.pyrih.tasks.model;

public class TaskListFactory {
    /**
     * Method that creates an ArrayTaskList and LinkedTaskList object
     * depending on the parameter passed to it.
     * It based on the design pattern: Abstract factory.
     *
     * @param type contains ARRAY and LINKED values.
     * @return returns an ArrayTaskList or LinkedTaskList object.
     */
    public static AbstractTaskList createTaskList(ListTypes.types type) {
        switch (type) {
            case ARRAY:
                return new ArrayTaskList();
            case LINKED:
                return new LinkedTaskList();
            default:
                return null;
        }
    }
}
