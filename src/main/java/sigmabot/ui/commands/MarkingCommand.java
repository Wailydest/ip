package sigmabot.ui.commands;

import sigmabot.exception.IncorrectMarkFormat;
import sigmabot.exception.IncorrectTaskNumber;
import sigmabot.exception.SigmabotException;
import sigmabot.exception.SigmabotInputException;
import sigmabot.tasks.TaskContainer;

public final class MarkingCommand extends Command {
    final private int taskNumber;
    final private boolean toMark;

    MarkingCommand(String input) throws SigmabotInputException {
        String[] inputParts = input.split("\\s+");
        if (inputParts.length != 2) throw new IncorrectMarkFormat(inputParts[0]);
        try {
            this.taskNumber = Integer.parseInt(inputParts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new IncorrectMarkFormat(inputParts[0]);
        }
        this.toMark = inputParts[0].equals("mark");
    }

    @Override
    public void executeOn(TaskContainer tasks) throws SigmabotException {
        if (this.taskNumber < 0 || this.taskNumber >= tasks.taskCount()) {
            throw new IncorrectTaskNumber(this.taskNumber);
        }
        if (this.toMark) {
            tasks.editTask(this.taskNumber, tasks.getTask(this.taskNumber).mark());
        } else {
            tasks.editTask(this.taskNumber, tasks.getTask(this.taskNumber).unmark());
        }
        System.out.println((this.toMark ? "marked" : "unmarked") + " task "
                + (this.taskNumber + 1) + ": " + tasks.getTask(this.taskNumber));
    }

    public int getTaskNumber() {
        return this.taskNumber;
    }

    public boolean getToMark() {
        return this.toMark;
    }
}
