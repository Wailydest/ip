package sigmabot.exception;

/**
 * An exception thrown when the data in the file is corrupted.
 */
public class SigmabotCorruptedDataException extends SigmabotDataException {
    /**
     * Constructs a new SigmabotCorruptedDataException object.
     * The message is a standard message to inform the user that the data is corrupted.
     *
     * @param message the message specifying the problem.
     */
    public SigmabotCorruptedDataException(String message) {
        super("Corrupt data detected: " + message);
    }
}
