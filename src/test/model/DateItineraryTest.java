package model;

import static org.junit.Assert.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Unit test class for testing functionalities of the DateItinerary class

public class DateItineraryTest {
    private DateItinerary testItinerary;
    private Activity testActivity1;
    private Activity testActivity2;
    private Activity testActivity3;

    @BeforeEach
    void runBefore() {
        testItinerary = new DateItinerary();
        testActivity1 = new Activity("Hiking", "Outdoor", "Vancouver");
        testActivity2 = new Activity("Movies", "Indoor", "Richmond");
        testActivity3 = new Activity("McDonalds", "Food & Drinks", "Surrey");
        new Activity("Art Gallery", "Indoor", "Vancouver");
    }

    @Test
    void testConstructor() {
        assertTrue(testItinerary.getScheduledActivities().isEmpty());
        assertNull(testItinerary.getDate());
    }

    @Test
    void testAddActivity() {
        testItinerary.addActivity(testActivity1);
        assertEquals(1, testItinerary.getScheduledActivities().size());
        assertEquals(testActivity1, testItinerary.getScheduledActivities().get(0));
    }

    @Test
    void testAddActivityMultiple() {
        testItinerary.addActivity(testActivity1);
        testItinerary.addActivity(testActivity2);
        assertEquals(2, testItinerary.getScheduledActivities().size());
        assertEquals(testActivity1, testItinerary.getScheduledActivities().get(0));
        assertEquals(testActivity2, testItinerary.getScheduledActivities().get(1));
    }

    @Test
    void testRemoveActivity() {
        testItinerary.addActivity(testActivity1);
        testItinerary.addActivity(testActivity2);
        testItinerary.removeActivity(testActivity1);
        assertEquals(1, testItinerary.getScheduledActivities().size());
        assertEquals(testActivity2, testItinerary.getScheduledActivities().get(0));
    }

    @Test
    void testRemoveActivityMultiple() {
        testItinerary.addActivity(testActivity1);
        testItinerary.addActivity(testActivity2);
        testItinerary.removeActivity(testActivity1);
        testItinerary.removeActivity(testActivity2);
        assertEquals(0, testItinerary.getScheduledActivities().size());
    }

    @Test
    void testSetDate() {
        testItinerary.setDate(2024, 2, 14);
        assertEquals(LocalDate.of(2024, 2, 14), testItinerary.getDate());
    }

    @Test
    void testHasTimeConflictNoneAdded() {
        assertFalse(testItinerary.hasTimeConflict(testActivity1));
    }

    @Test
    void testHasTimeConflictSingle() {
        testItinerary.addActivity(testActivity1);
        testActivity1.setDateTime("2024-02-14 12:00");
        assertFalse(testItinerary.hasTimeConflict(testActivity2));
    }

    @Test
    void testHasTimeConflictMultiple() {
        testItinerary.addActivity(testActivity1);
        testItinerary.addActivity(testActivity2);
        testItinerary.addActivity(testActivity3);
        testActivity1.setDateTime("2024-02-14 12:00");
        testActivity2.setDateTime("2024-01-14 12:00");
        testActivity3.setDateTime("2024-02-14 12:00");
        assertTrue(testItinerary.hasTimeConflict(testActivity3));
    }

    @Test
    void testGetActivitiesForDate() {
        testItinerary.addActivity(testActivity1);
        testItinerary.addActivity(testActivity2);
        testItinerary.addActivity(testActivity3);
        testActivity1.setDateTime("2024-02-14 12:00");
        testActivity2.setDateTime("2024-01-14 12:00");
        testActivity3.setDateTime("2024-02-14 14:00");
        DateItinerary activitiesOnDate = testItinerary.getActivitiesForDate(LocalDate.of(2024, 2, 14));
        assertEquals(2, activitiesOnDate.getScheduledActivities().size());
        assertTrue(activitiesOnDate.getScheduledActivities().contains(testActivity1));
        assertTrue(activitiesOnDate.getScheduledActivities().contains(testActivity3));
    }

    @Test
    void testGetActivitiesForDateNoneScheduled() {
        DateItinerary activitiesOnDate = testItinerary.getActivitiesForDate(LocalDate.of(2024, 2, 14));
        assertTrue(activitiesOnDate.getScheduledActivities().isEmpty());
    }

    @Test
    void testGetActivitiesForDateWrongTime() {
        testItinerary.addActivity(testActivity1);
        testActivity1.setDateTime("2024-02-14 12:00");
        DateItinerary activitiesOnDate = testItinerary.getActivitiesForDate(LocalDate.of(2024, 2, 16));
        assertTrue(activitiesOnDate.getScheduledActivities().isEmpty());
    }

    @Test
    void testScheduleActivityTimeConflict() {
        try {
            testItinerary.addActivity(testActivity1);
            testItinerary.addActivity(testActivity2);
            assertNull(testActivity1.getDateTime());
            assertNull(testActivity2.getDateTime());
            testItinerary.scheduleActivity(testActivity1, "2024-02-14 12:00");
            testItinerary.scheduleActivity(testActivity2, "2024-02-14 12:00");
            assertEquals("2024-02-14 12:00", testActivity1.getDateTimeString());
            assertFalse(testActivity2.getScheduleStatus());
        } catch (DateTimeException e) {
            fail("DateTimeException not expected");
        }
    }

    @Test
    void testScheduleActivityNoTimeConflict() {
        try {
            assertNull(testActivity1.getDateTime());
            assertEquals(0, testItinerary.getScheduledActivities().size());
            testItinerary.scheduleActivity(testActivity1, "2024-02-14 12:00");
            assertEquals("2024-02-14 12:00", testActivity1.getDateTimeString());
            assertEquals(1, testItinerary.getScheduledActivities().size());
        } catch (DateTimeException e) {
            fail("DateTimeException not expected");
        }
    }

    @Test
    void testScheduleActivityThrowDateTimeException() {
        try {
            testItinerary.addActivity(testActivity1);
            assertNull(testActivity1.getDateTime());
            testItinerary.scheduleActivity(testActivity1, "24-02 12:00");
        } catch (DateTimeException e) {
           // expected
        }
    }

}