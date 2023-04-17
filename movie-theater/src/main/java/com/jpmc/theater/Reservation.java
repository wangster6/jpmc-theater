package com.jpmc.theater;

/**
 * This class represents the Reservation object. It contains fields for the customer of the reservation, the movie
 * showing, and the audience count.
 * 
 * @author Ray Wang
 */
public class Reservation {
	/** The customer making the reservation */
    private Customer customer;
    /** The movie showing in the reservation */
    private Showing showing;
    /** The number of people the reservation is for */
    private int audienceCount;

    /**
     * Constructs the Reservation based on the given customer, showing, and audience count.
     * @param customer making the reservation
     * @param showing in the reservation
     * @param audienceCount of the reservation
     */
    public Reservation(Customer customer, Showing showing, int audienceCount) {
        this.customer = customer;
        this.showing = showing;
        this.audienceCount = audienceCount;
    }
    
    /**
     * Calculates and returns the total cost of the reservation. This total fee factors in the discounts and the audience count.
     * @return the total cost of the reservation
     */
    public double totalFee() {
        return showing.getMovie().calculateTicketPrice(showing) * this.audienceCount;
    }
    
    /**
     * Returns the customer who made the reservation
     * @return the customer
     */
    public Customer getCustomer() {
    	return this.customer;
    }

	/**
	 * Return the showing of the reservation
	 * @return the showing
	 */
	public Showing getShowing() {
		return showing;
	}

	/**
	 * Return the audience count of the reservation
	 * @return the audienceCount
	 */
	public int getAudienceCount() {
		return audienceCount;
	}
    
    
}