package org.examberry.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.examberry.util.IdGenerator;

/**
 * Represents a tuition lesson slot.
 */
public class Lesson {
    private final String id;
    private Subject subject;
    private LocalDate date;
    private TimeSlot timeSlot;
    private double price;
    private final int capacity = 4;
    private int weekendNumber;
    private final List<Booking> bookings = new ArrayList<>();

    /**
     * Constructs a Lesson.
     *
     * @param subject  the lesson subject
     * @param date     the calendar date (Saturday or Sunday)
     * @param timeSlot MORNING or AFTERNOON
     * @param price    price per student
     * @param i
     */
    public Lesson(Subject subject, LocalDate date, TimeSlot timeSlot, double price, int i) {
        this.id = IdGenerator.nextId("L");
        this.subject = subject;
        this.date = date;
        this.timeSlot = timeSlot;
        this.price = price;
        this.weekendNumber = weekendNumber;
    }

    // --- Getters & Setters ---
    public String getId() {
        return id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public int getWeekendNumber() {
        return weekendNumber;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCapacity() {
        return capacity;
    }

    /**
     * Returns an unmodifiable view of current bookings.
     */
    public List<Booking> getBookings() {
        return Collections.unmodifiableList(bookings);
    }

    /**
     * @return true if no more students can book this lesson.
     */
    public boolean isFull() {
        return bookings.size() >= capacity;
    }

    /**
     * Adds a booking to this lesson.
     *
     * @throws IllegalStateException if lesson is full or booking already exists
     */
    public void addBooking(Booking booking) {
        if (isFull()) {
            throw new IllegalStateException("Lesson is full: " + id);
        }
        if (bookings.contains(booking)) {
            throw new IllegalStateException("Booking already exists for lesson: " + id);
        }
        bookings.add(booking);
    }

    /**
     * Removes a booking (e.g. on cancellation).
     *
     * @return true if removed, false if not found
     */
    public boolean removeBooking(Booking booking) {
        return bookings.remove(booking);
    }

    /**
     * Calculates total income from this lesson.
     */
    public double getIncome() {
        return bookings.size() * price;
    }

    @Override
    public String toString() {
        return String.format("%s | %-20s | %s %s | £%.2f | %d/%d",
                id,
                subject,
                date,
                timeSlot,
                price,
                bookings.size(),
                capacity
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lesson)) return false;
        Lesson other = (Lesson) o;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}