package exception;

public class IncorrectTaskNumber extends SigmabotException {
    public IncorrectTaskNumber(int number) {
        super("Task numbered " + number + " does not exist.");
    }
}
