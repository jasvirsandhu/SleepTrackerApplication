package persistence;

import model.Event;
import model.EventLog;
import model.SleepTracker;
import model.SleepTrackerLog;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// citation: worked off of JsonSerializationDemo to implement similar functionality in my project

// Represents a reader that reads sleep log from JSON data stored in file
public class JsonReader {
    private String source;

    //EFFECTS: constructs reader to read source file
    public JsonReader(String source) {
        this.source = source;
    }

    //EFFECTS: reads sleep tracker log from file and returns it;
    //throws IOException if an error occurs reading data from file
    public SleepTrackerLog read() throws IOException {
        EventLog.getInstance().logEvent(new Event("Loaded sleep log from file."));
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSleepTrackerLog(jsonObject);
    }

    //EFFECTS: reads source file as string and returns it
    public String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    //EFFECTS: parses sleep tracker log from JSON object and returns it
    private SleepTrackerLog parseSleepTrackerLog(JSONObject jsonObject) {
        SleepTrackerLog st = new SleepTrackerLog();
        addSleepTrackers(st, jsonObject);
        return st;
    }

    //MODIFIES: st
    //EFFECTS: parses sleep tracker entries from JSON object and adds them to sleep log
    private void addSleepTrackers(SleepTrackerLog st, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("sleepLog");
        for (Object json : jsonArray) {
            JSONObject nextSleepTracker = (JSONObject) json;
            addSleepTracker(st, nextSleepTracker);
        }
    }

    //MODIFIES: st
    //EFFECTS: parses sleep tracker entry from JSON object and adds it to sleep log
    private void addSleepTracker(SleepTrackerLog st, JSONObject jsonObject) {
        String day = jsonObject.getString("day");
        Integer hours = jsonObject.getInt("hours");
        String notes = jsonObject.getString("notes");
        SleepTracker sleepTracker = new SleepTracker(day, hours, notes);
        st.addNewDayEntry(sleepTracker);
    }


}
