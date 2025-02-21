package sigmabot.tasks;

import java.time.DateTimeException;
import java.time.LocalDateTime;

import org.json.JSONException;
import org.json.JSONObject;

import sigmabot.Util;
import sigmabot.exception.IncorrectTaskFormat;
import sigmabot.exception.SigmabotCorruptedDataException;

/**
 * A class encapsulating a Deadline task. Stores the deadline of the task.
 */
public final class Deadline extends Task {
    private final LocalDateTime dueDateTime;

    public Deadline(String command) throws IncorrectTaskFormat {
        super(command);
        try {
            this.dueDateTime = Task.extractArgument(command, "by")
                    .map(Util::parseDateTime)
                    .orElseThrow(() -> new IncorrectTaskFormat("Missing 'by' parameter"));
        } catch (DateTimeException e) {
            throw new IncorrectTaskFormat("Incorrect date format: " + e.getMessage()
                    + ". Format for date should be yyyy-MM-dd HH:mm");
        }
    }

    Deadline(JSONObject taskJsonObject) throws SigmabotCorruptedDataException {
        super(taskJsonObject);
        try {
            this.dueDateTime = LocalDateTime.parse(taskJsonObject.getString("by"));
        } catch (JSONException e) {
            throw new SigmabotCorruptedDataException("could not access parameter for this task type "
                    + e.getMessage());
        } catch (DateTimeException e) {
            throw new SigmabotCorruptedDataException("could not parse date time: "
                    + e.getMessage());
        }

    }

    private Deadline(Deadline t) {
        super(t);
        this.dueDateTime = t.dueDateTime;
    }

    @Override
    protected Task copy() {
        return new Deadline(this);
    }

    @Override
    public JSONObject toJson() {
        var result = super.toJson();
        result.put("by", dueDateTime.toString());
        result.put("type", "deadline");
        return result;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString()
                + " (by: " + Task.dateTimeToString(this.dueDateTime) + ")";
    }
}
