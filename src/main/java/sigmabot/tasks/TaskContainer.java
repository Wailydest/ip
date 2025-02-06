package sigmabot.tasks;

import java.util.ArrayList;
import java.util.List;

import sigmabot.exception.SigmabotDataException;

public class TaskContainer {
    private final ArrayList<Task> taskList;
    private final Storage storage;

    public TaskContainer(String dataDirName, String dataFileName) throws SigmabotDataException {
        this.storage = new Storage(dataDirName, dataFileName);
        this.taskList = storage.load();
    }

    public void printTasks() {
        for (int i = 0; i < taskList.size(); ++i) System.out.println((i + 1) + ": " + this.taskList.get(i));
    }

    public void add(Task task) throws SigmabotDataException {
        taskList.add(task);
        storage.storeData(List.copyOf(taskList));
    }

    public void editTask(int i, Task task) throws SigmabotDataException {
        taskList.set(i, task);
        this.storage.storeData(List.copyOf(taskList));
    }

    public Task getTask(int i) {
        return taskList.get(i);
    }

    public void remove(int i) throws SigmabotDataException {
        taskList.remove(i);
        this.storage.storeData(List.copyOf(taskList));
    }

    public int taskCount() {
        return taskList.size();
    }
}
