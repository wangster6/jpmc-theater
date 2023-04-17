package com.jpmc.theater;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    public Reservation reserve(Customer customer, int sequence, int howManyTickets) {
        Showing showing = null;
        try {
            showing = schedule.get(sequence - 1);
        } catch (IndexOutOfBoundsException ex) {
            throw new IllegalArgumentException("The sequence you selected is out of bounds: " + sequence + ". Please select a valid sequence from the schedule.");
        } catch (Exception other) {
        	other.printStackTrace();
        	throw new IllegalArgumentException();
        }
        
        Reservation toAdd = new Reservation(customer, showing, howManyTickets);
        reservations.add(toAdd);
        return toAdd;
    }

    public void printSchedule() {
        System.out.println("Showtimes for " + provider.currentDate());
        System.out.println("===================================================");
        schedule.forEach(s ->
                System.out.println(s.getSequenceOfTheDay() + ": " + s.formatStartTime(s.getStartTime()) + " " + s.getMovie().getTitle() + " " + humanReadableFormat(s.getMovie().getRunningTime()) + " $" + s.getMovieFee())
        );
        System.out.println("===================================================");
    }

    private String humanReadableFormat(Duration duration) {
        long hour = duration.toHours();
        long remainingMin = duration.toMinutes() - TimeUnit.HOURS.toMinutes(duration.toHours());

        return String.format("(%s hour%s %s minute%s)", hour, handlePlural(hour), remainingMin, handlePlural(remainingMin));
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
