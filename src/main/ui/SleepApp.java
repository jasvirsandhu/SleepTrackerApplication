package ui;

import model.SleepTracker;
import model.SleepTrackerLog;
import persistence.JsonReader;
import persistence.JsonWriter;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Sleep application
public class SleepApp {
    private static final String JSON_STORE = "./data/SleepTrackerLog.json";
    private Scanner input;
    private static SleepTrackerLog mySleepLog;
    private static JsonWriter jsonWriter;
    private static JsonReader jsonReader;

    //EFFECTS: runs sleep log application
    public SleepApp() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runSleep();
    }

    //MODIFIES: this
    //EFFECTS: processes input from user
    private void runSleep() {
        boolean run = true;
        String command = null;

        init();

        while (run) {
            displayActions();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                run = false;
            } else {
                userCommand(command);
            }
        }
        System.out.println("\nSee you tomorrow!");
    }


    //EFFECTS: displays actions user can take in entry
    private void displayActions() {
        System.out.println("\n--------------------------------My Sleep Log-----------------------------------------");

        System.out.println("\nSelect from:");
        System.out.println("\td -> Add daily entry");
        System.out.println("\ta -> View weekly average");
        System.out.println("\ts -> Save work room to file");
        System.out.println("\tl -> Load work room from file");
        System.out.println("\tq -> Quit");
    }


    //MODIFIES: this
    //EFFECTS: processes user command
    private void userCommand(String command) {
        if (command.equals("d")) {
            doAddDailyEntry();
        } else if (command.equals("a")) {
            doCalculateWeeklyAverage();
        } else if (command.equals("s")) {
            saveSleepLog();
        } else if (command.equals("l")) {
            loadSleepLog();
        } else {
            System.out.println("Unable to process request.");
        }
    }


    //MODIFIES: this
    //EFFECTS: initializes log
    private void init() {
        mySleepLog = new SleepTrackerLog();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }


    //EFFECTS: gets weekly sleep average from log entries
    private void doCalculateWeeklyAverage() {
        SleepTrackerLog selected = selectSleepTrackerLog();
        System.out.println("Your daily sleep average this week: ");

        if (selected.fullWeek()) {
            System.out.println(selected.calculateAverageSleepTime());
        } else {
            System.out.println("Insufficient amount of entries to calculate average.");
        }

    }

    //EFFECTS: conducts addition of comments by user
    private void doAddDailyEntry() {
        SleepTrackerLog selected = selectSleepTrackerLog();

        System.out.println("Enter day: ");
        String day = input.next();

        System.out.println("Enter hours slept: ");
        int time = input.nextInt();

        System.out.println("Enter comments about sleep: ");
        String notes = input.next();

        SleepTracker entry = new SleepTracker(day, time, notes);
        if (selected.fullWeek()) {
            mySleepLog = new SleepTrackerLog();
            selected.addNewDayEntry(entry);

        } else {
            selected.addNewDayEntry(entry);
        }
        System.out.println("Day: " + day + "    Hours slept: " + time + "    Comments: " + notes);
    }

    //EFFECTS: returns Sleep Tracker log
    private SleepTrackerLog selectSleepTrackerLog() {
        if (mySleepLog == null) {
            mySleepLog = new SleepTrackerLog();
        }
        return mySleepLog;
    }

    // EFFECTS: saves the sleep log to file
    public static void saveSleepLog() {
        try {
            jsonWriter.open();
            jsonWriter.write(mySleepLog);
            jsonWriter.close();
            System.out.println("Saved " + mySleepLog + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads sleep log from file
    public static void loadSleepLog() {
        try {
            mySleepLog = jsonReader.read();
            System.out.println("Loaded " + mySleepLog + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}







