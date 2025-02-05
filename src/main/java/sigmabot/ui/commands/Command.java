package sigmabot.ui.commands;

import sigmabot.exception.SigmabotException;
import sigmabot.exception.SigmabotInputException;
import sigmabot.tasks.TaskContainer;

public abstract class Command {
    // command factory
    public static Command parse(String input) throws SigmabotInputException {
        if (input.equals("bye")) return new ExitCommand();
        if (input.equals("list")) return new ListCommand();
        if (input.startsWith("mark") || input.startsWith("unmark")) return new MarkingCommand(input);
        if (input.startsWith("delete")) return new DeleteCommand(input);
        return new AddTaskCommand(input);
    }
    public void executeOn(TaskContainer tasks) throws SigmabotException {

    }
}
