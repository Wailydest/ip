package sigmabot.tasks;

import sigmabot.exception.SigmabotCorruptedDataException;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A class encapsulating a task concept.
 * Each task instance is supposed to be immutable.
 */
public abstract class Task {
    private final String description;
    private boolean isMarked;
    protected Task(String description) {
        this.description = description;
        this.isMarked = false;
    }
    protected Task(JSONObject taskJsonObject) throws SigmabotCorruptedDataException{
        try {
            this.description = taskJsonObject.getString("description");
            this.isMarked = taskJsonObject.getBoolean("isMarked");
        } catch (JSONException e) {
            throw new SigmabotCorruptedDataException("could not access parameter: "
                    + e.getMessage());
        }
    }
    protected Task(Task t) {
        this.description = t.description;
        this.isMarked = t.isMarked;
    }
    protected abstract Task copy();
    /**
     * Converts the task to a JSON object.
     *
     * @return the JSON object representing the task.
     */
    public JSONObject toJson() {
        var result = new JSONObject();
        result.put("description", description);
        result.put("isMarked", isMarked);
        return result;
    }
    /**
     * Converts a JSON object to a Task object.
     * The format is supposed to be the same as the one produced by the toJson method.
     *
     * @param taskJsonObject the JSON object to convert.
     * @return the Task object represented by the JSON object.
     * @throws SigmabotCorruptedDataException if the JSON object cannot be transformed into a task.
     */
    public static Task jsonToTask(JSONObject taskJsonObject) throws SigmabotCorruptedDataException {
        String type;
        try {
            type = taskJsonObject.getString("type");
        } catch (JSONException e) {
            throw new SigmabotCorruptedDataException("unable to identify the task type: " + e.getMessage());
        }
        if (type.equals("todo")) {
            return new ToDo(taskJsonObject);
        } else if (type.equals("event")) {
            return new Event(taskJsonObject);
        } else if (type.equals("deadline")) {
            return new Deadline(taskJsonObject);
        }
        throw new SigmabotCorruptedDataException("type " + type + " could not be processed");
    }
    /**
     * Marks the task as done.
     *
     * @return a copy of the task with the isMarked field set to true.
     */
    public Task mark() {
        Task copy = this.copy();
        copy.isMarked = true;
        return copy;
    }

    /**
     * Unmarks the task.
     *
     * @return a copy of the task with the isMarked field set to false.
     */
    public Task unmark() {
        Task copy = this.copy();
        copy.isMarked = false;
        return copy;
    }
    public boolean getIsMarked() {
        return isMarked;
    }
    protected static String dateTimeToString(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy h:mma"));
    }
    @Override
    public String toString() {
        return "[" + (this.getIsMarked() ? "X" : " ") + "] " + description;
    }
}
