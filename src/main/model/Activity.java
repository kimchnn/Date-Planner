package model;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;

import persistence.Writable;

// Referenced code from JsonSerializationDemo

// Represents an activity having a name, activity type, location (city),
// scheduled time and schedule status

public class Activity implements Writable {
    private String name;
    private String category;
    private String location;
    private String dateTimeString;
    private LocalDateTime dateTime;
    private boolean scheduleStatus;

    // EFFECTS: constructs an unscheduled activity with the specified name, category
    // and location;
    public Activity(String name, String category, String location) {
        this.name = name;
        this.category = category;
        this.location = location;
        this.dateTimeString = "";
        this.dateTime = null;
        this.scheduleStatus = false;
    }

    // EFFECTS: returns the name of the activity
    public String getName() {
        return name;
    }

    // EFFECTS: returns the category of the activity
    public String getCategory() {
        return category;
    }

    // EFFECTS: returns the location of the activity
    public String getLocation() {
        return location;
    }

    // MODIFIES: this
    // EFFECTS: set date and time to schedule an activity to the specified integer
    // values
    public void setDateTime(String dateTimeString) throws DateTimeException {
        this.dateTimeString = dateTimeString;
        if (dateTimeString.isEmpty()) {
            this.dateTime = null;
        } else {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                this.dateTime = LocalDateTime.parse(dateTimeString, formatter);
            } catch (DateTimeException e) {
                throw new DateTimeException("Invalid date and time format. Please use the format yyyy-MM-dd HH:mm.");
            }
        }
    }

    // EFFECTS: returns the dateTimeString of the activity, returns ""null""
    // if activity is not scheduled
    public String getDateTimeString() {
        return dateTimeString;
    }

    // EFFECTS: returns the date and time of the activity, returns null if
    // activity is not scheduled
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    // MODIFIES: this
    // EFFECTS: marks the activity as scheduled
    public void markAsScheduled() {
        this.scheduleStatus = true;
        EventLog.getInstance()
                .logEvent(new Event(getName() + " was scheduled."));
    }

    // MODIFIES: this
    // EFFECTS: marks the activity as not scheduled
    public void markAsNotScheduled() {
        this.scheduleStatus = false;
    }

    // EFFECTS: returns the schedule status of the activity
    public boolean getScheduleStatus() {
        return this.scheduleStatus;
    }

    // EFFECTS: returns a JSON object of the activity
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("category", category);
        json.put("location", location);
        json.put("dateTimeString", dateTimeString);
        json.put("scheduleStatus", scheduleStatus);
        return json;
    }

}
