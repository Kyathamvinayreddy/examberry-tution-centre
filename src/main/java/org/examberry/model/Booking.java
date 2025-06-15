package org.examberry.model;

import org.examberry.util.IdGenerator;

/**
 * Represents a student's booking of a Lesson.
 */
public class Booking {
    private final String id;
    private final Student student;
    private Lesson lesson;
    private BookingStatus status;
    private Review review;

    /**
     * Create a new booking in BOOKED status.
     */
    public Booking(Student student, Lesson lesson) {
        this.id = IdGenerator.nextId("B");
        this.student = student;
        this.lesson = lesson;
        this.status = BookingStatus.BOOKED;
    }

    // --- Getters ---
    public String getId() { return id; }
    public Student getStudent() { return student; }
    public Lesson getLesson() { return lesson; }
    public BookingStatus getStatus() { return status; }
    public Review getReview() { return review; }

    /**
     * Check in the student (mark as ATTENDED).
     */
    public void checkIn() {
        if (status != BookingStatus.BOOKED) {
            throw new IllegalStateException(
                    "Cannot check in a booking in state " + status);
        }
        status = BookingStatus.ATTENDED;
    }

    /**
     * Change this booking to another Lesson of the same subject.
     * Handles removing/adding from the old/new Lesson.
     */
    public void changeLesson(Lesson newLesson) {
        if (status != BookingStatus.BOOKED) {
            throw new IllegalStateException("Only booked lessons can be changed.");
        }
        if (!lesson.getSubject().equals(newLesson.getSubject())) {
            throw new IllegalArgumentException(
                    "Subject mismatch: cannot change to " + newLesson.getSubject());
        }
        if (newLesson.isFull()) {
            throw new IllegalStateException(
                    "Cannot change to full lesson: " + newLesson.getId());
        }
        // remove from old lesson
        lesson.removeBooking(this);
        // reassign
        lesson = newLesson;
        newLesson.addBooking(this);
    }

    /**
     * Cancel this booking: remove from lesson & student, mark CANCELLED.
     */
    public void cancel() {
        if (status == BookingStatus.CANCELLED) {
            throw new IllegalStateException("Booking already cancelled.");
        }
        lesson.removeBooking(this);
        student.removeBooking(this);
        status = BookingStatus.CANCELLED;
    }

    /**
     * Attach a review once the lesson has been attended.
     */
    public void addReview(Review review) {
        if (status != BookingStatus.ATTENDED) {
            throw new IllegalStateException(
                    "Cannot review unless booking is ATTENDED.");
        }
        this.review = review;
    }

    @Override
    public String toString() {
        return String.format("%s | Student:%s | Lesson:%s | %s",
                id, student.getId(), lesson.getId(), status);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Booking)) return false;
        Booking other = (Booking) o;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}