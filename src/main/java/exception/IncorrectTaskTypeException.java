package exception;

public class IncorrectTaskTypeException extends SigmabotException {
    public IncorrectTaskTypeException(String input) {
        super("There's no task type named "
                + input.split("\\s+")[0]
                + " :( try one of the valid task types");
    }
}
