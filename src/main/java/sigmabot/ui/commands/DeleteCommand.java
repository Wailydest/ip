package sigmabot.ui.commands;

import sigmabot.exception.IncorrectDeleteFormat;
import sigmabot.exception.IncorrectTaskNumber;
import sigmabot.exception.SigmabotException;
import sigmabot.exception.SigmabotInputException;
import sigmabot.tasks.TaskContainer;

/**
 * Command to delete a task from the task list.
 */
public final class DeleteCommand extends Command {
    private final int taskNumber;
    /**
     * Constructs a new DeleteCommand object.
     *
     * @param input the user input that represents the task to delete.
     * @throws SigmabotInputException if the user input doesn't follow the delete command standard.
     */
    public DeleteCommand(String input) throws SigmabotInputException {
        String[] inputParts = input.split("\\s+");
        if (inputParts.length != 2) throw new IncorrectDeleteFormat();
        try {
            this.taskNumber = Integer.parseInt(inputParts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new IncorrectDeleteFormat();
        }
    }
    @Override
    public void executeOn(TaskContainer tasks) throws SigmabotException {
        if (taskNumber < 0 || taskNumber >= tasks.taskCount()) {
            throw new IncorrectTaskNumber(taskNumber);
        }
        System.out.println("removed task " + (taskNumber + 1) + ": " + tasks.getTask(taskNumber));
        tasks.remove(taskNumber);
        System.out.println("you've got " + tasks.taskCount() + " tasks so far");
    }
    public int getTaskNumber() {
        return taskNumber;
    }
}
