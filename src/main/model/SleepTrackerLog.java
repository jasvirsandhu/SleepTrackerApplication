package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents a log of Sleep Tracker entries.
public class SleepTrackerLog implements Writable {
    private ArrayList<SleepTracker> sleepLog;
    public static final int WEEK = 7;

    //EFFECTS: creates weekly log recording documented daily SleepTracker entries
    public SleepTrackerLog() {
        sleepLog = new ArrayList<>();

    }

    //MODIFIES: this
    //EFFECTS: adds new daily entry to log
    public void addNewDayEntry(SleepTracker s) {
        sleepLog.add(s);
        EventLog.getInstance().logEvent(new Event("Added new day entry to sleep log."));
    }


    //EFFECTS: returns true if number of entries has reached full week, otherwise false
    public boolean fullWeek() {
        if (sleepLog.size() >= WEEK) {
            return true;
        } else {
            return false;
        }
    }

    //MODIFIES: this
    //EFFECTS: if log entries has reached full week, adds new log and returns true, else returns false
    public boolean addNewWeek() {
        if (sleepLog.size() >= WEEK) {
            new ArrayList<SleepTracker>();
            return true;
        } else {
            return false;
        }
    }

    //EFFECTS: returns number of sleep entries in sleep log
    public int numLog() {
        return sleepLog.size();
    }

    //REQUIRES: sleep entries must equal 7
    //EFFECTS: calculates average sleep time over 7-day period
    public int calculateAverageSleepTime() {
        EventLog.getInstance().logEvent(new Event("Calculated average sleep time."));
        int sumForHours = 0;
        for (int i = 0; i < 7; ++i) {
            sumForHours += sleepLog.get(i).getHours();
        }
        return sumForHours / sleepLog.size();
    }

    //EFFECTS: returns an unmodifiable list of sleep entries in this sleep log
    public List<SleepTracker> getSleepLog() {
        return Collections.unmodifiableList(sleepLog);
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("sleepLog", sleepToJson());
        return json;
    }

    // EFFECTS: returns sleep entries in sleep log as a JSON array
    private JSONArray sleepToJson() {
        JSONArray jsonArray = new JSONArray();

        for (SleepTracker s : sleepLog) {
            jsonArray.put(s.toJson());
        }
        return jsonArray;
    }
}
