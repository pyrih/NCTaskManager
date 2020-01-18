package com.netcracker.eductr.tasks.tests;

import com.netcracker.eductr.tasks.tests.model.Task;
import org.junit.*;
import org.junit.runners.MethodSorters;

import static com.netcracker.eductr.tasks.tests.model.TaskCreator.create;
import static com.netcracker.eductr.tasks.tests.utils.ClassFinder.checkClassExistence;
import static com.netcracker.eductr.tasks.tests.utils.ClassFinder.checkMethodExistence;
import static com.netcracker.eductr.tasks.tests.utils.EqualsUtil.areEqual;
import static com.netcracker.eductr.tasks.tests.utils.Types.classTypes.TASK_BASE;
import static com.netcracker.eductr.tasks.tests.utils.Types.methodTypes.CLONE;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class T5_TaskCloneTest {
    @BeforeClass
    public static void init() {
        Assume.assumeTrue(checkClassExistence(TASK_BASE));
        Assume.assumeTrue(checkMethodExistence(CLONE, TASK_BASE));
    }

    @Test
    public void part1_testClone() {
        Task original = create("A");
        Task copy = original.clone();

        Object origRef = original.getInstance();
        Object cloneRef = copy.getInstance();

        Assert.assertTrue("{ x.clone() != x } не виконується", origRef != cloneRef);

        Assert.assertEquals("{ x.clone().getClass() == x.getClass() } не виконується", origRef.getClass(), cloneRef.getClass());

        System.out.println("original.getTime().equals(copy.getTime()): " + original.getTime().equals(copy.getTime()));
        System.out.println("original.getTime() == copy.getTime(): " + (original.getTime() == copy.getTime()));
        System.out.println("areEqual(original, copy): " + areEqual(original, copy));

        //System.out.println("original: " + (original.getClass().getField("time").getType()));
        //System.out.println("original: " + (copy.getClass().getField("time").getType()));
        //System.out.println("original: " + (original.getClass().getTypeName().toString()));
        //System.out.println("copy: " + (copy.getClass().getTypeName().toString()));


        Assert.assertTrue("{ x.clone().equals(x) } не виконується", areEqual(original, copy));
    }

    @Test
    public void part2_testCloneIndependence() {
        Task original = create("A");
        Task copy = original.clone();

        copy.setTitle("Copy");
        Assert.assertNotEquals("Після зміни title копії оригінал також змінився", original.getTitle(), copy.getTitle());
    }
}
