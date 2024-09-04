package persistence;

import model.Activity;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Provides a helper method to verify the attributes of the Activity objects in unit tests

public class JsonTest {
    protected void checkActivity(String name, String category, String location, boolean scheduleStatus,
            String dateTimeString, Activity activity) {
        assertEquals(name, activity.getName());
        assertEquals(category, activity.getCategory());
        assertEquals(location, activity.getLocation());
        assertEquals(scheduleStatus, activity.getScheduleStatus());
        assertEquals(dateTimeString, activity.getDateTimeString());
    }
}
