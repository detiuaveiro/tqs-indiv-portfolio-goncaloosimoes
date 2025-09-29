package org.example;

import java.time.LocalDate;

public class MealBookingRequest {
    private String userId;
    private LocalDate date;
    private MealShift shift;

    public MealBookingRequest(String userId, LocalDate date, MealShift shift) {
        this.userId = userId;
        this.date = date;
        this.shift = shift;
    }

    // Getters
    public String getUserId() {
        return userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public MealShift getShift() {
        return shift;
    }
}