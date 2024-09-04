package persistence;

import org.json.JSONObject;

// Interface to standardize conversion of objects into JSON format

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
