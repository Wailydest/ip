package sigmabot.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import sigmabot.exception.IncorrectTaskFormat;
import sigmabot.exception.SigmabotCorruptedDataException;

/**
 * A class encapsulating a task concept.
 * Each task instance is supposed to be immutable.
 */

public abstract class Task {
    private final String description;
    private boolean isMarked;
    private String tag;

    protected Task(String command) throws IncorrectTaskFormat {
        this.description = Task.extractDescription(command);
        this.tag = Task.extractArgument(command, "tag").orElse("");
    }

    protected Task(JSONObject taskJsonObject) throws SigmabotCorruptedDataException {
        try {
            this.description = taskJsonObject.getString("description");
            this.isMarked = taskJsonObject.getBoolean("isMarked");
            this.tag = taskJsonObject.getString("tag");
        } catch (JSONException e) {
            throw new SigmabotCorruptedDataException("could not access parameter: "
                    + e.getMessage());
        }
    }

    protected Task(Task t) {
        this.description = t.description;
        this.isMarked = t.isMarked;
        this.tag = t.tag;
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

    protected static Optional<String> extractArgument(String command, String argument) {
        var matcher = Pattern.compile("/" + argument + "([^/]*)").matcher(command);
        if (!matcher.find()) {
            return Optional.empty();
        }
        return Optional.of(matcher.group(1).trim());
    }

    private static String extractDescription(String command) throws IncorrectTaskFormat {
        String descriptionRegex = "^[a-z]+\\s([^/]+)";
        var matcher = Pattern.compile(descriptionRegex).matcher(command);
        if (!matcher.find()) throw new IncorrectTaskFormat(command);
        return matcher.group(1).trim();
    }

    public static Task commandToTask(String command) throws IncorrectTaskFormat {
        String[] commandParts = command.split(" ", 2);
        String type = commandParts[0];
        if (type.equals("todo")) {
            return new ToDo(command);
        } else if (type.equals("event")) {
            return new Event(command);
        } else {
            assert type.equals("deadline") : "Invalid task type: the check for validity of the task type must've been performed earlier";
            return new Deadline(command);
        }
    }

    protected static String dateTimeToString(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy h:mma"));
    }

    protected abstract Task copy();

    /**
     * Converts the task to a JSON object.
     *
     * @return the JSON object representing the task.
     */
    public JSONObject toJson() {
        var result = new JSONObject();
        result.put("description", this.description);
        result.put("isMarked", this.isMarked);
        result.put("tag", this.tag);
        return result;
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
     * Sets the tag of the task.
     *
     * @param tag The tag to set.
     * @return a copy of the task with the tag field set to the given tag.
     */
    public Task setTag(String tag) {
        Task copy = this.copy();
        copy.tag = tag;
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

    public String toString() {
        return "[" + (this.getIsMarked() ? "X" : " ") + "] "
                + description + (tag.isEmpty() ? "" : " #" + tag);
    }
}
