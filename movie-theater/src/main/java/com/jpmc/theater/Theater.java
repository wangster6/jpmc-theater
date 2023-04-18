package com.jpmc.theater;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class Theater {
    LocalDateProvider provider;
    private List<Showing> schedule;
    private List<Reservation> reservations;

    public Theater() {
        this.provider = LocalDateProvider.getInstance();
        this.schedule = new ArrayList<Showing>();
        this.reservations = new ArrayList<Reservation>();
    }
    
    private Showing createShowing(Movie movie, int sequenceOfTheDay, LocalDateTime showStartTime) {
    	return new Showing(movie, sequenceOfTheDay, showStartTime);
    }
    
    public Showing addShowingToSchedule(Movie movie, int sequenceOfTheDay, LocalDateTime showStartTime) {
    	Showing toAdd = createShowing(movie, sequenceOfTheDay, showStartTime);
    	schedule.add(toAdd);
    	return toAdd;
    }
    
    public Showing addShowingToSchedule(Showing showing) {
    	schedule.add(showing);
    	return showing;
    }
    
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
    
    public void clearSchedule() {
    	this.schedule = new ArrayList<Showing>();
    }

    public Reservation reserve(Customer customer, int sequence, int ticketCount) {
        Showing showing = null;

    	for(int i = 0; i < schedule.size(); i++) {
    		if(schedule.get(i).isSequence(sequence)) {
    			showing = schedule.get(i);
    		}
    	}
        
    	if(showing == null) {
    		throw new IllegalArgumentException("Invalid sequence. There are no showings with the sequence: " + String.valueOf(sequence));
    	}
    	
        Reservation toAdd = new Reservation(customer, showing, ticketCount);
        reservations.add(toAdd);
        return toAdd;
    }
    
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

    private String humanReadableFormat(Duration duration) {
        long hour = duration.toHours();
        long remainingMin = duration.toMinutes() - TimeUnit.HOURS.toMinutes(duration.toHours());

        return String.format("%s hr%s %s min%s", hour, handlePlural(hour), remainingMin, handlePlural(remainingMin));
    }

    // (s) postfix should be added to handle plural correctly
    private String handlePlural(long value) {
        if (value == 1) {
            return "";
        }
        else {
            return "s";
        }
    }
}
