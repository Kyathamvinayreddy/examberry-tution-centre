package org.examberry.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.examberry.util.IdGenerator;

/**
 * Represents a student at the tuition centre.
 */
public class Student {
    private final String id;
    private String name;
    private String gender;
    private LocalDate dob;
    private String address;
    private String emergencyContact;
    private final List<Booking> bookings = new ArrayList<>();

    public Student(String name,
                   String gender,
                   LocalDate dob,
                   String address,
                   String emergencyContact) {
        this.id = IdGenerator.nextId("S");
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.address = address;
        this.emergencyContact = emergencyContact;
    }

    // --- Getters & Setters ---
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    /**
     * @return an unmodifiable view of this student's bookings.
     */
    public List<Booking> getBookings() {
        return Collections.unmodifiableList(bookings);
    }

    /**
     * Adds a new booking for this student.
     */
    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    /**
     * Removes a booking (e.g. on cancel).
     */
    public void removeBooking(Booking booking) {
        bookings.remove(booking);
    }

    @Override
    public String toString() {
        return String.format(" %s | %s | %s | %s | %s | %s",
                id, name, gender, dob, address, emergencyContact);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student other = (Student) o;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}