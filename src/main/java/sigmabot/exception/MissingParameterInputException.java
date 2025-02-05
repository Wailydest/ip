package sigmabot.exception;

public class MissingParameterInputException extends SigmabotInputException {
    public MissingParameterInputException(String parameter) {
        super("This task type requires parameter /" + parameter);
    }
}
