import exception.SigmabotCorruptedDataException;
import exception.SigmabotDataException;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Task {
    private final String description;
    private boolean isMarked;
    public Task(String description) {
        this.description = description;
        this.isMarked = false;
    }
    public Task(String description, boolean isMarked) {
        this.description = description;
        this.isMarked = isMarked;
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
    protected JSONObject toJson() {
        var result = new JSONObject();
        result.put("description", description);
        result.put("isMarked", isMarked);
        return result;
    }
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
    public Task mark() {
        Task copy = this.copy();
        copy.isMarked = true;
        return copy;
    }
    public Task unmark() {
        Task copy = this.copy();
        copy.isMarked = false;
        return copy;
    }
    public boolean getIsMarked() {
        return isMarked;
    }
    public static String dateTimeToString(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy ha"));
    }
    @Override
    public String toString() {
        return "[" + (this.isMarked ? "X" : " ") + "] " + description;
    }
}
