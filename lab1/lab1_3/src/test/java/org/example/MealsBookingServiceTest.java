package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MealsBookingServiceTest {
    private MealsBookingService service;
    private final String VALID_USER_ID = "student123";
    private final LocalDate TOMORROW = LocalDate.now().plusDays(1);

    @BeforeEach
    void setUp() {
        service = new MealsBookingService();
    }

    @Nested
    @DisplayName("Booking meals")
    class BookingTests {
        @Test
        @DisplayName("Should successfully book a meal")
        void successfulBooking() {
            MealBookingRequest request = new MealBookingRequest(VALID_USER_ID, TOMORROW, MealShift.LUNCH);
            String token = service.bookMeal(request);

            assertNotNull(token);
            assertTrue(service.findReservation(token).isPresent());
        }

        @Test
        @DisplayName("Should prevent double booking for same shift")
        void preventDoubleBooking() {
            MealBookingRequest request1 = new MealBookingRequest(VALID_USER_ID, TOMORROW, MealShift.LUNCH);
            MealBookingRequest request2 = new MealBookingRequest(VALID_USER_ID, TOMORROW, MealShift.LUNCH);

            service.bookMeal(request1);

            assertThrows(IllegalStateException.class, () -> service.bookMeal(request2));
        }

        @Test
        @DisplayName("Should enforce capacity limits")
        void enforceCapacityLimits() {
            MealShift shift = MealShift.LUNCH;
            int capacity = shift.getCapacity();

            // Book until full
            for (int i = 0; i < capacity; i++) {
                service.bookMeal(new MealBookingRequest("user" + i, TOMORROW, shift));
            }

            // Try to book when full
            MealBookingRequest overCapacityRequest = new MealBookingRequest("userExtra", TOMORROW, shift);
            assertThrows(IllegalStateException.class, () -> service.bookMeal(overCapacityRequest));
        }
    }

    @Nested
    @DisplayName("Check-in process")
    class CheckInTests {
        @Test
        @DisplayName("Should successfully check in with valid token")
        void successfulCheckIn() {
            MealBookingRequest request = new MealBookingRequest(VALID_USER_ID, TOMORROW, MealShift.LUNCH);
            String token = service.bookMeal(request);

            assertDoesNotThrow(() -> service.checkIn(token));
            assertThrows(IllegalArgumentException.class, () -> service.checkIn(token)); // Can't check in twice
        }

        @Test
        @DisplayName("Should reject invalid tokens")
        void rejectInvalidTokens() {
            assertThrows(IllegalArgumentException.class, () -> service.checkIn("invalid-token"));
        }
    }

    @Nested
    @DisplayName("Cancellation process")
    class CancellationTests {
        @Test
        @DisplayName("Should successfully cancel valid reservation")
        void successfulCancellation() {
            MealBookingRequest request = new MealBookingRequest(VALID_USER_ID, TOMORROW, MealShift.LUNCH);
            String token = service.bookMeal(request);

            assertDoesNotThrow(() -> service.cancelReservation(token));
            assertTrue(service.findReservation(token).isEmpty());
        }

        @Test
        @DisplayName("Should prevent canceling used reservation")
        void preventCancelingUsedReservation() {
            MealBookingRequest request = new MealBookingRequest(VALID_USER_ID, TOMORROW, MealShift.LUNCH);
            String token = service.bookMeal(request);

            service.checkIn(token);
            assertThrows(IllegalStateException.class, () -> service.cancelReservation(token));
        }
    }

    @Nested
    @DisplayName("Reservation lookup")
    class LookupTests {
        @Test
        @DisplayName("Should find valid reservation")
        void findValidReservation() {
            MealBookingRequest request = new MealBookingRequest(VALID_USER_ID, TOMORROW, MealShift.LUNCH);
            String token = service.bookMeal(request);

            Optional<MealBookingRequest> found = service.findReservation(token);
            assertTrue(found.isPresent());
            assertEquals(VALID_USER_ID, found.get().getUserId());
        }

        @Test
        @DisplayName("Should return empty for invalid token")
        void returnEmptyForInvalidToken() {
            assertTrue(service.findReservation("invalid-token").isEmpty());
        }
    }
}