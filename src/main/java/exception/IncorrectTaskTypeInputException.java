package exception;

public class IncorrectTaskTypeInputException extends SigmabotInputException {
    public IncorrectTaskTypeInputException(String input) {
        super("There's no task type named "
                + input.split("\\s+")[0]
                + " :( try one of the valid task types");
    }
}
