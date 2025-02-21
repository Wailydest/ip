package sigmabot.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import sigmabot.exception.IncorrectTaskFormat;
import sigmabot.exception.SigmabotCorruptedDataException;

public class TaskTest {
    @Test
    public void taskMarkAndUnmarkTest() {
        Task task = null;
        try {
            task = Task.commandToTask("event test /from 2021-08-24 10:15 /to 2021-08-24 11:15");
        } catch (IncorrectTaskFormat e) {
            fail(e.getMessage());
        }
        assertEquals("[E][ ] test (from: Aug 24 2021 10:15am to: Aug 24 2021 11:15am)", task.toString());
        task = task.mark();
        assertEquals("[E][X] test (from: Aug 24 2021 10:15am to: Aug 24 2021 11:15am)", task.toString());
        task = task.unmark();
        assertEquals("[E][ ] test (from: Aug 24 2021 10:15am to: Aug 24 2021 11:15am)", task.toString());
        try {
            task = Task.commandToTask("event test /from 2021-08-24 01:15 /to 2021-08-24 14:15");
        } catch (IncorrectTaskFormat e) {
            fail(e.getMessage());
        }
        assertEquals("[E][ ] test (from: Aug 24 2021 1:15am to: Aug 24 2021 2:15pm)", task.toString());
    }

    @Test
    public void taskConvertToJsonAndBack() {
        for (Task task : Util.generateTasks()) {
            try {
                JSONObject taskJsonObject = task.toJson();
                assertEquals(task.toString(), Task.jsonToTask(taskJsonObject).toString());
            } catch (SigmabotCorruptedDataException e) {
                fail(e.getMessage());
            }
        }
    }
}
