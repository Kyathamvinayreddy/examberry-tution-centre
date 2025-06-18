package org.examberry.app;

import org.examberry.service.BookingService;
import org.examberry.service.ReportService;
import org.examberry.service.StudentService;
import org.examberry.service.TimetableService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // 1. Generate an 8-week timetable at startup
        TimetableService.generateTimetable(8);

        // 2. Pre-register 10-15 sample students
        StudentService.preRegisterStudents();


        // 2. Main menu loop
        boolean running = true;
        while (running) {
            printMainMenu();
            String input = in.nextLine().trim();

            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number from the menu.");
                continue;
            }

            switch (choice) {
                case 1 -> StudentService.addStudent(in);
                case 2 -> StudentService.listStudents();
                case 3 -> BookingService.bookLesson(in);
                case 4 -> BookingService.changeBooking(in);
                case 5 -> BookingService.cancelBooking(in);
                case 6 -> BookingService.attendLessonAndReview(in);
                case 7 -> ReportService.printAllReports();
                case 0 -> {
                    System.out.println("Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice, try again.");
            }
        }

        in.close();
    }

    private static void printMainMenu() {
        System.out.println("\n==== Examberry Tuition Centre ====");
        System.out.println("1.  Add new student");
        System.out.println("2.  List all students");
        System.out.println("3.  Book a lesson");
        System.out.println("4.  Change existing booking");
        System.out.println("5.  Cancel a booking");
        System.out.println("6.  Attend lesson & submit review");
        System.out.println("7.  Print consolidated report");
        System.out.println("0.  Exit");
        System.out.print("Enter choice: ");
    }
}