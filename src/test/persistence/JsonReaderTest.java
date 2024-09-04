package persistence;

import model.Activity;
import model.ActivityCollection;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

// Unit test class for testing functionalities of the JsonReader class

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyActivityCollection() {
        JsonReader reader = new JsonReader("./data/testWriterEmptyActivityCollection.json");
        try {
            ActivityCollection ac = reader.read();
            assertEquals(0, ac.getActivities().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralActivityCollection() {
        JsonReader reader = new JsonReader("./data/testWriterGeneralActivityCollection.json");
        try {
            ActivityCollection ac = reader.read();
            List<Activity> activities = ac.getActivities();
            assertEquals(2, activities.size());
            checkActivity("Pottery", "Indoor", "Vancouver", false, "", activities.get(0));
            checkActivity("Brunch", "Food", "Burnaby", true, "2024-02-17 12:00", activities.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
