package org.examberry.service;

import org.examberry.model.Booking;
import org.examberry.model.BookingStatus;
import org.examberry.model.Lesson;
import org.examberry.model.Subject;

import java.util.DoubleSummaryStatistics;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Generates consolidated reports:
 *  - Number of students per lesson & average rating
 *  - Subject with highest total income
 */
public class ReportService {

    /**
     * Prints both attendance/rating report and highest-income report.
     */
    public static void printAllReports() {
        printAttendanceReport();
        printIncomeReport();
    }

    /**
     * For each lesson: number of attended students & average rating.
     */
    private static void printAttendanceReport() {
        System.out.println("\n--- Attendance & Rating Report ---");
        List<Lesson> lessons = TimetableService.getAllLessons();
        if (lessons.isEmpty()) {
            System.out.println("No lessons scheduled.");
            return;
        }

        lessons.forEach(lesson -> {
            // Filter attended bookings
            List<Booking> attended = lesson.getBookings().stream()
                    .filter(b -> b.getStatus() == BookingStatus.ATTENDED)
                    .collect(Collectors.toList());

            // Compute average rating for those with reviews
            IntSummaryStatistics stats = attended.stream()
                    .map(Booking::getReview)
                    .filter(r -> r != null)
                    .mapToInt(r -> r.getRating())
                    .summaryStatistics();

            String avgRating = stats.getCount() > 0
                    ? String.format("%.2f", stats.getAverage())
                    : "N/A";

            System.out.printf("%s | Attended: %d | Avg Rating: %s%n",
                    lesson.getId(),
                    attended.size(),
                    avgRating);
        });
    }

    /**
     * Computes total income per subject and prints the highest earner.
     */
    private static void printIncomeReport() {
        System.out.println("\n--- Income Report ---");
        List<Lesson> lessons = TimetableService.getAllLessons();
        if (lessons.isEmpty()) {
            System.out.println("No lessons scheduled.");
            return;
        }

        // Sum income per subject
        Map<Subject, Double> incomeBySubject = lessons.stream()
                .collect(Collectors.groupingBy(
                        Lesson::getSubject,
                        Collectors.summingDouble(Lesson::getIncome)
                ));

        // Display each subject's total
        incomeBySubject.forEach((subject, income) ->
                System.out.printf("%-20s : £%.2f%n", subject, income)
        );

        // Find the highest
        Subject topSubject = incomeBySubject.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        if (topSubject != null) {
            System.out.println("\nTop‐earning subject: " + topSubject
                    + " with £" + String.format("%.2f", incomeBySubject.get(topSubject)));
        }
    }
}