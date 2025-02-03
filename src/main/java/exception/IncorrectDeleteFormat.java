package exception;

public class IncorrectDeleteFormat extends SigmabotInputException {
    public IncorrectDeleteFormat() {
        super("Incorrect delete format. Use: delete [task number]");
    }
}
