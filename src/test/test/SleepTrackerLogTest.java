package test;

import model.SleepTracker;
import model.SleepTrackerLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class SleepTrackerLogTest {

    private SleepTrackerLog sleepTrackerLog;

    SleepTracker s1;
    SleepTracker s2;
    SleepTracker s3;
    SleepTracker s4;
    SleepTracker s5;
    SleepTracker s6;
    SleepTracker s7;
    SleepTracker s8;

    @BeforeEach
    public void setup() {
        sleepTrackerLog = new SleepTrackerLog();

        s1 = new SleepTracker("Monday", 8, "Well-rested");
        s2 = new SleepTracker("Tuesday", 7, "Harder to get up.");
        s3 = new SleepTracker("Wednesday", 4, "Not able to focus.");
        s4 = new SleepTracker("Thursday", 11, "Feeling groggy");
        s5 = new SleepTracker("Friday", 9, "A lot of energy.");
        s6 = new SleepTracker("Saturday", 8, "Woke up easily, motivated.");
        s7 = new SleepTracker("Sunday", 5, "Slept through alarms");
        s8 = new SleepTracker("Monday", 12, "Feeling sluggish");
    }

    @Test
    public void testAddNewWeek() {
        sleepTrackerLog.addNewDayEntry(s1);
        sleepTrackerLog.addNewDayEntry(s2);
        assertFalse(sleepTrackerLog.fullWeek());
        sleepTrackerLog.addNewDayEntry(s3);
        sleepTrackerLog.addNewDayEntry(s4);
        assertFalse(sleepTrackerLog.addNewWeek());
        sleepTrackerLog.addNewDayEntry(s5);
        sleepTrackerLog.addNewDayEntry(s6);
        sleepTrackerLog.addNewDayEntry(s7);
        assertTrue(sleepTrackerLog.fullWeek());
        assertTrue(sleepTrackerLog.addNewWeek());

    }


    @Test
    public void testCalculateAverageSleepTime() {
        sleepTrackerLog.addNewDayEntry(s1);
        sleepTrackerLog.addNewDayEntry(s2);
        sleepTrackerLog.addNewDayEntry(s3);
        sleepTrackerLog.addNewDayEntry(s4);
        sleepTrackerLog.addNewDayEntry(s5);
        sleepTrackerLog.addNewDayEntry(s6);
        sleepTrackerLog.addNewDayEntry(s7);
        assertEquals(7, sleepTrackerLog.calculateAverageSleepTime());
    }
}
