package sigmabot.exception;

public class IncorrectMarkFormat extends SigmabotInputException {
    public IncorrectMarkFormat(String command) {
        super("Incorrect mark format. Use: " + command + " [task number]");
    }
}
