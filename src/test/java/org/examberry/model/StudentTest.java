package org.examberry.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void testStudentCreation() {
        // Create a student instance.
        Student student = new Student("Alice Smith", "F", LocalDate.of(2001, 3, 25),
                "1 Home Street, Hatfield", "07123456789");
        // Check that ID is generated and toString() contains critical info.
        assertNotNull(student.getId(), "Student ID should not be null");
        String studentStr = student.toString();
        assertTrue(studentStr.contains("Alice"), "Student name should be in the string");
        assertTrue(studentStr.contains("Hatfield"), "Student address should be in the string");
        assertTrue(studentStr.contains("07123456789"), "Emergency contact should be in the string");
    }

    @Test
    void testAddAndRemoveBooking() {
        Student student = new Student("Bob Williams", "M", LocalDate.of(2000, 7, 14),
                "2 Main Road, Luton", "07234567890");
        // Create a dummy lesson to associate the booking.
        Lesson lesson = new Lesson(Subject.ENGLISH, LocalDate.now(), TimeSlot.MORNING, 30.0, 1);
        Booking booking = new Booking(student, lesson);

        // Test adding booking.
        student.addBooking(booking);
        assertEquals(1, student.getBookings().size(), "Student should have 1 booking");

        // Test removal.
        student.removeBooking(booking);
        assertEquals(0, student.getBookings().size(), "Student should have 0 bookings after removal");
    }
}