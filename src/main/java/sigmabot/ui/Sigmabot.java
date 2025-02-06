package sigmabot.ui;

import sigmabot.exception.SigmabotException;
import sigmabot.tasks.TaskContainer;

public class Sigmabot {
    private final static String DATA_DIR_NAME = "data";
    private final static String DATA_FILE_NAME = "data.json";
    static TaskContainer tasks;
    static Ui ui;

    public static void main(String[] args) throws SigmabotException {
        Sigmabot.tasks = new TaskContainer(DATA_DIR_NAME, DATA_FILE_NAME);
        Sigmabot.ui = new Ui();
        while (ui.iteration()) ;
    }
}
