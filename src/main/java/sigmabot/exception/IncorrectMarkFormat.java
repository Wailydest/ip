package sigmabot.exception;

/**
 * An exception thrown when the user inputs an incorrect mark format.
 */
public class IncorrectMarkFormat extends SigmabotInputException {
    /**
     * Constructs a new IncorrectMarkFormat object.
     * The message is a standard message to inform the user of the correct mark format.
     *
     * @param command the command that the user inputted.
     */
    public IncorrectMarkFormat(String command) {
        super("Incorrect mark format. Use: " + command + " [task number]");
    }
}
