package sigmabot.exception;

/**
 * A superclass for exceptions related to input.
 */
public class SigmabotInputException extends SigmabotException {
    /**
     * Constructs a new SigmabotInputException object. Passes the message forward.
     *
     * @param message the message specifying the problem.
     */
    public SigmabotInputException(String message) {
        super(message);
    }
}
