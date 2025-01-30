package exception;

public class IncorrectMarkFormat extends SigmabotException {
    public IncorrectMarkFormat(String command) {
        super("Incorrect mark format. Use: " + command + " [task number]");
    }
}
