package persistence;

import model.Activity;
import model.ActivityCollection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Unit test class for testing functionalities of the JsonWriter class

class JsonWriterTest extends JsonTest {

    private Activity testActivity1;
    private Activity testActivity2;
    private ActivityCollection testActivities;

    @BeforeEach
    void runbefore() {
        testActivities = new ActivityCollection();
        testActivity1 = new Activity("Pottery", "Indoor", "Vancouver");
        testActivity2 = new Activity("Brunch", "Food", "Burnaby");
        testActivity2.setDateTime("2024-02-17 12:00");
        testActivity2.markAsScheduled();
    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterActivityCollection() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyActivityCollection.json");
            writer.open();
            writer.write(testActivities);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyActivityCollection.json");
            testActivities = reader.read();
            assertEquals(0, testActivities.getActivities().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralActivityCollection() {
        try {
            testActivities.addActivity(testActivity1);
            testActivities.addActivity(testActivity2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralActivityCollection.json");
            writer.open();
            writer.write(testActivities);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralActivityCollection.json");
            testActivities = reader.read();
            List<Activity> activities = testActivities.getActivities();
            assertEquals(2, activities.size());
            checkActivity("Pottery", "Indoor", "Vancouver", false, "", testActivity1);
            checkActivity("Brunch", "Food", "Burnaby", true, "2024-02-17 12:00", testActivity2);
            assertNull(testActivity1.getDateTime());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
