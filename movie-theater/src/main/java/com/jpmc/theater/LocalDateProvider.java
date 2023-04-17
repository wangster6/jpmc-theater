package com.jpmc.theater;

import java.time.LocalDate;

/**
 * Provides the local date. Will be used to display date in the final output.
 * 
 * @author Ray Wang
 */
public class LocalDateProvider {
	/** Current instance of LocalDateProvider. Used to maintain singleton instance */
    private static final LocalDateProvider INSTANCE = new LocalDateProvider();

    /** Private constructor to prevent other classes from creating instances of LocalDateProvider as it uses the singleton pattern */
    private LocalDateProvider() { }
    
    /**
     * Returns the singleton instance of the class.
     * @return the singleton instance
     */
    public static LocalDateProvider getInstance() {
            return INSTANCE;
    }
    
    /**
     * Returns the current date
     * @return the current date
     */
    public LocalDate currentDate() {
            return LocalDate.now();
    }
}
