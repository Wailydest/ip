import java.util.Scanner;

public class Sigmabot {
    public static void main(String[] args) {
        System.out.println("hi! i'm your sigma bot");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) break;
            System.out.println(input);
        }
        System.out.println("see you soon!");
    }
}
