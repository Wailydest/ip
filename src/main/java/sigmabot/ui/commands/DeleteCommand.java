package sigmabot.ui.commands;

import sigmabot.exception.IncorrectDeleteFormat;
import sigmabot.exception.IncorrectTaskNumber;
import sigmabot.exception.SigmabotException;
import sigmabot.exception.SigmabotInputException;
import sigmabot.tasks.TaskContainer;

public final class DeleteCommand extends Command {
    private final int taskNumber;
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
}
