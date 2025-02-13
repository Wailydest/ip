package sigmabot.ui.commands;

import sigmabot.tasks.TaskContainer;

/**
 * Command to list all tasks in the task list.
 */
public class ListCommand extends Command {
    @Override
    public String executeOn(TaskContainer tasks) {
        return "here's your task list:\n" + tasks.toString();
    }
}
