package com.jpmc.theater;

import java.time.Duration;
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
     * Constructs the Movie object with the provided parameters
     * @param title of the movie
     * @param runningTime of the movie
     * @param ticketPrice of the movie
     * @param specialCode to check for discounts
     */
    public Movie(String title, Duration runningTime, double ticketPrice, int specialCode) {
        this.title = title;
        this.runningTime = runningTime;
        this.ticketPrice = ticketPrice;
        this.specialCode = specialCode;
    }
    
    /**
     * Returns the title of the movie
     * @return the title of the movie
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the running time of the movie
     * @return the running time of the movie
     */
    public Duration getRunningTime() {
        return runningTime;
    }

    /**
     * Returns the ticket price of the movie
     * @return the ticket price of the movie
     */
    public double getTicketPrice() {
        return ticketPrice;
    }

    /**
     * Calculates the ticket price of the movie after applying the discount.
     * @param showing the movie showing of the day. Used to calculate sequence of the day.
     * @return Final ticket price after discount.
     */
    public double calculateTicketPrice(Showing showing) {
        return ticketPrice - getDiscount(showing.getSequenceOfTheDay());
    }

    /**
     * Returns the total discount for the showing in dollar value.
     * @param showSequence displays number of times the movie has been shown for the day
     * @return the total discount for the showing in dollar value
     */
    private double getDiscount(int showSequence) {
        double specialDiscount = 0;
        if (MOVIE_CODE_SPECIAL == specialCode) {
            specialDiscount = ticketPrice * 0.2;  // 20% discount for special movie
        }

        double sequenceDiscount = 0;
        if (showSequence == 1) {
            sequenceDiscount = 3; // $3 discount for 1st show
        } else if (showSequence == 2) {
            sequenceDiscount = 2; // $2 discount for 2nd show
        }
//        else {
//            throw new IllegalArgumentException("failed exception");
//        }

        // biggest discount wins
        return specialDiscount > sequenceDiscount ? specialDiscount : sequenceDiscount;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(title, description, runningTime, ticketPrice, specialCode);
    }
}