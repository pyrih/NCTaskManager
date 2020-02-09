package ua.edu.sumdu.j2se.pyrih.tasks.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Class to work with task collections.
 * The class allows you to use any collection to store tasks.
 */
public class Tasks implements Serializable {

    /**
     * The method that returns a subset of tasks that are scheduled
     * to run at least once after "from" and no later than "to".
     *
     * @param tasks collection of tasks.
     * @param start start time of a specified period.
     * @param end   end time of a specified period.
     * @return returns a subset of tasks.
     */
    public static Iterable<Task> incoming(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {
        AbstractTaskList result = TaskListFactory.createTaskList(ListTypes.types.ARRAY);

        for (Task task : tasks) {
            if (task.nextTimeAfter(start) != null
                    && task.nextTimeAfter(start).compareTo(end) <= 0) {
                Objects.requireNonNull(result).add(task);
            }
        }
        return result;
    }

    /**
     * The static method builds a task calendar for a given period - a table
     * where each date corresponds to a set of tasks to be completed at that time,
     * with one task meeting several dates if it has to be performed several times
     * during the specified period.
     *
     * @param tasks task collection.
     * @param start start time of a specified period.
     * @param end end time of a specified period.
     * @return returns map where each date corresponds to a set of tasks to be completed at that time.
     */
    public static SortedMap<LocalDateTime, Set<Task>> calendar(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {
        SortedMap<LocalDateTime, Set<Task>> sortedMap = new TreeMap<>();
        Iterable<Task> taskIterable = incoming(tasks, start, end);

        for (Task task : taskIterable) {
            LocalDateTime nextTime = task.nextTimeAfter(start);
            while (nextTime != null
                    && (nextTime.isBefore(end) || nextTime.isEqual(end))) {
                if (!sortedMap.containsKey(nextTime)) {
                    Set<Task> taskSet = new HashSet<>();
                    taskSet.add(task);
                    sortedMap.put(nextTime, taskSet);
                } else {
                    sortedMap.get(nextTime).add(task);
                }
                nextTime = task.nextTimeAfter(nextTime);
            }
        }
        return sortedMap;
    }
}
