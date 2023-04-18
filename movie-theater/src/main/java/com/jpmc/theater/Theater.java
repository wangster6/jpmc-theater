package com.jpmc.theater;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * This class represents the Theater object. It has the schedule of movie showings and a list of reservations made. It also allows
 * the user to manage movie showings and reservations, as well as display the movie schedule to console.
 * 
 * @author Ray Wang
 */
public class Theater {
	/** Singleton instance of the local date provider */
    LocalDateProvider provider;
    /** List of movie showings for the day */
    private List<Showing> schedule;
    /** List of reservations made */
    private List<Reservation> reservations;
    
    /**
     * Constructs the theater object and initializes the provider, schedule, and reservation.
     */
    public Theater() {
        this.provider = LocalDateProvider.getInstance();
        this.schedule = new ArrayList<Showing>();
        this.reservations = new ArrayList<Reservation>();
    }
    
    /**
     * Creates a movie showing with the given parameters.
     * @param movie
     * @param sequenceOfTheDay
     * @param showStartTime
     * @return the newly created movie showing
     */
    private Showing createShowing(Movie movie, int sequenceOfTheDay, LocalDateTime showStartTime) {
    	return new Showing(movie, sequenceOfTheDay, showStartTime);
    }
    
    /**
     * Creates a movie showing with the given parameters and adds it to the schedule.
     * @param movie
     * @param sequenceOfTheDay
     * @param showStartTime
     * @return the movie showing that was added
     */
    public Showing addShowingToSchedule(Movie movie, int sequenceOfTheDay, LocalDateTime showStartTime) {
    	Showing toAdd = createShowing(movie, sequenceOfTheDay, showStartTime);
    	schedule.add(toAdd);
    	return toAdd;
    }
    
    /**
     * Adds a movie showing that is already created to the schedule.
     * @param showing
     * @return the movie showing that was added
     */
    public void addShowingToSchedule(Showing showing) {
    	schedule.add(showing);
    }
    
    /**
     * Removes a movie showing from the schedule. Returns the showing that was removed.
     * @param sequence
     * @return the showing that was removed
     */
    public Showing removeShowingFromSchedule(int sequence) {
    	Showing removed = null;
    	for(Showing s : schedule) {
    		if(s.getSequenceOfTheDay() == sequence) {
    			removed = s;
    			schedule.remove(s);
    		}
    	}
    	return removed;
    }
    
    /**
     * Clears the schedule by making it an empty new schedule.
     */
    public void clearSchedule() {
    	this.schedule = new ArrayList<Showing>();
    }
    
    /**
     * Creates a reservation with the given parameters and adds it to the reservations list.
     * @param customer
     * @param sequence
     * @param ticketCount
     * @return the reservation that was created.
     */
    public Reservation reserve(Customer customer, int sequence, int ticketCount) {
        Showing showing = null;
        
        // Find the showing in the schedule that has the given sequence and retrieves it.
    	for(int i = 0; i < schedule.size(); i++) {
    		if(schedule.get(i).isSequence(sequence)) {
    			showing = schedule.get(i);
    		}
    	}
        
    	// Check if the retrieved showing exists or not
    	if(showing == null) {
    		throw new IllegalArgumentException("Invalid sequence. There are no showings with the sequence: " + String.valueOf(sequence));
    	}
    	
    	// Create a reservation with the given customer and ticket count, and the retrieved showing.
        Reservation toAdd = new Reservation(customer, showing, ticketCount);
        reservations.add(toAdd);
        return toAdd;
    }
    
    /**
     * Removes a reservation from the reservations list.
     * @param customer
     * @param sequence
     * @param ticketCount
     * @return the removed reservation
     */
    public Reservation removeReservation(Customer customer, int sequence, int ticketCount) {
    	Reservation removed = null;
    	for(int i = 0; i < reservations.size(); i++) {
    		Reservation current = reservations.get(i);
    		if(current.getCustomer().equals(customer) && current.getShowing().getSequenceOfTheDay() == sequence && current.getAudienceCount() == ticketCount) {
    			removed = current;
    			reservations.remove(reservations.get(i));
    		}
    	}
    	return removed;
    }
    
    /**
     * Helper method used to find the length of the longest movie title. Used for aligning the columns of the printed schedule.
     * @return the number of characters in the longest movie title
     */
    private int longestMovieTitle() {
    	int longest = 0;
        for (Showing s : schedule) {
            int titleLength = s.getMovie().getTitle().length();
            if (titleLength > longest) {
            	longest = titleLength;
            }
        }
    	return longest;
    }
    
    /**
     * Prints the movie schedule in text format with headers.
     */
    public void printScheduleText() {
        System.out.println("Showtimes for " + provider.currentDate());
        System.out.println("==========================================================================");
        System.out.printf("%-10s %-12s %-"+(longestMovieTitle() + 3)+"s %-14s %-10s\n", "Sequence", "Start Time", "Movie Title", "Runtime", "Price");
        schedule.forEach(s ->
        		System.out.printf("%-10d %-12s %-"+(longestMovieTitle() + 3)+"s %-14s $%.2f\n",
        				s.getSequenceOfTheDay(),
        				s.formatStartTime(s.getStartTime()),
        				s.getMovie().getTitle(),
        				humanReadableFormat(s.getMovie().getRunningTime()),
        				s.getMovie().getTicketPrice())
        );
        System.out.println("==========================================================================");
    }
    
    /**
     * Prints the movie schedule in json format.
     */
    public void printScheduleJson() {
        System.out.println("Showtimes for " + provider.currentDate());
        System.out.println("===================================================");
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        schedule.forEach(s -> {
        		JsonObject scheduleObject = new JsonObject();
                scheduleObject.addProperty("sequence", s.getSequenceOfTheDay());
                scheduleObject.addProperty("startTime", s.formatStartTime(s.getStartTime()));
                scheduleObject.addProperty("title", s.getMovie().getTitle());
                scheduleObject.addProperty("runningTime", humanReadableFormat(s.getMovie().getRunningTime()));
                scheduleObject.addProperty("ticketPrice", s.getMovie().getTicketPrice());
                String json = gson.toJson(scheduleObject);
                System.out.println(json);
        });
        
        System.out.println("===================================================");
    }

    /**
     * Helper method to take the inputted duration and return a String that makes the duration length readable.
     * @param duration
     * @return the duration formatted into a string
     */
    private String humanReadableFormat(Duration duration) {
        long hour = duration.toHours();
        long remainingMin = duration.toMinutes() - TimeUnit.HOURS.toMinutes(duration.toHours());

        return String.format("%s hr%s %s min%s", hour, handlePlural(hour), remainingMin, handlePlural(remainingMin));
    }

    /**
     * Helper method to return an s character if the value is plural.
     * @param value
     * @return empty string if singular value, "s" if plural
     */
    private String handlePlural(long value) {
        if (value == 1) {
            return "";
        }
        else {
            return "s";
        }
    }
    
    // I did not think having a main method in this Theater class was right. I am assuming there will be a separate GUI or UI class that
    // will have the main function which is why I have main commented out. I have also added the methods to print the movie schedule in
    // plain text or json format to the TheaterTests.
//    public static void main(String args[]) {
//    	Theater theater = new Theater();
//    	theater.printScheduleText();
//    	theater.printScheduleJson();
//    }
}
