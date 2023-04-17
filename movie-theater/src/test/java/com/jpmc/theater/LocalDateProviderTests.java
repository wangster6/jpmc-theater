package com.jpmc.theater;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LocalDateProviderTests {
	LocalDateProvider provider;
	
	@BeforeEach
	public void setUp() {
		provider = LocalDateProvider.getInstance();
	}
	
	@Test
	public void testGetInstance() {
		// Test to make sure getInstance method does not throw any exceptions.
		assertDoesNotThrow(() -> LocalDateProvider.getInstance());
		
		// Create two test instances and set them both to the returned instance from getInstance.
		LocalDateProvider instance1 = LocalDateProvider.getInstance();
		LocalDateProvider instance2 = LocalDateProvider.getInstance();
		
		// Check to make sure neither of the test instances are null and check to make sure they are equal to each other.
		assertNotNull(instance1);
		assertNotNull(instance2);
		assertEquals(instance1, instance2);
		// Since the LocalDateProvider class follows a singleton pattern, the returned value of getInstance should always
		// be the same singleton instance. Thus, testing the class this way helps us confirm that by calling getInstance multiple
		// times and asserting that they are equal and not null.
	}
	
    @Test
    void makeSureCurrentTime() {
    	// Test to make sure the currentDate method does not throw any exceptions.
    	assertDoesNotThrow(() -> provider.currentDate());
    	
    	// Create test date and initialize it to the returned value from currentDate method.
    	LocalDate date = provider.currentDate();
    	
    	// Check to make sure test date is not null and that it is equal to the current local date.
    	assertNotNull(date);
    	assertEquals(LocalDate.now(), date);
    }
    
    @Test
    public void testExtras() {
    	// Extra test to check if returned value of directly accessing LocalDateProvider's singleton instance and calling
    	// currentDate method works correctly and returns the right value.
    	assertEquals(LocalDate.now(), LocalDateProvider.getInstance().currentDate());
    	
//      System.out.println("current time is - " + LocalDateProvider.getInstance().currentDate());
    }
}
