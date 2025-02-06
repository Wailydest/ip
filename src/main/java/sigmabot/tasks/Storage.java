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
import java.util.List;

/**
 * A class that handles the storage of tasks.
 */
public class Storage {
    private final String dataDirName;
    private final String dataFileName;
    private Path getDataPath() throws SigmabotDataException {
        Path currentDir = Paths.get("").toAbsolutePath();
        Path dataDir = currentDir.resolve(this.dataDirName);
        try {
            Files.createDirectories(dataDir);
        } catch (IOException e) {
            throw new SigmabotDataException("Failed to create data directory: " + e.getMessage());
        }
        return dataDir.resolve(this.dataFileName);
    }
    /**
     * Initializes the Storage object with the name of the data directory and the name of the data file.
     *
     * @param dataDirName the name of the directory where the data file is stored.
     * @param dataFileName the name of the data file inside the data directory.
     */
    public Storage(String dataDirName, String dataFileName) {
        this.dataDirName = dataDirName;
        this.dataFileName = dataFileName;
    }
    /**
     * Loads the tasks from the data file.
     * If the data is corrupted or does not follow the expected format, the corresponding task will be skipped.
     *
     * @return an ArrayList list of the tasks loaded from the data file.
     * @throws SigmabotDataException if the data file cannot be read.
     */
    public ArrayList<Task> load() throws SigmabotDataException {
        Path dataFile = this.getDataPath();
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
    /**
     * Stores the tasks into the data file.
     *
     * @param tasks the list of tasks to store.
     * @throws SigmabotDataException if the data file cannot be written.
     */
    public void storeData(List<Task> tasks) throws SigmabotDataException {
        JSONArray data = new JSONArray();
        for (Task task : tasks) {
            data.put(task.toJson());
        }
        Path dataFile = this.getDataPath();
        try {
            Files.writeString(dataFile,
                    data.toString(4),
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.SYNC);
        } catch (IOException e) {
            throw new SigmabotDataException("unable to store data: " + e);
        }
    }
}
