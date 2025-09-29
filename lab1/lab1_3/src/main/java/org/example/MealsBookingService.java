package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class MealsBookingService {
    private Map<String, MealBookingRequest> bookings = new HashMap<>();
    private Map<String, ReservationState> bookingStates = new HashMap<>();
    private Map<String, Integer> noShows = new HashMap<>();
    private Map<LocalDate, Map<MealShift, Integer>> bookingCounts = new HashMap<>();

    public String bookMeal(MealBookingRequest request) {
        validateBookingRequest(request);

        String token = generateToken(request);
        bookings.put(token, request);
        bookingStates.put(token, ReservationState.ACTIVE);
        updateBookingCount(request.getDate(), request.getShift());

        return token;
    }

    public Optional<MealBookingRequest> findReservation(String token) {
        if (!isValidReservation(token)) {
            return Optional.empty();
        }
        return Optional.ofNullable(bookings.get(token));
    }

    public void checkIn(String token) {
        if (!isValidReservation(token)) {
            throw new IllegalArgumentException("Invalid or used token");
        }
        bookingStates.put(token, ReservationState.USED);
    }

    public void cancelReservation(String token) {
        if (!bookings.containsKey(token)) {
            throw new IllegalArgumentException("Reservation not found");
        }
        if (bookingStates.get(token) != ReservationState.ACTIVE) {
            throw new IllegalStateException("Cannot cancel non-active reservation");
        }

        MealBookingRequest request = bookings.get(token);
        if (isTooLateToCancel(request)) {
            throw new IllegalStateException("Too late to cancel");
        }

        bookingStates.put(token, ReservationState.CANCELLED);
        decrementBookingCount(request.getDate(), request.getShift());
    }

    private void validateBookingRequest(MealBookingRequest request) {
        if (hasActiveBooking(request.getUserId(), request.getDate(), request.getShift())) {
            throw new IllegalStateException("Student already has a booking for this shift");
        }
        if (isBookingFull(request.getDate(), request.getShift())) {
            throw new IllegalStateException("Shift is fully booked");
        }
        if (isTooLateToBook(request)) {
            throw new IllegalStateException("Too late to book");
        }
        if (isStudentBlocked(request.getUserId())) {
            throw new IllegalStateException("Student is blocked due to multiple no-shows");
        }
    }

    private boolean isValidReservation(String token) {
        return bookings.containsKey(token) &&
                bookingStates.get(token) == ReservationState.ACTIVE;
    }

    private boolean hasActiveBooking(String userId, LocalDate date, MealShift shift) {
        return bookings.values().stream()
                .anyMatch(b -> b.getUserId().equals(userId) &&
                        b.getDate().equals(date) &&
                        b.getShift() == shift);
    }

    private boolean isBookingFull(LocalDate date, MealShift shift) {
        return getBookingCount(date, shift) >= shift.getCapacity();
    }

    private boolean isTooLateToBook(MealBookingRequest request) {
        return LocalDateTime.now().plusHours(1).isAfter(request.getDate().atStartOfDay());
    }

    private boolean isTooLateToCancel(MealBookingRequest request) {
        return LocalDateTime.now().plusHours(2).isAfter(request.getDate().atStartOfDay());
    }

    private boolean isStudentBlocked(String userId) {
        return noShows.getOrDefault(userId, 0) >= 3;
    }

    private String generateToken(MealBookingRequest request) {
        return UUID.randomUUID().toString();
    }

    private void updateBookingCount(LocalDate date, MealShift shift) {
        bookingCounts.computeIfAbsent(date, k -> new HashMap<>())
                .merge(shift, 1, Integer::sum);
    }

    private void decrementBookingCount(LocalDate date, MealShift shift) {
        bookingCounts.get(date).merge(shift, -1, Integer::sum);
    }

    private int getBookingCount(LocalDate date, MealShift shift) {
        return bookingCounts.getOrDefault(date, Collections.emptyMap())
                .getOrDefault(shift, 0);
    }
}