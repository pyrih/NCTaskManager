package ua.edu.sumdu.j2se.pyrih.tasks.model;

import java.time.LocalDateTime;
import java.util.stream.Stream;

public abstract class AbstractTaskList implements Iterable<Task>, Cloneable {

    public abstract int size();

    public abstract Task getTask(int index) throws IndexOutOfBoundsException;

    public abstract void add(Task task) throws IllegalArgumentException;

    public abstract boolean remove(Task task) throws IllegalArgumentException;

    public abstract Stream<Task> getStream();

    /**
     *
     *
     * @param from
     * @param to
     * @return
     */
    public final AbstractTaskList incoming(LocalDateTime from, LocalDateTime to) {
        AbstractTaskList incomingTasks;
        String clazz = this.getClass().getSimpleName();
        if (clazz.equals("ArrayTaskList")) {
            incomingTasks = TaskListFactory.createTaskList(
                    ListTypes.types.ARRAY);
        } else if (clazz.equals("LinkedTaskList")) {
            incomingTasks = TaskListFactory.createTaskList(
                    ListTypes.types.LINKED);
        } else {
            return null;
        }
        if (incomingTasks != null) {
            this.getStream()
                    .filter(t -> t != null && t.isActive()
                            && t.nextTimeAfter(from).isAfter(from)
                            && t.nextTimeAfter(to).isBefore(to))
                    .forEach(incomingTasks::add);
        }
        return incomingTasks;
    }
}
