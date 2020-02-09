package ua.edu.sumdu.j2se.pyrih.tasks.model;

import org.apache.log4j.Logger;

import java.util.*;
import java.util.stream.Stream;

/**
 * A class is inherited from AbstractTaskList and implements those
 * abstract operations that depend on the storage method using an array.
 */
public class ArrayTaskList extends AbstractTaskList {
    private static final Logger logger = Logger.getLogger(ArrayTaskList.class);
    private int size;
    private Task[] array;

    /**
     * Constructs an empty list with the specified initial capacity.
     */
    public ArrayTaskList() {
        array = new Task[10];
        size = 0;
    }

    /**
     * {@inheritDoc}
     *
     * @param task element to be appended to this list.
     * @throws IllegalArgumentException
     */
    @Override
    public void add(Task task) throws IllegalArgumentException {
        if (task == null) {
            throw new IllegalArgumentException("Illegal incoming argument. Task must not be null.");
        } else if (size >= array.length) {
            Task[] bigger = new Task[array.length * 2];
            System.arraycopy(array, 0, bigger, 0, array.length);
            array = bigger;
        }
        array[size] = task;
        size++;
    }

    /**
     * {@inheritDoc}
     *
     * @param task element to be removed from this list, if present.
     * @return boolean. true if such a task was listed.
     * @throws IllegalArgumentException
     */
    @Override
    public boolean remove(Task task) throws IllegalArgumentException {
        if (task == null) {
            throw new IllegalArgumentException("Illegal incoming argument. Task must not be null.");
        }
        int index = indexOf(task);
        if (index == -1) {
            return false;
        }
        remove(index);
        return true;
    }

    /**
     * {@inheritDoc}
     *
     * @return the number of elements in this list.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * {@inheritDoc}
     *
     * @param index index of the task to return.
     * @return the task at the specified position in this list.
     * @throws IndexOutOfBoundsException
     */
    @Override
    public Task getTask(int index) throws IndexOutOfBoundsException {
        if (array == null || index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("An array has been accessed with an illegal index");
        }
        return array[index];
    }

    /**
     * {@inheritDoc}
     *
     * @return stream of tasks.
     */
    @Override
    public Stream<Task> getStream() {
        return Stream.of(this.toArray());
    }

    /**
     * Compares the specified object with this task list for equality.
     *
     * @param o the object to be compared for equality with this task list.
     * @return true if this list contained the specified element.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ArrayTaskList that = (ArrayTaskList) o;
        return size == that.size &&
                Arrays.equals(array, that.array);
    }

    /**
     * Returns the hash code value for this list.
     *
     * @return the hash code value for this list.
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(size);
        result = 31 * result + Arrays.hashCode(array);
        return result;
    }

    /**
     * Returns a copy of this instance.
     *
     * @return a clone of this instance.
     * @throws CloneNotSupportedException {@inheritDoc}
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        ArrayTaskList arrayTaskList = (ArrayTaskList) super.clone();
        arrayTaskList.array = Arrays.copyOf(array, size);
        return arrayTaskList;
    }

    /**
     * Returns a string representation of the array task list.
     *
     * @return a string.
     */
    @Override
    public String toString() {
        return new StringJoiner(", ", ArrayTaskList.class.getSimpleName() + "[", "]")
                .add("size=" + size)
                .add("array=" + Arrays.toString(array))
                .toString();
    }

    /**
     * Returns an iterator over elements of type Task.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Task> iterator() {
        return new ArrayListIterator(this);
    }

    private Task[] toArray() {
        return Arrays.copyOf(array, size);
    }

    private int indexOf(Task target) {
        for (int i = 0; i < size; i++) {
            if (equalTasks(target, array[i])) {
                return i;
            }
        }
        return -1;
    }

    private Task remove(int index) {
        Task task = getTask(index);
        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        size--;
        return task;
    }

    private boolean equalTasks(Task target, Task task) {
        if (target == null) {
            return task == null;
        } else {
            return target.equals(task);
        }
    }

    private class ArrayListIterator implements Iterator<Task> {
        private ArrayTaskList list;
        private int index;
        private Task task;

        public ArrayListIterator(ArrayTaskList list) {
            this.list = list;
        }

        @Override
        public boolean hasNext() {
            return index < list.size();
        }

        @Override
        public Task next() {
            if (index >= list.size()) {
                throw new NoSuchElementException();
            }
            task = list.getTask(index);
            index++;
            return task;
        }

        @Override
        public void remove() {
            if (task == null) {
                throw new IllegalStateException();
            }
            list.remove(task);
            index--;
        }
    }
}