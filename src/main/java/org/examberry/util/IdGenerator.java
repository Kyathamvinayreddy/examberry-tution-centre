package org.examberry.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Generates unique IDs per prefix (e.g. "S" for Student,
 * "L" for Lesson, "B" for Booking).
 */
public class IdGenerator {
    // Each prefix gets its own counter
    private static final Map<String, AtomicInteger> COUNTERS = new ConcurrentHashMap<>();

    /**
     * Returns the next ID for the given prefix.
     * e.g. nextId("S") → "S1001", "S1002", …
     */
    public static String nextId(String prefix) {
        // Initialize counter at 1000 if absent
        COUNTERS.putIfAbsent(prefix, new AtomicInteger(1000));
        int next = COUNTERS.get(prefix).getAndIncrement();
        return prefix + next;
    }
}