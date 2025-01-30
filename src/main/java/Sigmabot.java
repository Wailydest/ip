import java.util.ArrayList;
import java.util.Scanner;
public class Sigmabot {
    static ArrayList<Task> taskList;

    public static void viewTaskList() {
        for (int i = 0; i < taskList.size(); ++i) {
            System.out.println("Task " + (i + 1) + ": " + taskList.get(i).toString());
        }
    }
    public static void main(String[] args) {
        Sigmabot.taskList = new ArrayList<Task>();

        System.out.println("hi! i'm your sigma bot. what's on your list?");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) break;
            if (input.equals("list")) Sigmabot.viewTaskList();
            else taskList.add(new Task(input));
        }

        System.out.println("see you soon!");
    }
}
