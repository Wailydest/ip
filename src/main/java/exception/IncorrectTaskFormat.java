package exception;

public class IncorrectTaskFormat extends SigmabotException {
    public IncorrectTaskFormat(String input) {
        super("Incorrect task creation format: " + input
                + "\nThe proper format is: [task type] [task description] [parameters, if any]");
    }
}
