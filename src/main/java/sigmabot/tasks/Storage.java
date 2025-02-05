package sigmabot.tasks;

import org.json.JSONArray;
import sigmabot.exception.SigmabotCorruptedDataException;
import sigmabot.exception.SigmabotDataException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class Storage {
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
    static ArrayList<Task> load() throws SigmabotDataException {
        Path dataFile = Storage.getDataPath();
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
        ArrayList<Task> taskList = new ArrayList<>();
        for (int i = 0; i < data.length(); ++i) {
            try {
                taskList.add(Task.jsonToTask(data.getJSONObject(i)));
            } catch (SigmabotCorruptedDataException e) {
                System.err.println("Failed to load task: " + e.getMessage());
            }
        }
        return taskList;
    }
    static void storeData(TaskContainer tasks) throws SigmabotDataException {
        JSONArray data = new JSONArray();
        for (int i = 0; i < tasks.taskCount(); ++i) {
            data.put(tasks.getTask(i).toJson());
        }
        Path dataFile = Storage.getDataPath();
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
}
