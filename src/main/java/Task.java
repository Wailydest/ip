public class Task {
    private String description;
    private boolean isMarked;
    public Task(String description) {
        this.description = description;
        this.isMarked = false;
    }
    public void mark() {
        this.isMarked = true;
    }
    public void unmark() {
        this.isMarked = false;
    }
    public boolean getIsMarked() {
        return isMarked;
    }
    @Override
    public String toString() {
        return description;
    }
}
