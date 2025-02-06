package sigmabot.exception;

/**
 * An exception thrown when the user inputs a task type that requires a parameter but does not provide it.
 */
public class MissingParameterInputException extends SigmabotInputException {
    /**
     * Constructs a new MissingParameterInputException object.
     * The message is a standard message to inform the user that the task type requires a parameter.
     *
     * @param parameter the parameter that the user needs to provide.
     */
    public MissingParameterInputException(String parameter) {
        super("This task type requires parameter /" + parameter);
    }
}
