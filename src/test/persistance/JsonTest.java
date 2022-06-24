package persistance;

import model.SleepTracker;

import static org.junit.jupiter.api.Assertions.assertEquals;
// citation: worked off of JsonSerializationDemo to implement similar functionality in my project

public class JsonTest {
    protected void checkSleepTracker(String day, int hours, String notes) {
        SleepTracker sleepTracker = new SleepTracker(day, hours, notes);
        assertEquals(day, sleepTracker.getDay());
        assertEquals(hours, sleepTracker.getHours());
        assertEquals(notes, sleepTracker.getNotes());
    }
}
