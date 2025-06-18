package org.examberry.service;

import org.examberry.model.Lesson;
import org.examberry.model.Subject;
import org.examberry.model.TimeSlot;
import org.examberry.util.DateUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Generates and manages tuition lesson schedules.
 */
public class TimetableService {
    private static final List<Lesson> lessons = new ArrayList<>();

    /**
     * Generates the tuition schedule for the next N weekends.
     * Clears any previously generated lessons.
     */
    public static void generateTimetable(int weekendsToGenerate) {
        lessons.clear();  // Clear any existing lessons.
        List<LocalDate> dates = DateUtil.nextWeekends(weekendsToGenerate);

        // Expect the dates list size to be weekendsToGenerate*2 (one Saturday and one Sunday per weekend).
        // For each weekend, create 4 lessons with fixed mappings.
        for (int i = 0; i < weekendsToGenerate; i++) {
            LocalDate saturday = dates.get(i * 2);
            LocalDate sunday = dates.get(i * 2 + 1);

            // Fixed prices for each subject
            double englishPrice = 30.0;
            double mathPrice = 25.0;
            double verbalPrice = 28.0;
            double nonVerbalPrice = 27.0;

            // Create lessons for the weekend:
            lessons.add(new Lesson(Subject.ENGLISH, saturday, TimeSlot.MORNING, englishPrice));
            lessons.add(new Lesson(Subject.MATH, saturday, TimeSlot.AFTERNOON, mathPrice));
            lessons.add(new Lesson(Subject.VERBAL_REASONING, sunday, TimeSlot.MORNING, verbalPrice));
            lessons.add(new Lesson(Subject.NON_VERBAL_REASONING, sunday, TimeSlot.AFTERNOON, nonVerbalPrice));
        }
        System.out.println("✔ Generated timetable for " + weekendsToGenerate
                + " weekends (" + lessons.size() + " lessons).");
    }

    /**
     * Prints every lesson (with ID, subject, date, slot, price, occupancy).
     */
    public static void showTimetable() {
        System.out.println("\n--- Tuition Timetable ---");
        if (lessons.isEmpty()) {
            System.out.println("No timetable generated yet. Call generateTimetable(...) first.");
            return;
        }
        lessons.forEach(System.out::println);
    }

    /**
     * Finds a lesson by its unique ID.
     */
    public static Lesson findLessonById(String lessonId) {
        return lessons.stream()
                .filter(l -> l.getId().equalsIgnoreCase(lessonId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Finds a lesson by Subject, date, and slot.
     */
    public static Lesson findLesson(Subject subject, LocalDate date, TimeSlot timeSlot) {
        return lessons.stream()
                .filter(l -> l.getSubject() == subject
                        && l.getDate().equals(date)
                        && l.getTimeSlot() == timeSlot)
                .findFirst()
                .orElse(null);
    }

    /**
     * Exposes an unmodifiable copy of all lessons.
     */
    public static List<Lesson> getAllLessons() {
        return Collections.unmodifiableList(new ArrayList<>(lessons));
    }
}