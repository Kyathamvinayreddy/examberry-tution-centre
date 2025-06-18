package org.examberry.service;

import org.examberry.model.Student;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Manages the in‐memory list of Students
 * and provides console workflows for adding/listing.
 */
public class StudentService {
    private static final List<Student> students = new ArrayList<>();

    /**
     * Menu action: prompt for student details, create & store a new Student.
     */
    public static void addStudent(Scanner in) {
        System.out.println("\n--- Add New Student ---");
        try {
            System.out.print("Name: ");
            String name = in.nextLine().trim();

            System.out.print("Gender: ");
            String gender = in.nextLine().trim();

            System.out.print("Date of Birth (YYYY-MM-DD): ");
            LocalDate dob = LocalDate.parse(in.nextLine().trim());

            System.out.print("Address: ");
            String address = in.nextLine().trim();

            System.out.print("Emergency Contact Phone: ");
            String emergency = in.nextLine().trim();

            Student s = new Student(name, gender, dob, address, emergency);
            students.add(s);
            System.out.println("✔ Student added with ID: " + s.getId());
        } catch (DateTimeParseException e) {
            System.out.println("✘ Invalid date format. Please use YYYY-MM-DD.");
        }
    }

    /**
     * Menu action: list all stored students.
     */
    public static void listStudents() {
        System.out.println("\n--- All Students ---");
        if (students.isEmpty()) {
            System.out.println("No students in the system yet.");
            return;
        }
        students.forEach(s -> System.out.println(s));
    }

    /**
     * Lookup helper for other services.
     */
    public static Student findStudentById(String id) {
        return students.stream()
                .filter(s -> s.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Exposes a copy of the list for reports or batch ops.
     */
    public static List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public static void preRegisterStudents() {
        students.add(new Student("Alice Smith", "F", LocalDate.of(2001, 3, 25), "1 Home Street, Hatfield", "07123456789"));
        students.add(new Student("Bob Williams", "M", LocalDate.of(2000, 7, 14), "2 Main Road, Luton", "07234567890"));
        students.add(new Student("Charlie Brown", "M", LocalDate.of(1999, 1, 5), "3 Elm Avenue, Hatfield", "07345678901"));
        students.add(new Student("Diana Evans", "F", LocalDate.of(2002, 6, 22), "4 Cedar Lane, Luton", "07456789012"));
        students.add(new Student("Ethan Thomas", "M", LocalDate.of(2001, 11, 11), "5 Birch Road, Hatfield", "07567890123"));
        students.add(new Student("Fiona Garcia", "F", LocalDate.of(2000, 4, 30), "6 Spruce Street, Luton", "07678901234"));
        students.add(new Student("George Clark", "M", LocalDate.of(1998, 9, 17), "7 Willow Drive, Hatfield", "07789012345"));
        students.add(new Student("Hannah Lewis", "F", LocalDate.of(1999, 2, 28), "8 Ash Lane, Luton", "07890123456"));
        students.add(new Student("Ian Wilson", "M", LocalDate.of(2002, 8, 3), "9 Poplar Street, Hatfield", "07901234567"));
        students.add(new Student("Jasmine Scott", "F", LocalDate.of(2001, 12, 7), "10 Cherry Ave, Luton", "07123456780"));
        students.add(new Student("Kevin Hill", "M", LocalDate.of(2000, 3, 18), "11 Walnut Road, Hatfield", "07234567891"));
        students.add(new Student("Laura Adams", "F", LocalDate.of(1999, 11, 30), "12 Olive Drive, Luton", "07345678902"));
        students.add(new Student("Michael Perez", "M", LocalDate.of(2001, 7, 22), "13 Poplar Line, Hatfield", "07456789013"));
        System.out.println("✔ Pre-registered " + students.size() + " students.");
    }
}