package com.jpmc.theater;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservationTests {
	/** The movie used for testing */
	Movie movie;
	/** The showing used for testing */
	Showing showing;
	/** The customer making the reservation used for testing */
	Customer customer;
	/** The reservation used for testing */
	Reservation reservation;
	
	/**
	 * Sets up the movie and showing that will be used for testing each method or constructor.
	 */
	@BeforeEach
	public void setUp() {
		movie = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, 0);
		showing = new Showing(movie, 5, LocalDateTime.of(LocalDate.of(2023, 4, 17), LocalTime.of(5, 0)));
		customer = new Customer("John Doe", "johndoe123");
		reservation = new Reservation(customer, showing, 4);
	}
	
	/**
	 * Testing the totalFee method in Reservation
	 */
    @Test
    void testTotalFee() {
    	assertDoesNotThrow(() -> reservation.totalFee());
        assertEquals(50, reservation.totalFee()); // Showing and movie should not have any discount. Results is 12.5 price times 4 tickets.
    }
    
    /**
     * Testing the getCustomer method in Reservation.
     */
    @Test
    public void testGetCustomer() {
    	assertEquals(customer, reservation.getCustomer());
    }
    
    /**
     * Testing the getShowing method in Reservation.
     */
    @Test
    public void testGetShowing() {
    	assertEquals(showing, reservation.getShowing());
    }
    
    /**
     * Testing the getAudienceCount method in Reservation.
     */
    @Test
    public void testGetAudienceCount() {
    	assertEquals(4, reservation.getAudienceCount());
    }
}
