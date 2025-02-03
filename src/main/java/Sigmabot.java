import java.util.Scanner;
import java.util.regex.Pattern;

import exception.*;

public class Sigmabot {
    static private TaskContainer tasks;

    public static void viewTaskList() {
        System.out.println("here goes your task list:");
        tasks.printTasks();
    }
    public static void processMarkInput(String input) throws SigmabotException {
        String[] inputParts = input.split("\\s+");
        if (inputParts.length != 2) throw new IncorrectMarkFormat(inputParts[0]);
        int taskNumber;
        try {
            taskNumber = Integer.parseInt(inputParts[1]);
        } catch (NumberFormatException e) {
            throw new IncorrectMarkFormat(inputParts[0]);
        }
        if (taskNumber < 1 || taskNumber > Sigmabot.tasks.taskCount()) {
            throw new IncorrectTaskNumber(taskNumber);
        }
        --taskNumber;
        if (inputParts[0].equals("mark")) {
            tasks.editTask(taskNumber, tasks.getTask(taskNumber).mark());
        } else {
            tasks.editTask(taskNumber, tasks.getTask(taskNumber).unmark());
        }
    }
    public static void processDeleteInput(String input) throws SigmabotException {
        String[] inputParts = input.split("\\s+");
        if (inputParts.length != 2) throw new IncorrectDeleteFormat();
        int taskNumber;
        try {
            taskNumber = Integer.parseInt(inputParts[1]);
        } catch (NumberFormatException e) {
            throw new IncorrectDeleteFormat();
        }
        if (taskNumber < 1 || taskNumber > Sigmabot.tasks.taskCount()) {
            throw new IncorrectTaskNumber(taskNumber);
        }
        --taskNumber;
        System.out.println("removed task " + (taskNumber + 1) + ": " + tasks.getTask(taskNumber));
        tasks.remove(taskNumber);
        System.out.println("you've got " + tasks.taskCount() + " tasks so far");
    }
    public static void processAddTaskInput(String input) throws SigmabotException {
        String descriptionRegex = "^[a-z]+\\s([^/]+)";
        var matcher = Pattern.compile(descriptionRegex).matcher(input);
        if (!matcher.find()) throw new IncorrectTaskFormat(input);
        String description = matcher.group(1).trim();
        if (input.startsWith("deadline")) {
            var matcherBy = Pattern.compile("/by([^/]*)").matcher(input);
            if (!matcherBy.find()) {
                throw new MissingParameterInputException("by");
            }
            tasks.add(new Deadline(description, matcherBy.group(1).trim()));
        } else if (input.startsWith("event")) {
            var matcherFrom = Pattern.compile("/from([^/]*)").matcher(input);
            var matcherTo = Pattern.compile("/to([^/]*)").matcher(input);
            if (!matcherFrom.find()) {
                throw new MissingParameterInputException("from");
            }
            if (!matcherTo.find()) {
                throw new MissingParameterInputException("to");
            }
            tasks.add(new Event(description, matcherFrom.group(1).trim(), matcherTo.group(1).trim()));
        } else if (input.startsWith("todo")) {
            tasks.add(new ToDo(description));
        } else {
            throw new IncorrectTaskTypeInputException(input);
        }
        System.out.println("added new task " + tasks.taskCount() + ": " + tasks.getTask(tasks.taskCount() - 1));
        System.out.println("you've got " + tasks.taskCount() + " tasks so far");
    }
    public static void main(String[] args) throws SigmabotException {
        Sigmabot.tasks = new TaskContainer();

        System.out.println("hi! i'm your sigma bot. what's on your list?");

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            try {
                if (input.equals("bye")) break;
                if (input.equals("list")) Sigmabot.viewTaskList();
                else if (input.startsWith("mark ") || input.startsWith("unmark ")) Sigmabot.processMarkInput(input);
                else if (input.startsWith("delete ")) Sigmabot.processDeleteInput(input);
                else Sigmabot.processAddTaskInput(input);
            } catch (SigmabotInputException e) {
                System.out.println("[!] " + e.getMessage());
            }
        }

        System.out.println("see you soon!");
    }
}
