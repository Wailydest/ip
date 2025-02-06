package sigmabot.exception;

/**
 * An exception thrown when the user inputs an incorrect delete format.
 */
public class IncorrectDeleteFormat extends SigmabotInputException {
    /**
     * Constructs a new IncorrectDeleteFormat object.
     * The message is a standard message to inform the user of the correct delete format.
     */
    public IncorrectDeleteFormat() {
        super("Incorrect delete format. Use: delete [task number]");
    }
}
