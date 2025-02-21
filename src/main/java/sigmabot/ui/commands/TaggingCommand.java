package sigmabot.ui.commands;

import sigmabot.exception.IncorrectTagFormat;
import sigmabot.exception.IncorrectTaskNumber;
import sigmabot.exception.SigmabotException;
import sigmabot.tasks.TaskContainer;

public class TaggingCommand extends Command {
    int taskNumber;
    String tag;

    public TaggingCommand(String input) throws IncorrectTagFormat {
        String[] inputParts = input.split("\\s+");
        if (inputParts.length < 1) {
            throw new IncorrectTagFormat();
        }
        try {
            this.taskNumber = Integer.parseInt(inputParts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new IncorrectTagFormat();
        }
        if (inputParts.length > 2) {
            if (inputParts[0].equals("untag")) throw new IncorrectTagFormat();
            this.tag = inputParts[2];
        } else if (inputParts[0].equals("tag")) {
            throw new IncorrectTagFormat();
        } else {
            this.tag = "";
        }
    }

    @Override
    public String executeOn(TaskContainer tasks) throws SigmabotException {
        if (this.taskNumber < 0 || this.taskNumber >= tasks.taskCount()) {
            throw new IncorrectTaskNumber(this.taskNumber);
        }
        tasks.editTask(this.taskNumber, tasks.getTask(this.taskNumber).setTag(this.tag));
        return "Updated the tag for task "
                + (this.taskNumber + 1) + ": " + tasks.getTask(this.taskNumber);
    }
}
