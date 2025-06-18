package org.examberry.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility for generating weekend dates.
 */
public class DateUtil {

    /**
     * Returns a list of dates for the next {@code weeks} Saturdays and Sundays.
     * If today is before Saturday, starts this coming Saturday; otherwise the next one.
     *
     * @param weeks number of weekends to generate
     * @return List of LocalDate (Saturday then Sunday) × weeks
     */
    public static List<LocalDate> nextWeekends(int weeks) {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate today = LocalDate.now();

        // Find the next Saturday
        int daysUntilSaturday = DayOfWeek.SATURDAY.getValue()
                - today.getDayOfWeek().getValue();
        if (daysUntilSaturday < 0) {
            daysUntilSaturday += 7;
        }
        LocalDate saturday = today.plusDays(daysUntilSaturday);

        // Build weekends
        for (int i = 0; i < weeks; i++) {
            dates.add(saturday.plusWeeks(i));           // Saturday
            dates.add(saturday.plusWeeks(i).plusDays(1)); // Sunday
        }
        return dates;
    }
}