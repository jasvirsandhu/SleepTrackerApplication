package persistance;

import model.SleepTracker;
import model.SleepTrackerLog;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// citation: worked off of JsonSerializationDemo to implement similar functionality in my project
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            SleepTrackerLog st = new SleepTrackerLog();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptySleepTrackerLog() {
        try {
            SleepTrackerLog st = new SleepTrackerLog();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptySleepTrackerLog.json");
            writer.open();
            writer.write(st);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptySleepTrackerLog.json");
            st = reader.read();
            assertEquals(0, st.numLog());
        } catch (IOException e) {
            fail("Exception should not have been thrown.");
        }
    }

    @Test
    void testWriterGeneralSleepTrackerLog() {
        try {
            SleepTrackerLog st = new SleepTrackerLog();
            st.addNewDayEntry(new SleepTracker("Monday", 8, "Well-rested"));
            st.addNewDayEntry(new SleepTracker("Tuesday", 4, "Hard time waking up"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralSleepTrackerLog.json");
            writer.open();
            writer.write(st);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralSleepTrackerLog.json");
            st = reader.read();
            List<SleepTracker> sleepLog = st.getSleepLog();
            assertEquals(2, sleepLog.size());

        } catch (IOException e) {
            fail("Exception should not have been thrown.");
        }
    }
}
