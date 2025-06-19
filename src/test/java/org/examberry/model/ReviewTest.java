package org.examberry.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReviewTest {

    @Test
    void testValidReviewCreation() {
        String comments = "Excellent session!";
        int rating = 5;
        Review review = new Review(comments, rating);

        // Verify that the review was created correctly.
        assertEquals(comments, review.getComments(), "Comments should match.");
        assertEquals(rating, review.getRating(), "Rating should match.");

        // Ensure the toString() output contains expected parts.
        String reviewStr = review.toString();
        assertTrue(reviewStr.contains("5"), "toString should contain the rating.");
        assertTrue(reviewStr.contains(comments), "toString should contain the comments.");
    }

    @Test
    void testInvalidReviewRatingLow() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Review("Too bad", 0);
        });
        String expectedMessage = "Rating must be between 1 and 5";
        assertTrue(exception.getMessage().contains(expectedMessage),
                "Exception message should indicate rating must be between 1 and 5.");
    }

    @Test
    void testInvalidReviewRatingHigh() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Review("Too good", 6);
        });
        String expectedMessage = "Rating must be between 1 and 5";
        assertTrue(exception.getMessage().contains(expectedMessage),
                "Exception message should indicate rating must be between 1 and 5.");
    }
}