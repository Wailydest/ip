package sigmabot.tasks;

import org.json.JSONObject;

import sigmabot.exception.IncorrectTaskFormat;
import sigmabot.exception.SigmabotCorruptedDataException;

/**
 * A class encapsulating a ToDo task. Doesn't have any additional fields.
 */
public final class ToDo extends Task {
    /**
     * Initializes a ToDo object with the given description.
     *
     * @param description the description of the ToDo task.
     */
    public ToDo(String description) throws IncorrectTaskFormat {
        super(description);
    }

    ToDo(JSONObject taskJsonObject) throws SigmabotCorruptedDataException {
        super(taskJsonObject);
    }

    ToDo(ToDo t) {
        super(t);
    }

    @Override
    protected Task copy() {
        return new ToDo(this);
    }

    @Override
    public JSONObject toJson() {
        var result = super.toJson();
        result.put("type", "todo");
        return result;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
