package persistence;

import model.Activity;
import model.ActivityCollection;
import model.DateItinerary;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Referenced code from JsonSerializationDemo

// Represents a reader that reads activityCollection from JSON data stored in file

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads activityCollection from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ActivityCollection read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseActivityCollection(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses activityCollection from JSON object and returns it
    private ActivityCollection parseActivityCollection(JSONObject jsonObject) {
        ActivityCollection ac = new ActivityCollection();
        addActivities(ac, jsonObject);
        return ac;
    }

    // MODIFIES: ac
    // EFFECTS: parses activities from JSON object and adds them to
    // activityCollection
    private void addActivities(ActivityCollection ac, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("activities");
        for (Object json : jsonArray) {
            JSONObject nextActivity = (JSONObject) json;
            addActivity(ac, nextActivity);
        }
    }

    // MODIFIES: ac
    // EFFECTS: parses activity from JSON object and adds it to activityCollection
    private void addActivity(ActivityCollection ac, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String category = jsonObject.getString("category");
        String location = jsonObject.getString("location");
        String dateTimeString = jsonObject.optString("dateTimeString", "");
        boolean scheduleStatus = jsonObject.getBoolean("scheduleStatus");

        Activity activity = new Activity(name, category, location);
        DateItinerary scheduledActivities = new DateItinerary();
        ac.addActivity(activity);
        activity.setDateTime(dateTimeString);
        if (scheduleStatus) {
            activity.markAsScheduled();
            scheduledActivities.addActivity(activity);
        } else {
            activity.markAsNotScheduled();

        }
    }

}
