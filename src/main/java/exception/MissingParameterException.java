package exception;

public class MissingParameterException extends SigmabotException{
    public MissingParameterException(String parameter) {
        super("This task type requires parameter /" + parameter);
    }
}
