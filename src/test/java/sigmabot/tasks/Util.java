package sigmabot.tasks;

import static org.junit.jupiter.api.Assertions.fail;

import java.nio.file.Files;
import java.util.ArrayList;

import sigmabot.exception.IncorrectTaskFormat;

public class Util {
    public static final String DATA_DIR_NAME = "testDataDir";
    public static final String DATA_FILE_NAME = "testDataFile.json";

    public static void clearTestFile() {
        try {
            var path = java.nio.file.Paths.get(DATA_DIR_NAME, DATA_FILE_NAME);
            if (Files.exists(path)) {
                Files.delete(path);
            }
        } catch (java.io.IOException e) {
            fail("Failed to access the test data file: " + e.getMessage());
        }
    }

    public static ArrayList<Task> generateTasks() {
        ArrayList<Task> tasks = new ArrayList<Task>();
        try {
            tasks.add(Task.commandToTask("deadline dd1 /by 2021-08-24 10:15"));
            tasks.add(Task.commandToTask("event ev1 /from 2021-08-24 10:15 /to 2021-08-24 11:15"));
            tasks.add(Task.commandToTask("todo td1"));
            tasks.add(Task.commandToTask("deadline dd2 /by 2021-08-24 01:15"));
            tasks.add(Task.commandToTask("event ev2 /from 2021-08-24 01:15 /to 2021-08-24 14:15"));
            tasks.add(Task.commandToTask("todo td2"));
        } catch (IncorrectTaskFormat e) {
            fail("Failed to generate tasks: " + e.getMessage());
        }
        return tasks;
    }
}
