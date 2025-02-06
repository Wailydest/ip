package sigmabot.exception;

/**
 * Represents an exception thrown when the find command is not in the correct format.
 */
public class IncorrectFindFormat extends SigmabotInputException {
    /**
     * Constructs an IncorrectFindFormat object.
     */
    public IncorrectFindFormat(String input) {
        super("Incorrect find format: + " + input + ". Please use 'find <keyword>'");
    }
}
