package sigmabot.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import sigmabot.exception.SigmabotCorruptedDataException;

public class TaskTest {
    @Test
    public void taskMarkAndUnmarkTest() {
        Task task = new Event("test event", LocalDateTime.parse("2021-08-24T10:15"), LocalDateTime.parse("2021-08-24T11:15"));
        assertEquals("[E][ ] test event (from: Aug 24 2021 10:15am to: Aug 24 2021 11:15am)", task.toString());
        task = task.mark();
        assertEquals("[E][X] test event (from: Aug 24 2021 10:15am to: Aug 24 2021 11:15am)", task.toString());
        task = task.unmark();
        assertEquals("[E][ ] test event (from: Aug 24 2021 10:15am to: Aug 24 2021 11:15am)", task.toString());
        task = new Event("test event", LocalDateTime.parse("2021-08-24T13:15"), LocalDateTime.parse("2021-08-24T14:15"));
        assertEquals("[E][ ] test event (from: Aug 24 2021 1:15pm to: Aug 24 2021 2:15pm)", task.toString());
    }

    @Test
    public void taskConvertToJsonAndBack() {
        for (Task task : Util.generateTasks()) {
            try {
                JSONObject taskJsonObject = task.toJson();
                assertEquals(task.toString(), Task.jsonToTask(taskJsonObject).toString());
            } catch (SigmabotCorruptedDataException e) {
                fail();
            }
        }
    }
}
