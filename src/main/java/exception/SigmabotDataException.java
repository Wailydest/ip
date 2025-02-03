package exception;

public class SigmabotDataException extends SigmabotException {
    public SigmabotDataException(String message) {
        super("Fatal data error: " + message);
    }
}
