package com.jpmc.theater;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LocalDateProviderTests {
	/** The local date provider used for testing */
	LocalDateProvider provider;
	
	/**
	 * Initializes provider to singleton instance of LocalDateProvider from the getInstance method before each test.
	 */
	@BeforeEach
	public void setUp() {
		provider = LocalDateProvider.getInstance();
	}
	
	/**
	 * Testing the getInstance method of the LocalDateProvider
	 */
	@Test
	public void testGetInstance() {
		// Test to make sure getInstance method does not throw any exceptions.
		assertDoesNotThrow(() -> LocalDateProvider.getInstance());
		
		LocalDateProvider instance1 = LocalDateProvider.getInstance();
		LocalDateProvider instance2 = LocalDateProvider.getInstance();
		
		assertNotNull(instance1);
		assertNotNull(instance2);
		assertEquals(instance1, instance2);
		// Since the LocalDateProvider class follows a singleton pattern, the returned value of getInstance should always
		// be the same singleton instance. Thus, testing the class this way helps us confirm that by calling getInstance multiple
		// times and asserting that they are equal and not null.
	}
	
	/**
	 * Testing the currentDate method of the LocalDateProvider.
	 */
    @Test
    void testCurrentDate() {
    	assertDoesNotThrow(() -> provider.currentDate());
    	
    	LocalDate date = provider.currentDate();
    	
    	// Check to make sure test date is not null and that it is equal to the current local date.
    	assertNotNull(date);
    	assertEquals(LocalDate.now(), date);
    }
    
    /**
     * Extra tests for the LocalDateProvider.
     */
    @Test
    public void testExtras() {
    	// Extra test to check if returned value of directly accessing LocalDateProvider's singleton instance and calling
    	// currentDate method works correctly and returns the right value.
    	assertEquals(LocalDate.now(), LocalDateProvider.getInstance().currentDate());
    	
//      System.out.println("current time is - " + LocalDateProvider.getInstance().currentDate());
    }
}
