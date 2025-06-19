package org.examberry.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

class LessonTest {

    /**
     * Helper method to create a dummy student.
     */
    private Student createDummyStudent(String name) {
        return new Student(name, "M", LocalDate.of(2000, 1, 1), "Dummy Address", "0000000000");
    }

    @Test
    void testLessonCreationAndBookingCapacity() {
        // Create a Math lesson on a specific date with weekendNumber = 1
        Lesson lesson = new Lesson(Subject.MATH, LocalDate.of(2025, 6, 28),
                TimeSlot.AFTERNOON, 25.0, 1);

        // Add 4 bookings (capacity is 4, as defined in Lesson)
        for (int i = 0; i < 4; i++) {
            Booking booking = new Booking(createDummyStudent("Student" + i), lesson);
            lesson.addBooking(booking);
        }

        // Lesson should now be full.
        assertTrue(lesson.isFull(), "Lesson should be full after 4 bookings");

        // Attempting to add an extra booking should throw an exception.
        Booking extraBooking = new Booking(createDummyStudent("Extra"), lesson);
        Exception exception = assertThrows(IllegalStateException.class, () -> lesson.addBooking(extraBooking));
        assertTrue(exception.getMessage().toLowerCase().contains("full"),
                "Exception message should indicate that the lesson is full");
    }

    @Test
    void testLessonIncomeAndToString() {
        // Create an English lesson with weekendNumber = 2
        Lesson lesson = new Lesson(Subject.ENGLISH, LocalDate.of(2025, 7, 4),
                TimeSlot.MORNING, 30.0, 2);

        // At creation, income should be 0.
        assertEquals(0.0, lesson.getIncome(), "Initial income should be 0");

        // Add a booking and verify that income is updated.
        Booking booking = new Booking(createDummyStudent("Test Student"), lesson);
        lesson.addBooking(booking);
        assertEquals(30.0, lesson.getIncome(), "Income should be price * number of bookings");

        // Verify toString() produces a string that contains subject and weekend number.
        String lessonStr = lesson.toString();
        assertTrue(lessonStr.contains("ENGLISH"), "toString() should include the subject 'ENGLISH'");
        assertTrue(lessonStr.contains("2"), "toString() should include the weekend number");
    }
}