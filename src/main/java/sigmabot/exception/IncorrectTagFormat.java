package sigmabot.exception;

public class IncorrectTagFormat extends SigmabotInputException {
    public IncorrectTagFormat() {
        super("Incorrect tagging format. Usage: \n> tag [task number] [new tag]\n"
                + "to add/change the tag or\n"
                + "> untag [task number]\n"
                + "to remove tag.");
    }
}
