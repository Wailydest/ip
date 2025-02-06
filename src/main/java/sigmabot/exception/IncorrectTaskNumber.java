package sigmabot.exception;

/**
 * An exception thrown when the user inputs an incorrect task number.
 */
public class IncorrectTaskNumber extends SigmabotInputException {
    /**
     * Constructs a new IncorrectTaskNumber object.
     * The message is a standard message to inform the user that the task number does not exist.
     *
     * @param number the task number that the user inputted.
     */
    public IncorrectTaskNumber(int number) {
        super("Task numbered " + number + " does not exist.");
    }
}
