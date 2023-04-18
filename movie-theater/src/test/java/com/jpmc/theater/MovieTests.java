package com.jpmc.theater;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MovieTests {
	/** Movie used for testing */
	Movie movie;
	/** Showing used for testing */
	Showing showing;
	
	/**
	 * Sets up the movie and showing that will be used for testing each method or constructor.
	 */
	@BeforeEach
	public void setUp() {
		movie = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, 0);
		showing = new Showing(movie, 5, LocalDateTime.of(LocalDate.of(2023, 4, 17), LocalTime.of(5, 0)));
	}
	
	/**
	 * Testing the movie object constructor.
	 */
	@Test
	public void testMovie() {
		// Check that the movie object was constructed correctly. Title, running time, and ticket price of the movie
		// should equal the values initialized in setUp.
		assertEquals("Spider-Man: No Way Home", movie.getTitle());
		assertEquals(Duration.ofMinutes(90), movie.getRunningTime());
		assertEquals(12.5, movie.getTicketPrice());
		assertEquals("", movie.getDescription());
		
		// Check if description is correct when it is included in construction of movie.
		movie = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, 0, "Testing");
		assertEquals("Testing", movie.getDescription());
	}
	
	/**
	 * Testing the calculateTicketPrice method on a basic movie with no discounts.
	 */
	@Test
	public void testCalculateTicketPriceNoDiscount() {
		// Check that the ticket price of the movie equals the value initialized in setUp. There should not be any
		// discounts applied yet so the ticket price should not be less than the value initialized.
		assertEquals(12.5, movie.calculateTicketPrice(showing));
	}
	
	/**
	 * Testing the calculateTicketPrice method on a special movie.
	 */
    @Test
    public void testCalculateTicketPriceSpecialDiscount() {
    	// Create new movie with same values as setUp except special code is now 1. This should trigger the special
    	// discount of 20 percent off.
    	movie = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, 1);
    	
        assertEquals(10, movie.calculateTicketPrice(showing));
    }
    
    /**
	 * Testing the calculateTicketPrice method on a movie with a sequence discount.
	 */
    @Test
    public void testCalculateTicketPriceSequenceDiscount() {
    	// Create new showing with same values as setUp except sequence of the day is 1. This should trigger a 3 dollar discount.
		showing = new Showing(movie, 1, LocalDateTime.of(LocalDate.of(2023, 4, 17), LocalTime.of(5, 0)));
        assertEquals(9.5, movie.calculateTicketPrice(showing));
        
        // Change the showing sequence of the day to 2. This should trigger a 2 dollar discount.
		showing = new Showing(movie, 2, LocalDateTime.of(LocalDate.of(2023, 4, 17), LocalTime.of(5, 0)));
        assertEquals(10.5, movie.calculateTicketPrice(showing));
    }
    
    /**
	 * Testing the calculateTicketPrice method on a movie with a time discount.
	 */
    @Test
    public void testCalculateTicketPriceTimeDiscount() {
    	// Create new showing with same values as setUp except time is between 11 AM and 4 PM . This should trigger a 25 percent discount.
		showing = new Showing(movie, 5, LocalDateTime.of(LocalDate.of(2023, 4, 17), LocalTime.of(13, 0)));
        assertEquals(9.375, movie.calculateTicketPrice(showing));
    }
    
    /**
	 * Testing the calculateTicketPrice method on a movie with a day discount.
	 */
    @Test
    public void testCalculateTicketPriceDayDiscount() {
    	// Create new showing with same values as setUp except the showing day is the 7th. This should trigger a 1 dollar discount.
		showing = new Showing(movie, 5, LocalDateTime.of(LocalDate.of(2023, 4, 7), LocalTime.of(5, 0)));
        assertEquals(11.5, movie.calculateTicketPrice(showing));
    }
    
    /**
	 * Testing the calculateTicketPrice method on a movie with multiple discounts.
	 */
    @Test
    public void testCalculateTicketPriceMultipleDiscounts() {
    	// Create new movie with same values as setUp except special code is now 1. This should trigger the special discount of 20 percent off.
    	movie = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, 1);
    	
    	// Create new showing with same values as setUp except sequence of the day is 1 and time is between 11 AM and 4 PM.
    	// This should trigger a 3 dollar sequence discount as well as a 25 percent time discount
		showing = new Showing(movie, 1, LocalDateTime.of(LocalDate.of(2023, 4, 17), LocalTime.of(12, 0)));
    	
    	// The sequence discount is 3 dollars off, the special discount is 2.5 dollars off, and the time discount is 3.125 dollars off.
    	// The final ticket price should choose the larger of the multiple discounts and subtract that from the
    	// ticket price. In this case, the largest discount is the time discount so 3.125 should be subtracted from initial ticket price.
        assertEquals(9.375, movie.calculateTicketPrice(showing));
    }
    
    /**
     * Testing the equals method on a movie
     */
    @SuppressWarnings("unlikely-arg-type")
	@Test
    public void testEquals() {
    	// Create temporary movie with same values as the movie used for testing.
    	Movie temp = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, 0);
    	
    	// Check that test movie and temporary movie are equal.
    	assertTrue(movie.equals(temp));
    	
    	// Check that test movie is equal to itself
    	assertTrue(movie.equals(movie));
    	
    	// Check that test movie is not equal to a null Movie
    	temp = null;
    	assertFalse(movie.equals(temp));
    	
    	// Check that test movie is not equal to an object of another class
    	assertFalse(movie.equals(showing));
    	
    	// Check that test movie is not equal to a movie with different values.
    	temp = new Movie("Spider-Man: No Way Home TESTING", Duration.ofMinutes(90),12.5, 0);
    	assertFalse(movie.equals(temp));
    	
    	temp = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(120),12.5, 0);
    	assertFalse(movie.equals(temp));
    	
    	temp = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),123.5, 0);
    	assertFalse(movie.equals(temp));
    	
    	temp = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, 1);
    	assertFalse(movie.equals(temp));
    }
    
    /**
     * Testing the hashCode method on a movie
     */
    @Test
    public void testHashCode() {
    	Movie temp = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, 0);
    	assertEquals(temp.hashCode(), movie.hashCode());
    	
    	temp = new Movie("Spider-Man: No Way Home TESTING", Duration.ofMinutes(90),12.5, 0);
    	assertNotEquals(temp.hashCode(), movie.hashCode());
    }
}
