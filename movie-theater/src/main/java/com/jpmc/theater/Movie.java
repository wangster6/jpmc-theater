package com.jpmc.theater;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;

/**
 * This class represents a Movie object. Each Movie has a title, description, running time, ticket price, and special code.
 * 
 * @author Ray Wang
 */
public class Movie {
	/** The special code that can provide customers with a 20% discount for a special movie */
    private static final int MOVIE_CODE_SPECIAL = 1;
    
    /** Title of the movie */
    private String title;
    /** Description of the movie */
    private String description;
    /** Running time of the movie */
    private Duration runningTime;
    /** Ticket price of the movie */
    private double ticketPrice;
    /** The special code of the movie */
    private int specialCode;

    /**
     * Constructs the Movie object with the provided parameters.
     * @param title of the movie
     * @param runningTime of the movie
     * @param ticketPrice of the movie
     * @param specialCode to check for discounts
     */
    public Movie(String title, Duration runningTime, double ticketPrice, int specialCode, String description) {
        this.title = title;
        this.runningTime = runningTime;
        this.ticketPrice = ticketPrice;
        this.specialCode = specialCode;
        this.description = description;
    }
    
    /**
     * Constructs the Movie object with the provided parameters. If no description is specified for the movie, set it to empty string.
     * @param title of the movie
     * @param runningTime of the movie
     * @param ticketPrice of the movie
     * @param specialCode to check for discounts
     */
    public Movie(String title, Duration runningTime, double ticketPrice, int specialCode) {
        this(title, runningTime, ticketPrice, specialCode, "");
    }

    /**
     * Returns the title of the movie
     * @return the title of the movie
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Returns the running time of the movie
     * @return the running time of the movie
     */
    public Duration getRunningTime() {
        return this.runningTime;
    }

    /**
     * Returns the ticket price of the movie
     * @return the ticket price of the movie
     */
    public double getTicketPrice() {
        return this.ticketPrice;
    }
    
    /**
     * Returns the description of the movie
     * @return the description of the movie
     */
    public String getDescription() {
        return this.description;
    }

    /**
	 * Returns the total discount for the showing in dollar value.
	 * @param showSequence displays number of times the movie has been shown for the day
	 * @return the total discount for the showing in dollar value
	 */
	private double getDiscount(Showing showing) {
		// Used to keep track of largest discount. Starts at 0 and if each discount is greater, then set this value to that larger discount.
		double largestDiscount = 0; 
		
		// Checking for special movie discount
	    if (MOVIE_CODE_SPECIAL == specialCode) {
	        largestDiscount = ticketPrice * 0.2;  // 20% discount for special movie
	    }
	    
	    // Checking for sequence discount
	    int showSequence = showing.getSequenceOfTheDay();
	    if (showSequence == 1) {
	    	if(3 > largestDiscount) {
	    		largestDiscount = 3; // 3 dollar discount for first showing of the day
	    	}
	    } else if (showSequence == 2) {
	    	if(2 > largestDiscount) {
	    		largestDiscount = 2; // 2 dollar discount for second showing of the day
	    	}
	    }
	
	    // Checking for time discount
	    LocalTime timeDiscountStart = LocalTime.of(11, 0);
	    LocalTime timeDiscountEnd = LocalTime.of(16, 0);
	    LocalTime showingTime = showing.getStartTime().toLocalTime();
	    if(showingTime.isAfter(timeDiscountStart) && showingTime.isBefore(timeDiscountEnd)) {
	    	if(ticketPrice * 0.25 > largestDiscount) {
	    		largestDiscount = ticketPrice * 0.25;
	    	}
	    }
	    
	    // Checking for 7th day discount
	    if(showing.getStartTime().getDayOfMonth() == 7) {
	    	if(1 > largestDiscount) {
	    		largestDiscount = 1;
	    	}
	    }
	    
	    return largestDiscount;
	}

	/**
     * Calculates the ticket price of the movie after applying the discount.
     * @param showing the movie showing of the day. Used to calculate sequence of the day.
     * @return Final ticket price after discount.
     */
    public double calculateTicketPrice(Showing showing) {
        return ticketPrice - getDiscount(showing);
    }

    /**
     * Checks if movie objects are equal by comparing their variable values.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Double.compare(movie.ticketPrice, ticketPrice) == 0
                && Objects.equals(title, movie.title)
                && Objects.equals(description, movie.description)
                && Objects.equals(runningTime, movie.runningTime)
                && Objects.equals(specialCode, movie.specialCode);
    }
    
    /**
     * Hashes the movie object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(title, description, runningTime, ticketPrice, specialCode);
    }
}