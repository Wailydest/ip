package sigmabot.ui.commands;

import java.util.regex.Pattern;

import sigmabot.exception.IncorrectTaskFormat;
import sigmabot.exception.MissingParameterInputException;
import sigmabot.exception.SigmabotDataException;
import sigmabot.exception.SigmabotInputException;
import sigmabot.exception.UnknownCommandInputException;
import sigmabot.tasks.Deadline;
import sigmabot.tasks.Event;
import sigmabot.tasks.Task;
import sigmabot.tasks.TaskContainer;
import sigmabot.tasks.ToDo;
import sigmabot.ui.Ui;

public final class AddTaskCommand extends Command {
    private final Task task;

    public AddTaskCommand(String input) throws SigmabotInputException {
        String descriptionRegex = "^[a-z]+\\s([^/]+)";
        var matcher = Pattern.compile(descriptionRegex).matcher(input);
        if (!matcher.find()) throw new IncorrectTaskFormat(input);
        String description = matcher.group(1).trim();
        if (input.startsWith("deadline")) {
            var matcherBy = Pattern.compile("/by([^/]*)").matcher(input);
            if (!matcherBy.find()) {
                throw new MissingParameterInputException("by");
            }
            this.task = new Deadline(description,
                    Ui.parseDateTime(matcherBy.group(1).trim()));
        } else if (input.startsWith("event")) {
            var matcherFrom = Pattern.compile("/from([^/]*)").matcher(input);
            var matcherTo = Pattern.compile("/to([^/]*)").matcher(input);
            if (!matcherFrom.find()) {
                throw new MissingParameterInputException("from");
            }
            if (!matcherTo.find()) {
                throw new MissingParameterInputException("to");
            }
            this.task = new Event(description,
                    Ui.parseDateTime(matcherFrom.group(1).trim()),
                    Ui.parseDateTime(matcherTo.group(1).trim()));
        } else if (input.startsWith("todo")) {
            this.task = new ToDo(description);
        } else {
            throw new UnknownCommandInputException(input);
        }
    }

    @Override
    public void executeOn(TaskContainer tasks) throws SigmabotDataException {
        tasks.add(task);
    }

    public Task getTask() {
        return task;
    }
}
