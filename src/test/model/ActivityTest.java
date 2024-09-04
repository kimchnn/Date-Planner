package model;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DateTimeException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Unit test class for testing functionalities of the Activity class

public class ActivityTest {
    private Activity testActivity;

    @BeforeEach
    void runBefore() {
        testActivity = new Activity("Hiking", "Outdoor", "Vancouver");
    }

    @Test
    void testConstructor() {
        assertEquals("Hiking", testActivity.getName());
        assertEquals("Outdoor", testActivity.getCategory());
        assertEquals("Vancouver", testActivity.getLocation());
    }

    @Test
    void testSetDateTimeNoExceptionThrown() {
        testActivity.setDateTime("2024-02-17 12:00");
        assertEquals(LocalDateTime.of(2024, 2, 17, 12, 00), testActivity.getDateTime());
    }

    @Test
    void testSetDateTimeExceptionThrown() {
        try {
            testActivity.setDateTime("2024-2-17 12:00");
            fail("DateTimeException expected");
        } catch (DateTimeException e) {
            // expected
        }
        assertNull(testActivity.getDateTime());
    }

    @Test
    void testMarkAsScheduled() {
        testActivity.markAsScheduled();
        assertTrue(testActivity.getScheduleStatus());
    }

    @Test
    void testMarkAsScheduledMultiple() {
        testActivity.markAsScheduled();
        testActivity.markAsScheduled();
        assertTrue(testActivity.getScheduleStatus());
    }

    @Test
    void testMarkAsNotScheduled() {
        testActivity.markAsNotScheduled();
        assertFalse(testActivity.getScheduleStatus());
    }

    @Test
    void testMarkAsNotScheduledMultiple() {
        testActivity.markAsNotScheduled();
        testActivity.markAsNotScheduled();
        assertFalse(testActivity.getScheduleStatus());
    }

    @Test
    void testMarkAsScheduledInverse() {
        assertFalse(testActivity.getScheduleStatus());
        testActivity.markAsScheduled();
        testActivity.markAsNotScheduled();
        assertFalse(testActivity.getScheduleStatus());
        testActivity.markAsScheduled();
        assertTrue(testActivity.getScheduleStatus());
    }

}
