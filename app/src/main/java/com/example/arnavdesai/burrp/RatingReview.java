package com.example.arnavdesai.burrp;

/**
 * Created by Arnav Desai on 9/22/2018.
 */


public class RatingReview {
    public float avgRating;
    public int noOfStudent;


    public RatingReview() {
    }

    public RatingReview(float avgRating, int noOfStudent) {
        this.avgRating = avgRating;
        this.noOfStudent = noOfStudent;
    }

    public float getAvgRating() {
        return avgRating;
    }

    public int getNoOfStudent() {
        return noOfStudent;
    }
}
