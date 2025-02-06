package sigmabot.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import sigmabot.exception.SigmabotDataException;

public class StorageTest {
    @Test
    public void storageStoreAndLoadTasksTest() {
        Util.clearTestFile();
        ArrayList<Task> tasks = Util.generateTasks();
        Storage storage = new Storage(Util.DATA_DIR_NAME, Util.DATA_FILE_NAME);
        try {
            storage.storeData(tasks);
        } catch (SigmabotDataException e) {
            fail();
        }
        try {
            ArrayList<Task> loadedTasks = storage.load();
            for (int i = 0; i < tasks.size(); ++i) {
                assertEquals(tasks.get(i).toString(), loadedTasks.get(i).toString());
            }
        } catch (SigmabotDataException e) {
            fail();
        }
    }
}
