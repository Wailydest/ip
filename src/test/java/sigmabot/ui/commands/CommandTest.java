package sigmabot.ui.commands;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import sigmabot.exception.SigmabotInputException;
import sigmabot.exception.UnknownCommandInputException;
import sigmabot.tasks.ToDo;

public class CommandTest {
    @Test
    public void parseShouldReturnExitCommandForByeInput() throws SigmabotInputException {
        Command command = Command.parse("bye");
        assertInstanceOf(ExitCommand.class, command);
    }

    @Test
    public void parseShouldReturnListCommandForListInput() throws SigmabotInputException {
        Command command = Command.parse("list");
        assertInstanceOf(ListCommand.class, command);
    }

    @Test
    public void parseShouldReturnMarkingCommandForMarkInput() throws SigmabotInputException {
        Command command = Command.parse("mark 1");
        assertInstanceOf(MarkingCommand.class, command);
        assertTrue(((MarkingCommand) command).getToMark());
        assertEquals(0, ((MarkingCommand) command).getTaskNumber());
    }

    @Test
    public void parseShouldReturnMarkingCommandForUnmarkInput() throws SigmabotInputException {
        Command command = Command.parse("unmark 2");
        assertInstanceOf(MarkingCommand.class, command);
        assertFalse(((MarkingCommand) command).getToMark());
        assertEquals(1, ((MarkingCommand) command).getTaskNumber());
    }

    @Test
    public void parseShouldReturnDeleteCommandForDeleteInput() throws SigmabotInputException {
        Command command = Command.parse("delete 1");
        assertInstanceOf(DeleteCommand.class, command);
        assertEquals(0, ((DeleteCommand) command).getTaskNumber());
    }

    @Test
    public void parseShouldReturnAddTaskCommandForOtherInput() throws SigmabotInputException {
        Command command = Command.parse("todo read book");
        assertInstanceOf(AddTaskCommand.class, command);
        assertEquals((new ToDo("read book")).toString(),
                ((AddTaskCommand) command).getTask().toString());
    }

    @Test
    public void parseShouldThrowExceptionForEmptyInput() {
        assertThrows(UnknownCommandInputException.class, () -> Command.parse(""));
    }
}
