package ua.edu.sumdu.j2se.pyrih.tasks.model;

import org.apache.log4j.Logger;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task implements Cloneable, Serializable {
    private static final Logger logger = Logger.getLogger(Task.class);

    private String title;
    private LocalDateTime time;
    private LocalDateTime start;
    private LocalDateTime end;
    private int interval;
    private boolean active;

    /**
     * Constructor creates inactive and non-repeating task.
     *
     * @param title is a string that contains a task title.
     * @param time  defines a specified time to run task.
     */
    public Task(String title, LocalDateTime time) throws IllegalArgumentException, NullPointerException {
        if (title == null) {
            logger.error("Title is null.");
            throw new NullPointerException("Title must not be null.");
        } else if (title.isEmpty() || time == null) {
            logger.error("Illegal argument.");
            throw new IllegalArgumentException("Illegal argument.");
        }
        this.title = title;
        this.time = time;
        this.active = false;
        this.interval = 0;
    }

    /**
     * Constructor creates inactive and repeating task.
     *
     * @param title    is a string that contains task title.
     * @param start    set a specified time to start running task.
     * @param end      set a specified time to end running task.
     * @param interval set period of time between repeating task.
     */
    public Task(String title, LocalDateTime start, LocalDateTime end, int interval) throws IllegalArgumentException, NullPointerException {
        if (title.isEmpty()) {
            logger.error("Title is empty.");
            throw new IllegalArgumentException("Illegal argument.");
        }
        if (start.isAfter(end)) {
            logger.error("Illegal arguments.");
            throw new IllegalArgumentException("Illegal arguments.");
        }

        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
        this.active = false;
    }

    /**
     * Gets task title.
     *
     * @return task title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets task title.
     *
     * @param title is a given name of task.
     */
    public void setTitle(String title) throws IllegalArgumentException {
        if (title == null || title.isEmpty()) {
            logger.error("Illegal title argument.");
            throw new IllegalArgumentException("Title argument is illegal.");
        }
        this.title = title;
    }

    /**
     * Returns task status.
     *
     * @return true if task is active.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets task status.
     *
     * @param active is a boolean value (true if task is active, false - inactive).
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Returns task time.
     *
     * @return task time or start task time for repeating task.
     */
    public LocalDateTime getTime() {
        if (this.interval > 0) {
            return this.start;
        } else {
            return this.time;
        }
    }

    /**
     * Sets task time to start non-repeating task.
     * If task was repeating made it non-repeating.
     *
     * @param time is a specified time to start non-repeating task.
     */
    public void setTime(LocalDateTime time) {
        if (time == null) {
            throw new IllegalArgumentException("Illegal argument");
        }
        if (isRepeated()) {
            start = time;
            end = time;
            interval = 0;
        }
        this.time = time;
    }

    /**
     * Returns start time for repeating task.
     *
     * @return start time.
     */
    public LocalDateTime getStartTime() {
        if (isRepeated()) {
            return start;
        }
        return time;
    }

    /**
     * Returns end time for repeating task.
     * If task non-repeating returns time.
     *
     * @return end time.
     */
    public LocalDateTime getEndTime() {
        if (isRepeated()) {
            return end;
        }
        return time;
    }

    /**
     * Returns time interval of task repetition.
     * If task non-repeating than returns 0;
     *
     * @return time interval
     */
    public int getRepeatInterval() {
        if (start != end) {
            return interval;
        }
        return 0;
    }

    /**
     * Sets time to repeating task.
     *
     * @param start    set a specified time to start running task.
     * @param end      set a specified time to end running task.
     * @param interval set time period between repeating task.
     */
    public void setTime(LocalDateTime start, LocalDateTime end, int interval) {
        if (interval <= 0) {
            throw new IllegalArgumentException("Illegal arguments.");
        }
        this.time = start;
        this.start = start;
        this.end = end;
        this.interval = interval;
    }

    /**
     * Returns boolean value that shows task is repeated or not.
     *
     * @return true if task is repeated.
     */
    public boolean isRepeated() {
        return interval > 0;
    }

    /**
     * Returns next executing time of active task after current time.
     *
     * @param current is current time.
     * @return next time point. If task have no next time returns null.
     */
    public LocalDateTime nextTimeAfter(LocalDateTime current) {
        if (active && !isRepeated()) {
            if (current.isBefore(time)) {
                return time;
            }
            return null;
        }

        if (active) {
            isRepeated();
            LocalDateTime nextTime = start;
            if (current.isBefore(start)) {
                return start;
            } else if (current.isAfter(end)) {
                return null;
            } else {
                while (nextTime.isBefore(end) || nextTime.isEqual(end)) {
                    if (nextTime.isAfter(current)) {
                        return nextTime;
                    }
                    nextTime = nextTime.plusSeconds(interval);
                }
            }
        }
        return null;
    }


    /**
     * Comparing this instance of Task to another.
     *
     * @param o takes an object to comparing
     * @return true if both objects are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Task task = (Task) o;
        return time == task.time
                && start == task.start
                && end == task.end
                && interval == task.interval
                && active == task.active
                && Objects.equals(title, task.title);
    }

    /**
     * Overrided hashCode() method.
     *
     * @return object's hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(title, time, start, end, interval, active);
    }

    /**
     * Overrided clone() method that returns cloned task.
     *
     * @return cloned task
     * @throws CloneNotSupportedException
     */
    @Override
    public Task clone() throws CloneNotSupportedException {
        return (Task) super.clone();
    }

    /**
     * Overrided toString() method.
     *
     * @return string that describe object Task.
     */
    @Override
    public String toString() {
        return "Task{"
                + "title='" + title + '\''
                + ", time=" + time
                + ", start=" + start
                + ", end=" + end
                + ", interval=" + interval
                + ", active=" + active
                + '}';
    }
}
