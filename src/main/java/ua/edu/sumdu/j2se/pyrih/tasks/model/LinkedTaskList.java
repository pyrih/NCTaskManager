package ua.edu.sumdu.j2se.pyrih.tasks.model;

import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Stream;

public class LinkedTaskList extends AbstractTaskList {
    private static final Logger logger = Logger.getLogger(LinkedTaskList.class);
    private int size;
    private Node head;

    public LinkedTaskList() {
        head = null;
        size = 0;
    }

    @Override
    public void add(Task task) throws IllegalArgumentException {
        if (task == null) {
            throw new IllegalArgumentException("Illegal incoming argument. Task must not be null.");
        } else if (head == null) {
            head = new Node(task);
        } else {
            Node node = head;
            while (node.next != null) {
                node = node.next;
            }
            node.next = new Node(task);
        }
        size++;
    }

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

    @Override
    public int size() {
        return size;
    }

    @Override
    public Task getTask(int index) throws IndexOutOfBoundsException {
        if (head == null || index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("An array has been accessed with an illegal index");
        }
        Node node = getNode(index);
        return node.task;
    }

    @Override
    public Stream<Task> getStream() {
        return Stream.of(this.toArray());
    }

    public Task[] toArray() {
        Task[] result = new Task[size];
        int i = 0;
        for (Node x = head; x != null; x = x.next)
            result[i++] = x.task;
        return result;
    }

    private Task remove(int index) {
        Task task = getTask(index);
        if (index == 0) {
            head = head.next;
        } else {
            Node node = getNode(index - 1);
            node.next = node.next.next;
        }
        size--;
        return task;
    }

    private int indexOf(Task target) {
        Node node = head;
        for (int i = 0; i < size; i++) {
            if (equalTasks(target, node.task)) {
                return i;
            }
            node = node.next;
        }
        return -1;
    }

    private Node getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    private boolean equalTasks(Task target, Task task) {
        if (target == null) {
            return task == null;
        } else {
            return target.equals(task);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkedTaskList that = (LinkedTaskList) o;
        Node temp = that.head;
        Node temp2 = ((LinkedTaskList) o).head;

        boolean isEquals = false;
        int count = 0;
        while (that.head != null && ((LinkedTaskList) o).head != null) {

            Task t1 = that.getTask(count);
            Task t2 = ((LinkedTaskList) o).getTask(count);

            temp = temp.next;
            temp2 = temp2.next;

            if (t1.equals(t2)) {
                isEquals = true;
                return true;
            }
            count++;
        }
        return size == that.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, head);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", LinkedTaskList.class.getSimpleName() + "[", "]")
                .add("size=" + size)
                .add("head=" + head)
                .toString();
    }

    @Override
    public LinkedTaskList clone() throws CloneNotSupportedException {
        LinkedTaskList clone = superClone();

        clone.head = null;
        clone.size = 0;

        // Initialize a clone with our elements
        for (Node x = head; x != null; x = x.next) {
            clone.add(x.task);
        }
        return clone;
    }

    private LinkedTaskList superClone() {
        try {
            LinkedTaskList clone = (LinkedTaskList) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    @Override
    public Iterator<Task> iterator() {
        return new LinkedListIterator();
    }

    static class Node {
        public Task task;
        public Node next;

        public Node(Task task) {
            this.task = task;
            this.next = null;
        }

        public Node(Task task, Node next) {
            this.task = task;
            this.next = next;
        }

        public String toString() {
            return "Node(" + task.toString() + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return task.equals(node.task) &&
                    next == node.next;
        }

        @Override
        public int hashCode() {
            return Objects.hash(task, next);
        }
    }

    private class LinkedListIterator implements Iterator<Task> {
        private Node position;
        private Node previous;
        private boolean isAfterNext;

        public LinkedListIterator() {
            position = null;
            previous = null;
            isAfterNext = false;
        }

        @Override
        public boolean hasNext() {
            if (position == null) {
                return head != null;
            } else {
                return position.next != null;
            }
        }

        @Override
        public Task next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            previous = position;
            isAfterNext = true;

            if (position == null) {
                position = head;
            } else {
                position = position.next;
            }
            return position.task;
        }

        @Override
        public void remove() {
            if (!isAfterNext) {
                throw new IllegalStateException();
            }
            if (position == head) {
                // remove head
                LinkedTaskList.this.remove(position.task);
            } else {
                previous.next = position.next;
            }
            position = previous;
            isAfterNext = false;
        }
    }
}