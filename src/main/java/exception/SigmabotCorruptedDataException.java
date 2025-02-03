package exception;

public class SigmabotCorruptedDataException extends SigmabotDataException {
    public SigmabotCorruptedDataException(String message) {
        super("Corrupt data detected: " + message);
    }
}
