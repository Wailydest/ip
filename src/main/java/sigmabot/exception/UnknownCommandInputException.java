package sigmabot.exception;

public class UnknownCommandInputException extends SigmabotInputException {
    public UnknownCommandInputException(String input) {
        super("There's no such command "
                + input.split("\\s+")[0]
                + " :(");
    }
}
