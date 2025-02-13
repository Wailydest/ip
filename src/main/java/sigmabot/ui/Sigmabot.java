package sigmabot.ui;

import java.util.Scanner;

import sigmabot.exception.SigmabotException;
import sigmabot.exception.SigmabotInputException;
import sigmabot.tasks.TaskContainer;
import sigmabot.ui.commands.Command;
import sigmabot.ui.commands.ExitCommand;

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
    private TaskContainer tasks;

    public Sigmabot() {
        try {
            this.tasks = new TaskContainer(DATA_DIR_NAME, DATA_FILE_NAME);
        } catch (SigmabotException e) {
            System.err.println("[!] Fatal exception: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Pass input to Sigmabot and get reponse.
     */
    public String getResponse(String input) {
        try {
            Command cmd = Command.parse(input);
            if (cmd instanceof ExitCommand) {
                System.exit(0);
            }
            return cmd.executeOn(this.tasks);
        } catch (SigmabotInputException e) {
            return "[!] " + e.getMessage();
        } catch (SigmabotException e) {
            return "[!] Fatal exception: " + e.getMessage();
        }
    }

    /**
     * Accept one line of user console input and execute the corresponding command,
     * printing to output back to the command line
     */
    public void cmdInteraction() {
        System.out.println(getGreeting());
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();
            System.out.println(getResponse(input));
        }
    }
    public String getGreeting() {
        return "hi! i'm your sigma bot. what's on your list?";
    }
}
