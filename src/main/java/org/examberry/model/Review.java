package org.examberry.model;

/**
 * A post‐lesson review with star‐rating.
 */
public class Review {
    private final String comments;
    private final int rating;  // 1–5

    /**
     * @param comments free‐text feedback
     * @param rating   1=Very dissatisfied … 5=Very satisfied
     */
    public Review(String comments, int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        this.comments = comments;
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return String.format("%d ★ – %s", rating, comments);
    }
}