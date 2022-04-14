package android.C868.Capstone.All.Utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateValidator {

    //Validate correct format for dates.
    public boolean isDateValid(String date) {
        return date.matches("\\d{2}/\\d{2}/\\d{2}");
    }

    // Validate start date is not after end date.
    public boolean isDateSequenceValid(String date1, String date2) {

        boolean result = false;

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("MM/dd/yy", Locale.ENGLISH);
        LocalDate startDate = LocalDate.parse(date1, formatter1);

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM/dd/yy", Locale.ENGLISH);
        LocalDate endDate = LocalDate.parse(date2, formatter2);

        if (startDate.isBefore(endDate)) {
            result = true;
        }
        return !result;
    }
}
