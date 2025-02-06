package sigmabot.tasks;

import static org.junit.jupiter.api.Assertions.fail;

import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Util {
    public static final String DATA_DIR_NAME = "testDataDir";
    public static final String DATA_FILE_NAME = "testDataFile.json";

    public static void clearTestFile() {
        try {
            Files.delete(java.nio.file.Paths.get(DATA_DIR_NAME, DATA_FILE_NAME));
        } catch (java.io.IOException e) {
            fail("Failed to delete test file: " + e.getMessage());
        }
    }

    public static ArrayList<Task> generateTasks() {
        ArrayList<Task> tasks = new ArrayList<Task>();
        tasks.add(new Event("event 1",
                LocalDateTime.parse("2021-08-24T10:15"),
                LocalDateTime.parse("2021-08-24T11:15")));
        tasks.add(new Deadline("deadline 1",
                LocalDateTime.parse("2021-08-24T12:00")));
        tasks.add(new ToDo("todo 1"));
        tasks.add(new Event("event 2",
                LocalDateTime.parse("2021-08-25T10:15"),
                LocalDateTime.parse("2021-08-25T11:15")));
        tasks.add((new Deadline("deadline marked",
                LocalDateTime.parse("2021-08-25T12:00"))).mark());
        tasks.add(new ToDo("todo marked").mark());
        tasks.add((new Event("event marked",
                LocalDateTime.parse("2021-08-26T10:15"),
                LocalDateTime.parse("2021-08-26T11:15"))).mark());
        return tasks;
    }
}
