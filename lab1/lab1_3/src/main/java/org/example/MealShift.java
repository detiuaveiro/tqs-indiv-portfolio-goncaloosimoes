package org.example;

public enum MealShift {
    LUNCH(150), // 150 maximum capacity
    DINNER(100); // 100 maximum capacity

    private final int capacity;

    MealShift(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}