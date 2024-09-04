package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Event class
 */
public class EventTest {
    private Event event;
    private Date date;

    // NOTE: these tests might fail if time at which line (2) below is executed
    // is different from time that line (1) is executed. Lines (1) and (2) must
    // run in same millisecond for this test to make sense and pass.

    @BeforeEach
    public void runBefore() {
        event = new Event("Added Activity to activity collection"); // (1)
        date = Calendar.getInstance().getTime(); // (2)
    }

    @Test
    public void testEvent() {
        assertEquals("Added Activity to activity collection", event.getDescription());
        assertEquals(date, event.getDate());
    }

    @Test
    public void testToString() {
        assertEquals(date.toString() + "\n" + "Added Activity to activity collection", event.toString());
    }

    @Test
    public void testEqualsNull() {
        assertFalse(event.equals(null));
    }

    @Test
    public void testEqualsOtherClass() {
        assertFalse(event.equals(0));
    }

    @Test
    public void testEqualsFalse() {
        assertFalse(event.equals(new Event("")));
    }

    @Test
    public void testEqualsTrue() {
        assertTrue(event.equals(event));
    }

    @Test
    public void testHashCode() {
        assertEquals(13 * event.getDate().hashCode() + event.getDescription().hashCode(), event.hashCode());
    }
}
