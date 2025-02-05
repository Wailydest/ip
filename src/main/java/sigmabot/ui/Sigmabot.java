package sigmabot.ui;

import sigmabot.tasks.TaskContainer;
import sigmabot.exception.*;

public class Sigmabot {
    static TaskContainer tasks;
    static Ui ui;

    public static void main(String[] args) throws SigmabotException {
        Sigmabot.tasks = new TaskContainer();
        Sigmabot.ui = new Ui();
        while (ui.iteration());
    }
}
