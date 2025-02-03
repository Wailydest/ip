import exception.SigmabotDataException;
import org.json.JSONException;
import org.json.JSONObject;

public final class Deadline extends Task {
    String by;
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }
    Deadline(JSONObject taskJsonObject) throws SigmabotDataException {
        super(taskJsonObject);
        try {
            this.by = taskJsonObject.getString("by");
        } catch (JSONException e) {
            throw new SigmabotDataException("could not locate parameter for this task type "
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
        result.put("by", by);
        result.put("type", "deadline");
        return result;
    }
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.by + ")";
    }
}
