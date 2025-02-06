package sigmabot.ui;

import sigmabot.exception.SigmabotException;
import sigmabot.tasks.TaskContainer;

/**
 * Main class for the SigmaBot application.
 */
public class Sigmabot {
    /**
     * The name of the directory where the data file is stored.
     */
    private final static String DATA_DIR_NAME = "data";
    /**
     * The name of the data file inside the data directory.
     */
    private final static String DATA_FILE_NAME = "data.json";
    static TaskContainer tasks;
    static Ui ui;

    /**
     * Main method for the SigmaBot application.
     *
     * @param args command line arguments.
     * @throws SigmabotException if a fatal error occurs while running the application.
     */
    public static void main(String[] args) throws SigmabotException {
        Sigmabot.tasks = new TaskContainer(DATA_DIR_NAME, DATA_FILE_NAME);
        Sigmabot.ui = new Ui();
        while (ui.iteration()) ;
    }
}
