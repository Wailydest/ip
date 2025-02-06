package sigmabot.exception;

/**
 * An exception thrown when the user inputs an unknown command.
 */
public class UnknownCommandInputException extends SigmabotInputException {
    /**
     * Constructs a new UnknownCommandInputException object.
     *
     * @param input the input that the user inputted.
     */
    public UnknownCommandInputException(String input) {
        super("There's no such command "
                + input.split("\\s+")[0]
                + " :(");
    }
}
