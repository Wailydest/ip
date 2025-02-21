package sigmabot.ui.commands;

import sigmabot.exception.SigmabotException;
import sigmabot.exception.SigmabotInputException;
import sigmabot.exception.UnknownCommandInputException;
import sigmabot.tasks.TaskContainer;

/**
 * Abstract class for the commands that can be executed by the SigmaBot application.
 */
public abstract class Command {
    /**
     * A factory method that parses a user input string into a Command object.
     *
     * @param input the user input string to parse.
     * @return an appropriate command subclass object that corresponds to the user input.
     * @throws SigmabotInputException if the user input is in an incorrect/unknown format.
     */
    public static Command parse(String input) throws SigmabotInputException {
        if (input.equals("bye")) return new ExitCommand();
        if (input.equals("list")) return new ListCommand();
        if (input.startsWith("mark") || input.startsWith("unmark")) return new MarkingCommand(input);
        if (input.startsWith("delete")) return new DeleteCommand(input);
        if (input.startsWith("todo") || input.startsWith("deadline") || input.startsWith("event")) {
            return new AddTaskCommand(input);
        }
        if (input.startsWith("find")) return new FindCommand(input);
        if (input.startsWith("tag") || input.startsWith("untag")) return new TaggingCommand(input);
        throw new UnknownCommandInputException(input);
    }

    /**
     * An abstract method that executes the command on the given TaskContainer object.
     *
     * @param tasks the TaskContainer object to execute the command on.
     *              For some commands the contents of the TaskContainer object may be modified.
     * @return a string that represents the output to be displayed.
     * @throws SigmabotException if an error occurs while executing the command.
     */
    public abstract String executeOn(TaskContainer tasks) throws SigmabotException;
}
