package exception;

public class IncorrectDeleteFormat extends SigmabotException {
    public IncorrectDeleteFormat() {
        super("Incorrect delete format. Use: delete [task number]");
    }
}
