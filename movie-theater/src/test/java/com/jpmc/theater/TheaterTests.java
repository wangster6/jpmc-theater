package com.jpmc.theater;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TheaterTests {
	Theater theater;
	Customer john;
	
	final Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1);
	final Movie turningRed = new Movie("Turning Red", Duration.ofMinutes(85), 11, 0);
	final Movie theBatMan = new Movie("The Batman", Duration.ofMinutes(95), 9, 0);
	
	final Showing showing1 = new Showing(turningRed, 1, LocalDateTime.of(LocalDate.of(2023, 4, 17), LocalTime.of(9, 0)));
	final Showing showing2 = new Showing(spiderMan, 2, LocalDateTime.of(LocalDate.of(2023, 4, 17), LocalTime.of(11, 0)));
	final Showing showing3 = new Showing(theBatMan, 3, LocalDateTime.of(LocalDate.of(2023, 4, 17), LocalTime.of(12, 50)));
	final Showing showing4 = new Showing(turningRed, 4, LocalDateTime.of(LocalDate.of(2023, 4, 17), LocalTime.of(14, 30)));
	final Showing showing5 = new Showing(spiderMan, 5, LocalDateTime.of(LocalDate.of(2023, 4, 17), LocalTime.of(16, 10)));
	final Showing showing6 = new Showing(theBatMan, 6, LocalDateTime.of(LocalDate.of(2023, 4, 17), LocalTime.of(17, 50)));
	final Showing showing7 = new Showing(turningRed, 7, LocalDateTime.of(LocalDate.of(2023, 4, 17), LocalTime.of(19, 30)));
	final Showing showing8 = new Showing(spiderMan, 8, LocalDateTime.of(LocalDate.of(2023, 4, 17), LocalTime.of(21, 10)));
	
	@BeforeEach
	public void setUp() {
        john = new Customer("John Doe", "johndoe123");
        theater = new Theater();

        theater.addShowingToSchedule(showing1);
        theater.addShowingToSchedule(showing2);
        theater.addShowingToSchedule(showing3);
        theater.addShowingToSchedule(showing4);
        theater.addShowingToSchedule(showing5);
        theater.addShowingToSchedule(showing6);
        theater.addShowingToSchedule(showing7);
        theater.addShowingToSchedule(showing8);
        theater.addShowingToSchedule(theBatMan, 9, LocalDateTime.of(LocalDate.of(2023, 4, 17), LocalTime.of(23, 0)));
	}
	
	/**
	 * Testing the reserve method in Theater
	 */
	@Test
	public void testReserve() {
		Reservation newReservation = theater.reserve(john, 1, 4);
		assertEquals(john, newReservation.getCustomer());
		assertEquals(showing1, newReservation.getShowing());
		assertEquals(4, newReservation.getAudienceCount());
		
		Exception e = assertThrows(IllegalArgumentException.class, () -> theater.reserve(john, 20, 1));
		assertEquals("Invalid sequence. There are no showings with the sequence: " + String.valueOf(20), e.getMessage());
	}

    @Test
    void printMovieScheduleText() {
        theater.printScheduleText();
    }
    
    @Test
    void printMovieScheduleJson() {
        theater.printScheduleJson();
    }
}
