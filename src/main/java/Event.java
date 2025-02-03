import exception.SigmabotDataException;
import org.json.JSONException;
import org.json.JSONObject;

public final class Event extends Task {
    String from, to;
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }
    Event(JSONObject taskJsonObject) throws SigmabotDataException {
        super(taskJsonObject);
        try {
            this.from = taskJsonObject.getString("from");
            this.to = taskJsonObject.getString("to");
        } catch (JSONException e) {
            throw new SigmabotDataException("could not locate parameter for this task type "
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
        var result =  super.toJson();
        result.put("from", from);
        result.put("to", to);
        result.put("type", "event");
        return result;
    }
    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + this.from + " to: " + this.to + ")";
    }
}
