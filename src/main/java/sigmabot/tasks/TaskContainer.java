package sigmabot.tasks;

import sigmabot.exception.SigmabotDataException;

import java.util.ArrayList;

public class TaskContainer {
    private final ArrayList<Task> taskList;
    public TaskContainer() throws SigmabotDataException {
        this.taskList = Storage.load();
    }
    public void printTasks() {
        for (int i = 0; i < taskList.size(); ++i) System.out.println((i + 1) + ": " + this.taskList.get(i));
    }
    public void add(Task task) throws SigmabotDataException {
        taskList.add(task);
        Storage.storeData(this);
    }
    public void editTask(int i, Task task) throws SigmabotDataException {
        taskList.set(i, task);
        Storage.storeData(this);
    }
    public Task getTask(int i) {
        return taskList.get(i);
    }
    public void remove(int i) throws SigmabotDataException {
        taskList.remove(i);
        Storage.storeData(this);
    }
    public int taskCount() {
        return taskList.size();
    }
}
