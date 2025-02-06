package sigmabot.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import sigmabot.exception.SigmabotDataException;

public class TaskContainerTest {
    @Test
    public void taskContainerStoreAndModifyTasksTest() {
        Util.clearTestFile();
        try {
            TaskContainer taskContainer = new TaskContainer(Util.DATA_DIR_NAME, Util.DATA_FILE_NAME);
            ArrayList<Task> tasks = Util.generateTasks();
            for (int i = 0; i < tasks.size() - 1; ++i) taskContainer.add(tasks.get(i));
            assertEquals(taskContainer.taskCount(), tasks.size() - 1);
            for (int i = 0; i < tasks.size() - 1; ++i) {
                Task task = taskContainer.getTask(i);
                assertEquals(task.toString(), tasks.get(i).toString());
            }
            taskContainer.editTask(2, tasks.get(tasks.size() - 1));
            assertEquals(taskContainer.getTask(2).toString(), tasks.get(tasks.size() - 1).toString());
            taskContainer.remove(2);
            for (int i = 0; i < 2; ++i) {
                Task task = taskContainer.getTask(i);
                assertEquals(task.toString(), tasks.get(i).toString());
            }
            for (int i = 3; i < tasks.size() - 1; ++i) {
                Task task = taskContainer.getTask(i - 1);
                assertEquals(task.toString(), tasks.get(i).toString());
            }
        } catch (SigmabotDataException e) {
            fail();
        }
    }

    @Test
    public void taskContainerLoadTasksFromFileTest() {
        Util.clearTestFile();
        ArrayList<Task> tasks = Util.generateTasks();
        Storage storage = new Storage(Util.DATA_DIR_NAME, Util.DATA_FILE_NAME);
        try {
            storage.storeData(tasks);
        } catch (SigmabotDataException e) {
            fail();
        }
        try {
            TaskContainer taskContainer = new TaskContainer(Util.DATA_DIR_NAME, Util.DATA_FILE_NAME);
            assertEquals(taskContainer.taskCount(), tasks.size());
            for (int i = 0; i < tasks.size(); ++i) {
                Task task = taskContainer.getTask(i);
                assertEquals(task.toString(), tasks.get(i).toString());
            }
        } catch (SigmabotDataException e) {
            fail();
        }
    }
}
