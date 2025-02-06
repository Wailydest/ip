package sigmabot.ui.commands;

import sigmabot.exception.IncorrectMarkFormat;
import sigmabot.exception.IncorrectTaskNumber;
import sigmabot.exception.SigmabotException;
import sigmabot.exception.SigmabotInputException;
import sigmabot.tasks.TaskContainer;

/**
 * Command to mark or unmark a task in the task list.
 */
public final class MarkingCommand extends Command {
    final private int taskNumber;
    final private boolean isMarkingTask;

    /**
     * Constructs a new MarkingCommand object.
     *
     * @param input the user input that represents the task to mark or unmark.
     *              Assumes the first word of the input is either mark or unmark
     * @throws IncorrectMarkFormat if the user input doesn't follow the mark/unmark command standard.
     */
    public MarkingCommand(String input) throws SigmabotInputException {
        String[] inputParts = input.split("\\s+");
        if (inputParts.length != 2) throw new IncorrectMarkFormat(inputParts[0]);
        try {
            this.taskNumber = Integer.parseInt(inputParts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new IncorrectMarkFormat(inputParts[0]);
        }
        this.isMarkingTask = inputParts[0].equals("mark");
    }

    @Override
    public void executeOn(TaskContainer tasks) throws SigmabotException {
        if (this.taskNumber < 0 || this.taskNumber >= tasks.taskCount()) {
            throw new IncorrectTaskNumber(this.taskNumber);
        }
        if (this.isMarkingTask) {
            tasks.editTask(this.taskNumber, tasks.getTask(this.taskNumber).mark());
        } else {
            tasks.editTask(this.taskNumber, tasks.getTask(this.taskNumber).unmark());
        }
        System.out.println((this.isMarkingTask ? "marked" : "unmarked") + " task "
                + (this.taskNumber + 1) + ": " + tasks.getTask(this.taskNumber));
    }

    public int getTaskNumber() {
        return this.taskNumber;
    }

    public boolean getIsMarkingTask() {
        return this.isMarkingTask;
    }
}
