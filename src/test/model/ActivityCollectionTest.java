package model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Unit test class for testing functionalities of the ActivityCollection class

public class ActivityCollectionTest {
    private ActivityCollection testCollection;
    private Activity testActivity1;
    private Activity testActivity2;
    private Activity testActivity3;
    private Activity testActivity4;
    private Activity testActivity5;

    @BeforeEach
    void runBefore() {
        testCollection = new ActivityCollection();
        testActivity1 = new Activity("Hiking", "Outdoor", "Vancouver");
        testActivity2 = new Activity("Movies", "Indoor", "Richmond");
        testActivity3 = new Activity("McDonalds", "Food & Drinks", "Surrey");
        testActivity4 = new Activity("Art Gallery", "Indoor", "Vancouver");
        testActivity5 = new Activity("Hiking", "Outdoor", "North Vancouver");
    }

    @Test
    void testConstructor() {
        assertTrue(testCollection.getActivities().isEmpty());
    }

    @Test
    void testAddActivity() {
        testCollection.addActivity(testActivity1);
        assertEquals(1, testCollection.getActivities().size());
        assertEquals(testActivity1, testCollection.getActivities().get(0));
    }

    @Test
    void testAddActivityMultiple() {
        testCollection.addActivity(testActivity1);
        testCollection.addActivity(testActivity2);
        assertEquals(2, testCollection.getActivities().size());
        assertEquals(testActivity1, testCollection.getActivities().get(0));
        assertEquals(testActivity2, testCollection.getActivities().get(1));
    }

    @Test
    void testRemoveActivity() {
        testCollection.addActivity(testActivity1);
        testCollection.addActivity(testActivity2);
        testCollection.removeActivity(testActivity1);
        assertEquals(1, testCollection.getActivities().size());
        assertEquals(testActivity2, testCollection.getActivities().get(0));
    }

    @Test
    void testRemoveActivityMultiple() {
        testCollection.addActivity(testActivity1);
        testCollection.addActivity(testActivity2);
        testCollection.removeActivity(testActivity1);
        testCollection.removeActivity(testActivity2);
        assertEquals(0, testCollection.getActivities().size());
    }

    @Test
    void testFilterActivitiesByName() {
        testCollection.addActivity(testActivity1);
        testCollection.addActivity(testActivity2);
        testCollection.addActivity(testActivity3);
        testCollection.addActivity(testActivity4);
        ActivityCollection filteredList = testCollection.filterByName("Movies");
        assertEquals(1, filteredList.getActivities().size());
        assertEquals(testActivity2, filteredList.getActivities().get(0));

    }

    @Test
    void testFilterActivitiesByCategory() {
        testCollection.addActivity(testActivity1);
        testCollection.addActivity(testActivity2);
        testCollection.addActivity(testActivity3);
        testCollection.addActivity(testActivity4);
        ActivityCollection filteredList = testCollection.filterByCategory("Indoor");
        assertEquals(2, filteredList.getActivities().size());
        assertEquals(testActivity2, filteredList.getActivities().get(0));
        assertEquals(testActivity4, filteredList.getActivities().get(1));
    }

    @Test
    void testFilterActivitiesByLocation() {
        testCollection.addActivity(testActivity1);
        testCollection.addActivity(testActivity2);
        testCollection.addActivity(testActivity3);
        testCollection.addActivity(testActivity4);
        ActivityCollection filteredList = testCollection.filterByLocation("Vancouver");
        assertEquals(2, filteredList.getActivities().size());
        assertEquals(testActivity1, filteredList.getActivities().get(0));
        assertEquals(testActivity4, filteredList.getActivities().get(1));
    }

    @Test
    void testDuplicateNameFalse() {
        testCollection.addActivity(testActivity2);
        assertFalse(testCollection.hasDuplicateName(testActivity1));
    }

    @Test
    void testDuplicateNameTrue() {
        testCollection.addActivity(testActivity1);
        testCollection.addActivity(testActivity5);
        assertTrue(testCollection.hasDuplicateName(testActivity5));
    }
}
