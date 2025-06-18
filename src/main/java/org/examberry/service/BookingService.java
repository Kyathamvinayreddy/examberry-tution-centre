package org.examberry.service;

import org.examberry.model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Handles booking, changing, cancelling, attending lessons & reviews.
 */
public class BookingService {
    private static final List<Booking> bookings = new ArrayList<>();

    /**
     * Menu action: book a new lesson for a student.
     */
    public static void bookLesson(Scanner in) {
        System.out.println("\n--- Book a Lesson ---");
        // 1. Show timetable
        TimetableService.showTimetable();

        // 2. Choose student
        System.out.print("Enter student ID: ");
        String sid = in.nextLine().trim();
        Student student = StudentService.findStudentById(sid);
        if (student == null) {
            System.out.println("✘ Student not found.");
            return;
        }

        // 3. Choose lesson
        System.out.print("Enter lesson ID: ");
        String lid = in.nextLine().trim();
        Lesson lesson = TimetableService.findLessonById(lid);
        if (lesson == null) {
            System.out.println("✘ Lesson not found.");
            return;
        }

        // 4. Check capacity
        if (lesson.isFull()) {
            System.out.println("✘ Lesson is full.");
            return;
        }

        // 5. Check time conflicts
        boolean conflict = student.getBookings().stream()
                .filter(b -> b.getStatus() == BookingStatus.BOOKED)
                .anyMatch(b -> {
                    Lesson l = b.getLesson();
                    return l.getDate().equals(lesson.getDate())
                            && l.getTimeSlot() == lesson.getTimeSlot();
                });
        if (conflict) {
            System.out.println("✘ You already have a booking at that time.");
            return;
        }

        // 6. Create & register booking
        Booking booking = new Booking(student, lesson);
        try {
            lesson.addBooking(booking);
            student.addBooking(booking);
            bookings.add(booking);
            System.out.println("✔ Booking created with ID: " + booking.getId());
        } catch (IllegalStateException e) {
            System.out.println("✘ " + e.getMessage());
        }
    }

    /**
     * Menu action: change an existing booking's date/time (same subject).
     */
    public static void changeBooking(Scanner in) {
        System.out.println("\n--- Change Booking ---");
        System.out.print("Enter booking ID: ");
        String bid = in.nextLine().trim();
        Booking booking = findBookingById(bid);
        if (booking == null) {
            System.out.println("✘ Booking not found.");
            return;
        }
        if (booking.getStatus() != BookingStatus.BOOKED) {
            System.out.println("✘ Only booked lessons can be changed.");
            return;
        }

        // show timetable
        TimetableService.showTimetable();
        System.out.print("Enter new lesson ID (same subject): ");
        String lid = in.nextLine().trim();
        Lesson newLesson = TimetableService.findLessonById(lid);
        if (newLesson == null) {
            System.out.println("✘ Lesson not found.");
            return;
        }

        try {
            booking.changeLesson(newLesson);
            System.out.println("✔ Booking moved to lesson " + newLesson.getId());
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("✘ " + e.getMessage());
        }
    }

    /**
     * Menu action: cancel an existing booking.
     */
    public static void cancelBooking(Scanner in) {
        System.out.println("\n--- Cancel Booking ---");
        System.out.print("Enter booking ID: ");
        String bid = in.nextLine().trim();
        Booking booking = findBookingById(bid);
        if (booking == null) {
            System.out.println("✘ Booking not found.");
            return;
        }

        try {
            booking.cancel();
            System.out.println("✔ Booking " + bid + " cancelled.");
        } catch (IllegalStateException e) {
            System.out.println("✘ " + e.getMessage());
        }
    }

    /**
     * Menu action: check in for a lesson and immediately submit a review.
     */
    public static void attendLessonAndReview(Scanner in) {
        System.out.println("\n--- Attend Lesson & Review ---");
        System.out.print("Enter booking ID: ");
        String bid = in.nextLine().trim();
        Booking booking = findBookingById(bid);
        if (booking == null) {
            System.out.println("✘ Booking not found.");
            return;
        }
        if (booking.getStatus() != BookingStatus.BOOKED) {
            System.out.println("✘ Only booked lessons can be attended.");
            return;
        }

        // 1. Check in
        booking.checkIn();
        System.out.println("✔ Checked in for lesson " + booking.getLesson().getId());

        // 2. Collect review
        System.out.print("Enter your review comments: ");
        String comments = in.nextLine().trim();
        System.out.print("Enter rating (1–5): ");
        int rating;
        try {
            rating = Integer.parseInt(in.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("✘ Invalid rating; must be a number 1–5.");
            return;
        }

        try {
            Review review = new Review(comments, rating);
            booking.addReview(review);
            System.out.println("✔ Review saved.");
        } catch (IllegalStateException | IllegalArgumentException e) {
            System.out.println("✘ " + e.getMessage());
        }
    }

    /**
     * Helper to find a booking by ID.
     */
    public static Booking findBookingById(String bookingId) {
        return bookings.stream()
                .filter(b -> b.getId().equalsIgnoreCase(bookingId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Exposes all bookings (for reports or tests).
     */
    public static List<Booking> getAllBookings() {
        return new ArrayList<>(bookings);
    }
}