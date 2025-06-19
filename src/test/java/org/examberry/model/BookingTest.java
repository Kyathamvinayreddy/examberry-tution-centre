package org.examberry.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

class BookingTest {

    @Test
    void testBookingCheckInAndReview() {
        Student student = new Student("Charlie Brown", "M", LocalDate.of(2000, 2, 2),
                "3 Elm Street", "07345678901");
        Lesson lesson = new Lesson(Subject.VERBAL_REASONING, LocalDate.of(2025, 7, 12),
                TimeSlot.MORNING, 28.0, 2);
        Booking booking = new Booking(student, lesson);
        lesson.addBooking(booking);
        student.addBooking(booking);

        // Initially, the booking should be in BOOKED state.
        assertEquals(BookingStatus.BOOKED, booking.getStatus(), "Booking should start as BOOKED");

        // Check in the student.
        booking.checkIn();
        assertEquals(BookingStatus.ATTENDED, booking.getStatus(), "Booking should be marked as ATTENDED after checkIn");

        // Add a review.
        Review review = new Review("Great lesson", 5);
        booking.addReview(review);
        assertNotNull(booking.getReview(), "Booking should now have a review");
        assertEquals(5, booking.getReview().getRating(), "Rating should be 5");
    }

    @Test
    void testBookingCancel() {
        Student student = new Student("Diana Evans", "F", LocalDate.of(2000, 3, 3),
                "4 Cedar Lane", "07456789012");
        Lesson lesson = new Lesson(Subject.MATH, LocalDate.of(2025, 7, 13),
                TimeSlot.AFTERNOON, 25.0, 1);
        Booking booking = new Booking(student, lesson);
        lesson.addBooking(booking);
        student.addBooking(booking);

        // Cancel the booking.
        booking.cancel();
        assertEquals(BookingStatus.CANCELLED, booking.getStatus(), "Booking status should be CANCELLED after cancellation");
        // Verify removal from both student and lesson booking lists.
        assertFalse(lesson.getBookings().contains(booking), "Booking should be removed from lesson after cancellation");
        assertFalse(student.getBookings().contains(booking), "Booking should be removed from student after cancellation");
    }
}