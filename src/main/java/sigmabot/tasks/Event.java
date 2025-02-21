package sigmabot.tasks;

import java.time.DateTimeException;
import java.time.LocalDateTime;

import org.json.JSONException;
import org.json.JSONObject;

import sigmabot.Util;
import sigmabot.exception.IncorrectTaskFormat;
import sigmabot.exception.SigmabotCorruptedDataException;

/**
 * A class encapsulating an Event task. Stores the start and end time of the event.
 */
public final class Event extends Task {
    private LocalDateTime from, to;

    public Event(String command) throws IncorrectTaskFormat {
        super(command);
        try {
            this.from = Task.extractArgument(command, "from")
                    .map(Util::parseDateTime)
                    .orElseThrow(() -> new IncorrectTaskFormat("Missing 'from' parameter"));
            this.to = Task.extractArgument(command, "to")
                    .map(Util::parseDateTime)
                    .orElseThrow(() -> new IncorrectTaskFormat("Missing 'to' parameter"));
        } catch (DateTimeException e) {
            throw new IncorrectTaskFormat("Incorrect date format: " + e.getMessage()
                    + ". Format for date should be yyyy-MM-dd HH:mm");
        }
    }

    Event(JSONObject taskJsonObject) throws SigmabotCorruptedDataException {
        super(taskJsonObject);
        try {
            this.from = LocalDateTime.parse(taskJsonObject.getString("from"));
            this.to = LocalDateTime.parse(taskJsonObject.getString("to"));
        } catch (JSONException e) {
            throw new SigmabotCorruptedDataException("could not access parameter for this task type "
                    + e.getMessage());
        } catch (DateTimeException e) {
            throw new SigmabotCorruptedDataException("could not parse date time: "
                    + e.getMessage());
        }
    }

    private Event(Event t) {
        super(t);
        this.from = t.from;
        this.to = t.to;
    }

    @Override
    protected Task copy() {
        return new Event(this);
    }

    @Override
    public JSONObject toJson() {
        var result = super.toJson();
        result.put("from", from.toString());
        result.put("to", to.toString());
        result.put("type", "event");
        return result;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + Task.dateTimeToString(this.from)
                + " to: " + Task.dateTimeToString(this.to) + ")";
    }
}
