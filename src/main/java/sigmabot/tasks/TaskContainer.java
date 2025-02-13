package sigmabot.tasks;

import java.util.ArrayList;
import java.util.List;

import sigmabot.exception.SigmabotDataException;

/**
 * A class that maintains a list of tasks and synchronizes it with a storage.
 * Every change done to the task will be reflected in the storage.
 * Thus expects the storage directory to be writable.
 */
public class TaskContainer {
    private final ArrayList<Task> taskList;
    private final Storage storage;

    /**
     * Initializes the TaskContainer object with the name of the data directory and the name of the data file.
     *
     * @param dataDirName  the name of the directory where the data file is stored.
     * @param dataFileName the name of the data file inside the data directory.
     * @throws SigmabotDataException if the data file cannot be read.
     */
    public TaskContainer(String dataDirName, String dataFileName) throws SigmabotDataException {
        this.storage = new Storage(dataDirName, dataFileName);
        this.taskList = storage.load();
    }

    /**
     * Adds a task to the task list.
     *
     * @param task the task to add.
     * @throws SigmabotDataException if the data is failed to be stored.
     */
    public void add(Task task) throws SigmabotDataException {
        taskList.add(task);
        storage.storeData(List.copyOf(taskList));
    }

    /**
     * Edits a task in the task list.
     *
     * @param i    index (0-indexed) of the task to edit
     * @param task the new task to replace the old task
     * @throws SigmabotDataException if the data is failed to be stored.
     */
    public void editTask(int i, Task task) throws SigmabotDataException {
        taskList.set(i, task);
        this.storage.storeData(List.copyOf(taskList));
    }

    /**
     * Retrieves a task from the task list.
     *
     * @param i index (0-indexed) of the task to retrieve.
     * @return the task at the specified index.
     */
    public Task getTask(int i) {
        return taskList.get(i);
    }

    /**
     * Removes a task from the task list.
     *
     * @param i index (0-indexed) of the task to remove.
     * @throws SigmabotDataException if the data is failed to be stored.
     */
    public void remove(int i) throws SigmabotDataException {
        taskList.remove(i);
        this.storage.storeData(List.copyOf(taskList));
    }

    /**
     * Returns the task count.
     *
     * @return the number of tasks in the task list.
     */
    public int taskCount() {
        return taskList.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < taskList.size(); ++i) {
            sb.append((i + 1)).append(": ").append(taskList.get(i)).append("\n");
        }
        return sb.toString();
    }
}
