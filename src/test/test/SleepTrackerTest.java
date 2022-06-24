package test;

import model.SleepTracker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SleepTrackerTest {
    private SleepTracker testSleepTracker;
    private static int WEEK = 7;

    SleepTracker s1;
    SleepTracker s2;
    SleepTracker s3;
    SleepTracker s4;
    SleepTracker s5;
    SleepTracker s6;
    SleepTracker s7;


    @BeforeEach
    void runBefore() {
        testSleepTracker = new SleepTracker("Monday", 0, "");

        s1 = new SleepTracker("Monday", 8, "Well-rested");
        s2 = new SleepTracker("Tuesday", 7, "Harder to get up.");
        s3 = new SleepTracker("Wednesday", 4, "Not able to focus.");
        s4 = new SleepTracker("Thursday", 11, "Feeling groggy");
        s5 = new SleepTracker("Friday", 9, "A lot of energy.");
        s6 = new SleepTracker("Saturday", 8, "Woke up easily, motivated.");
        s7 = new SleepTracker("Sunday", 5, "Slept through alarms");

    }

    @Test
    void testConstructor() {
        assertEquals("Monday", testSleepTracker.getDay());
        assertEquals(0, testSleepTracker.getHours());
        assertEquals("", testSleepTracker.getNotes());
    }

    @Test
    void testAddSleepTime() {
        testSleepTracker.addSleepTime(8);
        assertEquals(8, testSleepTracker.getHours());
    }

    @Test
    void removeSleepTime() {
        testSleepTracker.addSleepTime(8);
        testSleepTracker.removeSleepTime(4);
        assertEquals(4, testSleepTracker.getHours());
    }

    @Test
    void testAddSleepNotes() {
        testSleepTracker.addSleepNotes("Woke up feeling well-rested!");
        assertEquals("Woke up feeling well-rested!", testSleepTracker.getNotes());
    }

}