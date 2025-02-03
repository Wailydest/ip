import org.json.JSONObject;

public final class ToDo extends Task {
    public ToDo(String description) {
        super(description);
    }
    ToDo(JSONObject taskJsonObject) {
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
        var result =  super.toJson();
        result.put("type", "todo");
        return result;
    }
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
