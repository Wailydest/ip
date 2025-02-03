import exception.SigmabotDataException;
import exception.SigmabotException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.nio.file.Path;

public class TaskContainer {
    private ArrayList<Task> taskList;
    private final static String DATA_DIR_NAME = "data";
    private final static String DATA_FILE_NAME = "data.json";
    private static Path getDataPath() throws SigmabotDataException {
        Path currentDir = Paths.get("").toAbsolutePath();
        Path dataDir = currentDir.resolve(DATA_DIR_NAME);
        try {
            Files.createDirectories(dataDir);
        } catch (IOException e) {
            throw new SigmabotDataException("Failed to create data directory: " + e.getMessage());
        }
        return dataDir.resolve(DATA_FILE_NAME);
    }
    public TaskContainer() throws SigmabotDataException {
        Path dataFile = TaskContainer.getDataPath();
        JSONArray data;
        if (Files.exists(dataFile)) {
            try {
                String content = Files.readString(dataFile, StandardCharsets.UTF_8);
                data = new JSONArray(content);
            } catch (IOException e) {
                throw new SigmabotDataException("Failed to read data file: " + e.getMessage());
            }
        } else {
            data = new JSONArray();
        }
        taskList = new ArrayList<>();
        for (int i = 0; i < data.length(); ++i) {
            taskList.add(Task.jsonToTask(data.getJSONObject(i)));
        }
    }
    private void storeData() throws SigmabotDataException {
        JSONArray data = new JSONArray();
        for (var task : taskList) {
            data.put(task.toJson());
        }
        Path dataFile = TaskContainer.getDataPath();
        try {
            Files.writeString(dataFile,
                    data.toString(4),
                    StandardCharsets.UTF_8,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.SYNC);
        } catch (IOException e) {
            throw new SigmabotDataException("unable to store data: " + e.getMessage());
        }
    }
    public void printTasks() {
        for (int i = 0; i < taskList.size(); ++i) System.out.println((i + 1) + ": " + this.taskList.get(i));
    }
    public void add(Task task) throws SigmabotDataException {
        taskList.add(task);
        this.storeData();
    }
    public void editTask(int i, Task task) throws SigmabotDataException {
        taskList.set(i, task);
        this.storeData();
    }
    public Task getTask(int i) {
        return taskList.get(i);
    }
    public void remove(int i) throws SigmabotDataException {
        taskList.remove(i);
        this.storeData();
    }
    public int taskCount() {
        return taskList.size();
    }
}
