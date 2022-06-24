package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents an entry named SleepTracker having the day of the week, hours slept and comments describing sleep.
public class SleepTracker implements Writable {
    private String day;                // day of week
    private int hours;                 // hours slept
    private String notes;              // user is able to record how they felt after amount of sleep


    //EFFECTS: creates daily log with day, hours slept and notes about sleep patterns/noticeable changes.
    public SleepTracker(String day, int hours, String notes) {
        this.day = day;
        this.hours = hours;
        this.notes = notes;
    }

    public String getDay() {
        return day;
    }

    public int getHours() {
        return hours;
    }

    public String getNotes() {
        return notes;
    }

    //REQUIRES: time >= 0
    //MODIFIES: this
    //EFFECTS: adds time slept in hours
    public int addSleepTime(int time) {
        hours = time + hours;
        return hours;
    }

    //REQUIRES: time >= 0 and time <= getHours()
    //MODIFIES: this
    //EFFECTS: removes log of time slept in day chosen
    public int removeSleepTime(int time) {
        hours = hours - time;
        return hours;
    }

    //MODIFIES: this
    //EFFECTS: allows user to add in notes about sleep
    public void addSleepNotes(String message) {
        this.notes = message;
    }


    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("day", day);
        json.put("hours", hours);
        json.put("notes", notes);
        return json;
    }
}

