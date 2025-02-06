package sigmabot.ui.commands;

import sigmabot.tasks.TaskContainer;

/**
 * Command to list all tasks in the task list.
 */
public class ListCommand extends Command {
    @Override
    public void executeOn(TaskContainer tasks) {
        System.out.println("here's your task list:");
        tasks.printTasks();
    }
}
