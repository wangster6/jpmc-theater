package com.jpmc.theater;

import java.time.LocalDateTime;

public class Showing {
    private Movie movie;
    private int sequenceOfTheDay;
    private LocalDateTime showStartTime;

    public Showing(Movie movie, int sequenceOfTheDay, LocalDateTime showStartTime) {
        this.movie = movie;
        this.sequenceOfTheDay = sequenceOfTheDay;
        this.showStartTime = showStartTime;
    }

    public Movie getMovie() {
        return movie;
    }
    
    /**
     * Returns the start time of the movie showing
     * @return the start time of the movie showing
     */
    public LocalDateTime getStartTime() {
        return showStartTime;
    }
    
    /**
     * Formats the start time of the movie showing into HH:MM form and returns it as a String
     * @param startTime the start time of the movie showing to format
     * @return the start time of the movie showing in HH:MM form
     */
    public String formatStartTime(LocalDateTime startTime) {
    	// Start time to return in String form
    	String rtn;
    	
    	// Checks if hour of start time is a single digit number. If so, concat a 0 before the time and add to rtn String.
    	// This is to fix the alignment of the start times of the show to maintain HH:MM format.
    	if(startTime.getHour() >= 0 && startTime.getHour() <= 9) {
    		rtn = "0" + startTime.getHour();
    	} else {
    		rtn = String.valueOf(startTime.getHour());
    	}
    	
    	// Checks if minute of start time is 0. If so, concat a 0 and add to rtn String. This is to maintain HH:MM format.
    	if(startTime.getMinute() == 0) {
    		rtn = rtn + ":00";
    	} else {
    		rtn = rtn + ":" + startTime.getMinute();
    	}
        return rtn;
    }

    public boolean isSequence(int sequence) {
        return this.sequenceOfTheDay == sequence;
    }

    public int getSequenceOfTheDay() {
        return sequenceOfTheDay;
    }


}
