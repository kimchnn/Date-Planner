package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// Referenced code from JsonSerializationDemo

// Represents a list of activities to do

public class ActivityCollection implements Writable {
    private List<Activity> activities;

    // EFFECTS: constructs a new empty list of activities for ActivityCollection
    public ActivityCollection() {
        this.activities = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds specified activity to the list of activities if there are no
    // activities with the same name in the ActivityCollection
    // Return true if activity added successfully; false otherwise
    public boolean addActivity(Activity activity) {
        if (!hasDuplicateName(activity)) {
            activities.add(activity);
            EventLog.getInstance()
                    .logEvent(new Event("New Activity (" + activity.getName() + ") added to the activity collection."));
            return true;

        } else {
            return false;
        }
    }

    // REQUIRES: activities is not an empty list
    // MODIFIES: this
    // EFFECTS: removes specified activity from the list of activities
    public void removeActivity(Activity activity) {
        activities.remove(activity);
        EventLog.getInstance().logEvent(new Event(activity.getName() + " was removed from the activity collection."));
    }

    // EFFECTS: returns the list of activities in the collection
    public List<Activity> getActivities() {
        EventLog.getInstance().logEvent(new Event("Viewed all activities in the activity collection."));
        return activities;
    }

    // EFFECTS: returns a list of activities that match the specified name
    public ActivityCollection filterByName(String name) {
        ActivityCollection filteredByName = new ActivityCollection();
        for (Activity activity : this.activities) {
            if (activity.getName().equals(name)) {
                filteredByName.addActivity(activity);
            }
        }
        return filteredByName;
    }

    // EFFECTS: returns a list of activities that match the specified category
    public ActivityCollection filterByCategory(String category) {
        ActivityCollection filteredByCategory = new ActivityCollection();
        for (Activity activity : this.activities) {
            if (activity.getCategory().equals(category)) {
                filteredByCategory.addActivity(activity);
            }
        }
        return filteredByCategory;
    }

    // EFFECTS: returns a list of activities that match the specified location
    public ActivityCollection filterByLocation(String location) {
        ActivityCollection filteredByLocation = new ActivityCollection();
        for (Activity activity : this.activities) {
            if (activity.getLocation().equals(location)) {
                filteredByLocation.addActivity(activity);
            }
        }
        return filteredByLocation;
    }

    // EFFECTS: returns true if there is a duplicate name between newActivity and
    // activities
    public boolean hasDuplicateName(Activity newActivity) {
        for (Activity activity : activities) {
            if (activity.getName().equals(newActivity.getName())) {
                EventLog.getInstance()
                        .logEvent(new Event(activity.getName() + " was not added due to duplicate name error."));
                return true;
            }
        }
        return false;
    }

    // EFFECTS: returns a JSON object of ActivityCollection
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("activities", activitiesToJson());
        return json;
    }

    // EFFECTS: returns activities in this activityCollection as a JSON array
    private JSONArray activitiesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Activity activity : activities) {
            jsonArray.put(activity.toJson());
        }

        return jsonArray;
    }

}
