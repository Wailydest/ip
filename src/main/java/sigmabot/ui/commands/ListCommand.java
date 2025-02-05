package sigmabot.ui.commands;

import sigmabot.tasks.TaskContainer;

public class ListCommand extends Command {
    @Override
    public void executeOn(TaskContainer tasks) {
        System.out.println("here's your task list:");
        tasks.printTasks();
    }
}
