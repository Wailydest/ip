package sigmabot.tasks;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import sigmabot.exception.SigmabotCorruptedDataException;
import sigmabot.exception.SigmabotDataException;

public class Storage {
    private final String dataDirName;
    private final String dataFileName;

    Storage(String dataDirName, String dataFileName) {
        this.dataDirName = dataDirName;
        this.dataFileName = dataFileName;
    }

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
