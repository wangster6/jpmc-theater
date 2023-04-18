package com.jpmc.theater;

import java.util.Objects;

/**
 * This class represents the Customer object which includes a name and id field.
 * 
 * @author Ray Wang
 */
public class Customer {
	/** Name of the customer */
    private String name;
    /** Id of the customer */
    private String id;

    /**
     * Constructs the customer object
     * @param name customer name
     * @param id customer id
     */
    public Customer(String name, String id) {
        this.id = id; // NOTE - id is not used anywhere at the moment

        this.name = name;

        }

    /**
     * Checks if customer objects are equal or not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(name, customer.name) && Objects.equals(id, customer.id);
    }
    
    /**
     * Hashes the customer object
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }

    /**
     * Formats the name of the customer into a string.
     */
    @Override
    public String toString() {
        return "name: " + name;
    }
}