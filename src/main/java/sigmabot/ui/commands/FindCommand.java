package sigmabot.ui.commands;

import sigmabot.exception.IncorrectFindFormat;
import sigmabot.tasks.TaskContainer;

/**
 * Represents a command to find tasks in the task list.
 */
public class FindCommand extends Command {
    private final String keyword;

    /**
     * Constructs a FindCommand object.
     *
     * @param input The input string. Assumes the input first word is "find".
     */
    public FindCommand(String input) throws IncorrectFindFormat {
        String[] parts = input.split("\\s+", 2);
        if (parts.length < 2) {
            throw new IncorrectFindFormat(input);
        }
        this.keyword = parts[1];
    }

    /**
     * Executes the command to find tasks in the task list.
     *
     * @param tasks The task container to find tasks in.
     */
    @Override
    public void executeOn(TaskContainer tasks) {
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < tasks.taskCount(); i++) {
            if (tasks.getTask(i).toString().contains(keyword)) {
                System.out.println((i + 1) + ": " + tasks.getTask(i).toString());
            }
        }
    }
}
