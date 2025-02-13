package sigmabot.ui.commands;

import sigmabot.tasks.TaskContainer;

/**
 * Command to exit the SigmaBot application.
 */
public class ExitCommand extends Command {
    @Override
    public String executeOn(TaskContainer tasks) {
        System.exit(0);
        return "";
    }
}
