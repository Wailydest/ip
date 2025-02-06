package sigmabot.tasks;

import org.json.JSONException;
import org.json.JSONObject;
import sigmabot.exception.SigmabotCorruptedDataException;

import java.time.DateTimeException;
import java.time.LocalDateTime;

/**
 * A class encapsulating a Deadline task. Stores the deadline of the task.
 */
public final class Deadline extends Task {
    LocalDateTime by;

    /**
     * Initializes a Deadline object with the given description and deadline.
     *
     * @param description the description of the Deadline task.
     * @param by the deadline of the task.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }
    Deadline(JSONObject taskJsonObject) throws SigmabotCorruptedDataException {
        super(taskJsonObject);
        try {
            this.by = LocalDateTime.parse(taskJsonObject.getString("by"));
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
        this.by = t.by;
    }
    @Override
    protected Task copy() {
        return new Deadline(this);
    }
    @Override
    public JSONObject toJson() {
        var result =  super.toJson();
        result.put("by", by.toString());
        result.put("type", "deadline");
        return result;
    }
    @Override
    public String toString() {
        return "[D]" + super.toString()
                + " (by: " + Task.dateTimeToString(this.by) + ")";
    }
}
