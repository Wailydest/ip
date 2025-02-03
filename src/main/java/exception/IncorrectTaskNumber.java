package exception;

public class IncorrectTaskNumber extends SigmabotInputException {
    public IncorrectTaskNumber(int number) {
        super("Task numbered " + number + " does not exist.");
    }
}
