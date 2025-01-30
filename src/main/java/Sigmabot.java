import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
public class Sigmabot {
    static ArrayList<Task> taskList;

    public static void viewTaskList() {
        for (int i = 0; i < taskList.size(); ++i) {
            System.out.println("["
                    + (taskList.get(i).getIsMarked() ? "X" : " " )
                    + "] Task " + (i + 1) + ": "
                    + taskList.get(i).toString());
        }
    }
    public static boolean processMarkInput(String input) {
        String[] inputParts = input.split("\\s+");
        if (inputParts.length == 2
                && (inputParts[0].equals("mark") || inputParts[0].equals("unmark"))) {
            try {
                int taskNumber = Integer.parseInt(inputParts[1]);
                if (taskNumber < 1 || taskNumber > Sigmabot.taskList.size()) {
                    throw new IllegalArgumentException("Task " + taskNumber + " does not exist");
                }
                --taskNumber;
                if (inputParts[0].equals("mark")) taskList.get(taskNumber).mark();
                else taskList.get(taskNumber).unmark();
                return true;
            } catch (NumberFormatException e) {
                System.out.println("invalid " + inputParts[0] + " request: " + e);
                throw e;
            }
        }
        return false;
    }
    public static void main(String[] args) {
        Sigmabot.taskList = new ArrayList<Task>();

        System.out.println("hi! i'm your sigma bot. what's on your list?");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equals("bye")) break;
            if (input.equals("list")) Sigmabot.viewTaskList();
            else if (!Sigmabot.processMarkInput(input)) taskList.add(new Task(input));
        }

        System.out.println("see you soon!");
    }
}
