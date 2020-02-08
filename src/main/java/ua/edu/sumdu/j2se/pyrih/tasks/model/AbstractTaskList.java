package ua.edu.sumdu.j2se.pyrih.tasks.model;

import java.time.LocalDateTime;
import java.util.stream.Stream;

/**
 * An abstract class describes operations that can be performed with a task list
 * and implements methods that are independent of storage.
 */
public abstract class AbstractTaskList implements Iterable<Task>, Cloneable {

    /**
     * Returns the number of tasks in the list.
     *
     * @return the number of elements in this list.
     */
    public abstract int size();

    /**
     * Returns a task that is in the specified place in the list,
     * the first task has an index of 0.
     *
     * @param index index of the task to return.
     * @return the task at the specified position in this list.
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public abstract Task getTask(int index) throws IndexOutOfBoundsException;

    /**
     * Adds the specified task to the list.
     *
     * @param task element to be appended to this list.
     * @throws IllegalArgumentException {@inheritDoc}
     */
    public abstract void add(Task task) throws IllegalArgumentException;

    /**
     * Removes a task from a list and returns true if such a task was listed.
     * If there are more than one task in the list, it deletes one of them.
     *
     * @param task element to be removed from this list, if present.
     * @return boolean. true if such a task was listed.
     * @throws IllegalArgumentException {@inheritDoc}
     */
    public abstract boolean remove(Task task) throws IllegalArgumentException;

    /**
     * Allows to work with the task list as a stream.
     *
     * @return stream of tasks.
     */
    public abstract Stream<Task> getStream();

    /**
     * Returns a subset of tasks scheduled to be completed at least once after
     * from and no later than to.
     *
     * @param from beginning of gap.
     * @param to   end of gap.
     * @return a subset of tasks.
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
