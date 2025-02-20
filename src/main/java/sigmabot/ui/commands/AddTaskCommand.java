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
import sigmabot.ui.Util;

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
        String descriptionRegex = "^[a-z]+\\s([^/]+)";
        var matcher = Pattern.compile(descriptionRegex).matcher(input);
        if (!matcher.find()) throw new IncorrectTaskFormat(input);
        String description = matcher.group(1).trim();
        if (input.startsWith("deadline")) {
            var deadlineMatcherBy = Pattern.compile("/by([^/]*)").matcher(input);
            if (!deadlineMatcherBy.find()) {
                throw new MissingParameterInputException("by");
            }
            this.task = new Deadline(description,
                    Util.parseDateTime(deadlineMatcherBy.group(1).trim()));
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
                    Util.parseDateTime(matcherFrom.group(1).trim()),
                    Util.parseDateTime(matcherTo.group(1).trim()));
        } else if (input.startsWith("todo")) {
            this.task = new ToDo(description);
        } else {
            this.task = null;
            assert false : "The check for validity of the task type must've been performed earlier";
        }
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
