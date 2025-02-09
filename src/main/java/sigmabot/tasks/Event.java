package sigmabot.tasks;

import java.time.DateTimeException;
import java.time.LocalDateTime;

import org.json.JSONException;
import org.json.JSONObject;

import sigmabot.exception.SigmabotCorruptedDataException;

/**
 * A class encapsulating an Event task. Stores the start and end time of the event.
 */
public final class Event extends Task {
    private final LocalDateTime from, to;

    /**
     * Initializes an Event object with the given description, start time and end time.
     *
     * @param description the description of the Event task.
     * @param from        the start time of the event.
     * @param to          the end time of the event.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
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
