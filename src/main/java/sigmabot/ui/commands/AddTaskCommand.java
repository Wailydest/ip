package sigmabot.ui.commands;

import sigmabot.exception.SigmabotDataException;
import sigmabot.exception.SigmabotInputException;
import sigmabot.tasks.Task;
import sigmabot.tasks.TaskContainer;

/**
 * Command to add a task to the task list.
 */
public final class AddTaskCommand extends Command {
    private final Task task;

    /**
     * Constructs a new AddTaskCommand object.
     *
     * @param input the user input that represents the task to add.
     *              Assumes the first word of the input command is a valid task type.
     * @throws SigmabotInputException if the user input is in an incorrect format.
     */
    public AddTaskCommand(String input) throws SigmabotInputException {
        this.task = Task.commandToTask(input);
    }

    @Override
    public String executeOn(TaskContainer tasks) throws SigmabotDataException {
        tasks.add(task);
        return "Got it. I've added this task:\n" + task
                + "\nNow you have " + tasks.taskCount() + " tasks in the list.";
    }

    public Task getTask() {
        return task;
    }
}
