package sigmabot.ui;

import sigmabot.exception.SigmabotException;
import sigmabot.exception.SigmabotInputException;
import sigmabot.ui.commands.Command;
import sigmabot.ui.commands.ExitCommand;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

final public class Ui {
    private final Scanner scanner;
    Ui() {
        this.scanner = new Scanner(System.in);
        System.out.println("hi! i'm your sigma bot. what's on your list?");
    }

    public static LocalDateTime parseDateTime(String dateTime) throws SigmabotInputException {
        try {
            return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (DateTimeException e) {
            throw new SigmabotInputException("Incorrect date format: " + dateTime
                    + ". Format for date should be yyyy-MM-dd HH:mm");
        }
    }

    boolean iteration() {
        String input = this.scanner.nextLine();
        try {
            Command cmd = Command.parse(input);
            if (cmd instanceof ExitCommand) {
                System.out.println("bye! see you soon!");
                return false;
            }
            cmd.executeOn(Sigmabot.tasks);
        } catch (SigmabotInputException e) {
            System.out.println("[!] " + e.getMessage());
        } catch (SigmabotException e) {
            System.out.println("[!] Fatal exception: " + e.getMessage());
        }
        return true;
    }
}
