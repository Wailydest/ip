package sigmabot.exception;

/**
 * A superclass for exceptions related to the Sigmabot application.
 */
public class SigmabotException extends Exception {
    SigmabotException(String message) {
        super(message);
    }
}
