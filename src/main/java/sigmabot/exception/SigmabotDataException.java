package sigmabot.exception;

/**
 * Exceptions of this type are thrown to inform about data-related errors.
 */
public class SigmabotDataException extends SigmabotException {
    /**
     * Constructs a new SigmabotDataException object.
     *
     * @param message the message specifying the problem.
     */
    public SigmabotDataException(String message) {
        super(message);
    }
}
