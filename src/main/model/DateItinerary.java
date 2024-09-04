package model;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Represents a list of activities scheduled for a date 

public class DateItinerary {
    private List<Activity> scheduledActivities;
    private LocalDate date;

    // EFFECTS: constructs a new empty list of scheduled activities for
    // DateItinerary
    public DateItinerary() {
        this.scheduledActivities = new ArrayList<>();
        this.date = null;
    }

    // MODIFIES: this
    // EFFECTS: adds specified activity to the date itinerary
    public void addActivity(Activity activity) {
        scheduledActivities.add(activity);

    }

    // REQUIRES: scheduledActivities is not empty
    // MODIFIES: this
    // EFFECTS: removes specified activity from the date itinerary
    public void removeActivity(Activity activity) {
        scheduledActivities.remove(activity);
    }

    // EFFECTS: returns the list of all scheduled activities
    public List<Activity> getScheduledActivities() {
        return scheduledActivities;
    }

    // MODIFIES: this
    // EFFECTS: set date of DateItinerary
    public void setDate(int year, int month, int day) {
        this.date = LocalDate.of(year, month, day);
    }

    // EFFECTS: returns date of the DateItinerary
    public LocalDate getDate() {
        return date;
    }

    // EFFECTS: returns true if there is a time conflict between activity
    // and scheduledactivity, otherwise returns false
    public boolean hasTimeConflict(Activity newActivity) {
        for (Activity scheduledActivity : scheduledActivities) {
            if (scheduledActivity.getDateTime().equals(newActivity.getDateTime())) {
                EventLog.getInstance()
                        .logEvent(new Event(newActivity.getName() + " was not scheduled due to time conflict error."));
                return true;
            }
        }
        return false;
    }

    // EFFECTS: returns a list of activities that are scheduled for the specified
    // date
    public DateItinerary getActivitiesForDate(LocalDate date) {
        DateItinerary activitiesForDate = new DateItinerary();
        for (Activity activity : this.scheduledActivities) {
            if (activity.getDateTime().toLocalDate().equals(date)) {
                activitiesForDate.addActivity(activity);
            }
        }
        return activitiesForDate;
    }

    // REQUIRES: dateTimeInput must be a valid LocalDateTime object representing a
    // valid date and time
    // MODIFIES: this, selectedActivity, scheduledActivities
    // EFFECTS: sets a date and time for the selected activity if no time conflict
    // exists in
    // scheduledActivities, otherwise, prompts users to choose another time
    public boolean scheduleActivity(Activity selectedActivity, String dateTimeInput) {
        try {
            selectedActivity.setDateTime(dateTimeInput);

            if (hasTimeConflict(selectedActivity)) {
                selectedActivity.markAsNotScheduled();
                return false;
            } else {
                addActivity(selectedActivity);
                selectedActivity.markAsScheduled();
                return true;
            }
        } catch (DateTimeException e) {
            return false;
        }
    }
}