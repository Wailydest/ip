import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;

import exception.*;

public class Sigmabot {
    static ArrayList<Task> taskList;

    public static void viewTaskList() {
        System.out.println("here goes your task list:");
        for (int i = 0; i < taskList.size(); ++i) {
            System.out.println(i + 1 + ": " + taskList.get(i).toString());
        }
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
        if (taskNumber < 1 || taskNumber > Sigmabot.taskList.size()) {
            throw new IncorrectTaskNumber(taskNumber);
        }
        --taskNumber;
        if (inputParts[0].equals("mark")) taskList.get(taskNumber).mark();
        else taskList.get(taskNumber).unmark();
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
        if (taskNumber < 1 || taskNumber > Sigmabot.taskList.size()) {
            throw new IncorrectTaskNumber(taskNumber);
        }
        --taskNumber;
        System.out.println("removed task " + (taskNumber + 1) + ": " + taskList.get(taskNumber));
        taskList.remove(taskNumber);
        System.out.println("you've got " + taskList.size() + " tasks so far");
    }
    public static void processAddTaskInput(String input) throws SigmabotException {
        String descriptionRegex = "^[a-z]+\\s([^/]+)";
        var matcher = Pattern.compile(descriptionRegex).matcher(input);
        if (!matcher.find()) throw new IncorrectTaskFormat(input);
        String description = matcher.group(1).trim();
        if (input.startsWith("deadline")) {
            var matcherBy = Pattern.compile("/by([^/]*)").matcher(input);
            if (!matcherBy.find()) {
                throw new MissingParameterException("by");
            }
            taskList.add(new Deadline(description, matcherBy.group(1).trim()));
        } else if (input.startsWith("event")) {
            var matcherFrom = Pattern.compile("/from([^/]*)").matcher(input);
            var matcherTo = Pattern.compile("/to([^/]*)").matcher(input);
            if (!matcherFrom.find()) {
                throw new MissingParameterException("from");
            }
            if (!matcherTo.find()) {
                throw new MissingParameterException("to");
            }
            taskList.add(new Event(description, matcherFrom.group(1).trim(), matcherTo.group(1).trim()));
        } else if (input.startsWith("todo")) {
            taskList.add(new ToDo(description));
        } else {
            throw new IncorrectTaskTypeException(input);
        }
        System.out.println("added new task " + taskList.size() + ": " + taskList.get(taskList.size() - 1));
        System.out.println("you've got " + taskList.size() + " tasks so far");
    }
    public static void main(String[] args) {
        Sigmabot.taskList = new ArrayList<Task>();

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
            } catch (SigmabotException e) {
                System.out.println("[!] " + e.getMessage());
            }
        }

        System.out.println("see you soon!");
    }
}
