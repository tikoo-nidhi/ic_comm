package com.app.iccomm.Model;

public class ReviewModel {
    int reviewId;
    String reviewHeading, reviewText, reviewName, reviewDate, reviewStars;

    public ReviewModel() {
    }

    public ReviewModel(int reviewId, String reviewHeading, String reviewText, String reviewName, String reviewDate, String reviewStars) {
        this.reviewId = reviewId;
        this.reviewHeading = reviewHeading;
        this.reviewText = reviewText;
        this.reviewName = reviewName;
        this.reviewDate = reviewDate;
        this.reviewStars = reviewStars;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewHeading() {
        return reviewHeading;
    }

    public void setReviewHeading(String reviewHeading) {
        this.reviewHeading = reviewHeading;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getReviewName() {
        return reviewName;
    }

    public void setReviewName(String reviewName) {
        this.reviewName = reviewName;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getReviewStars() {
        return reviewStars;
    }

    public void setReviewStars(String reviewStars) {
        this.reviewStars = reviewStars;
    }
}
