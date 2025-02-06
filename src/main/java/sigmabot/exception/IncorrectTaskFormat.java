package sigmabot.exception;

/**
 * An exception thrown when the user inputs an incorrect task format.
 */
public class IncorrectTaskFormat extends SigmabotInputException {
    /**
     * Constructs a new IncorrectTaskFormat object.
     * The message is a standard message to inform the user of the correct task creation format.
     *
     * @param input the input that the user inputted.
     */
    public IncorrectTaskFormat(String input) {
        super("Incorrect task creation format: " + input
                + "\nThe proper format is: [task type] [task description] [parameters, if any]");
    }
}
