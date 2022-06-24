package persistance;

import model.SleepTracker;
import model.SleepTrackerLog;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// citation: worked off of JsonSerializationDemo to implement similar functionality in my project
public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            SleepTrackerLog st = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptySleepTrackerLog() {
        JsonReader reader = new JsonReader("./data/testReaderEmptySleepTrackerLog.json");
        try {
            SleepTrackerLog st = reader.read();
            assertEquals(0, st.numLog());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralSleepTrackerLog() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralSleepTrackerLog.json");
        try {
            SleepTrackerLog st = reader.read();
            List<SleepTracker> sleepLog = st.getSleepLog();
            assertEquals(2, sleepLog.size());
            checkSleepTracker("Monday", 8, "Well-rested");
            checkSleepTracker("Wednesday", 5, "Lacking energy");
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
