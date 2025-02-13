package sigmabot.ui;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import sigmabot.exception.SigmabotInputException;

/**
 * Class for the UI interactions between the user and the SigmaBot application.
 */
final public class Util {
    /**
     * Parses a date time string into a LocalDateTime object.
     *
     * @param dateTime the date time string to parse.
     * @return the LocalDateTime object representing the date time string.
     * @throws SigmabotInputException if the date time string is in an incorrect format.
     */
    public static LocalDateTime parseDateTime(String dateTime) throws SigmabotInputException {
        try {
            return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (DateTimeException e) {
            throw new SigmabotInputException("Incorrect date format: " + dateTime
                    + ". Format for date should be yyyy-MM-dd HH:mm");
        }
    }
}
