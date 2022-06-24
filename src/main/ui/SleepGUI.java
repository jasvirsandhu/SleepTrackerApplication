package ui;

import model.Event;
import model.EventLog;
import model.SleepTracker;
import model.SleepTrackerLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

// Sleep Tracker Log Application
public class SleepGUI extends JFrame implements ActionListener {
    private final JPanel jpanel;
    private final JPanel jpanel2;

    private final JLabel labelDay;
    private final JTextField textDay;

    private final JLabel labelHours;
    private final JTextField textHours;

    private final JLabel labelComments;
    private final JTextField textComments;

    private final JButton buttonSubmit;
    private final JButton buttonAverage;

    private ImageIcon image;
    private JLabel label;

    private final DefaultTableModel tableModel;

    private static JsonWriter jsonWriter;
    private static JsonReader jsonReader;
    private static final String JSON_STORE = "./data/SleepTrackerLog.json";

    private SleepTrackerLog sleepLog;

    //EFFECTS: runs the sleep log application
    public SleepGUI() {

        super("Sleep Log");
        setPreferredSize(new Dimension(900, 700));
        setLayout(new GridLayout(4, 2));

        jpanel = new JPanel();
        jpanel2 = new JPanel();
        labelDay = new JLabel("   Enter Day:");
        textDay = new JTextField(9);

        labelHours = new JLabel("   Enter hours slept:");
        textHours = new JTextField(3);

        labelComments = new JLabel("   Enter comments:");
        textComments = new JTextField(10);

        buttonSubmit = new JButton("Submit Entry");
        buttonAverage = new JButton("Calculate Average");

        buttonSubmit.setActionCommand("submit");
        buttonSubmit.addActionListener(this);

        buttonAverage.setActionCommand("average");
        buttonAverage.addActionListener(this);

        tableModel = new DefaultTableModel();

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        sleepLog = new SleepTrackerLog();

        runSleepApp();
        printLog();
    }

    //EFFECTS: calls necessary methods to display and run application
    public void runSleepApp() {
        outline();
        table();
        addImage();
        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }

    //EFFECTS: displays text fields and labels to user
    public void outline() {
        add(jpanel);
        jpanel.add(menu());
        jpanel.add(labelDay);
        jpanel.add(textDay);
        jpanel.add(labelHours);
        jpanel.add(textHours);
        jpanel.add(labelComments);
        jpanel.add(textComments);
        jpanel.add(buttonSubmit);
        jpanel.add(buttonAverage);
    }

    //MODIFIES: this
    //EFFECTS: processes the user's command
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("submit")) {
            fillInTable();
        }

        if (e.getActionCommand().equals("average")) {
            try {
                average();
            } catch (Exception ex) {
                System.out.println("Insufficient amount of entries: minimum of 7 entries needed.");
                JFrame averageMessage = new JFrame();
                JOptionPane.showMessageDialog(averageMessage,
                        "Insufficient data: minimum of 7 entries needed.");
            }
        }

        if (e.getActionCommand().equals("save")) {
            saveSleepLog();
        }

        if (e.getActionCommand().equals("load")) {
            loadSleepLog();
        }
    }

    //EFFECTS: displays empty table with columns
    public void table() {
        add(jpanel2);
        tableModel.addColumn("DAY");
        tableModel.addColumn("HOURS");
        tableModel.addColumn("NOTES");

        JTable jt = new JTable(tableModel);
        jt.setPreferredScrollableViewportSize(new Dimension(600, 700));
        JScrollPane sp = new JScrollPane(jt);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sp.setPreferredSize(new Dimension(500, 150));

        jpanel2.add(sp);
        jt.setFillsViewportHeight(true);
        sp.setViewportView(jt);
        jpanel2.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: fills in table by adding row with corresponding user inputs from text fields
    public void fillInTable() {
        String day = textDay.getText();
        String hours = textHours.getText();
        String notes = textComments.getText();
        tableModel.addRow(new Object[]{day, hours, notes});

        SleepTracker st;
        st = new SleepTracker(day, Integer.parseInt(hours), notes);

        sleepLog.addNewDayEntry(st);

        textDay.setText(null);
        textHours.setText(null);
        textComments.setText(null);
    }

    //EFFECTS: displays drop down menu bar with options to save or open previous file
    public JMenu fileMenu() {
        JMenu fm = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open");
        fm.add(openItem);
        openItem.setActionCommand("load");
        openItem.addActionListener(this);

        JMenuItem saveItem = new JMenuItem("Save");
        fm.add(saveItem);
        saveItem.setActionCommand("save");
        saveItem.addActionListener(this);
        return fm;
    }

    //EFFECTS: displays main file menu option in the upper corner of user's screen
    public JMenuBar menu() {
        JMenuBar menu = new JMenuBar();
        menu.add(fileMenu());
        return menu;
    }

    // EFFECTS: saves the sleep log to file
    public void saveSleepLog() {
        try {
            jsonWriter.open();
            jsonWriter.write(sleepLog);
            jsonWriter.close();
            System.out.println("Saved " + sleepLog + " to " + JSON_STORE);

            JFrame savedMessage = new JFrame();
            JOptionPane.showMessageDialog(savedMessage, "Your sleep log was successfully saved!");

        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads sleep log from file
    public void loadSleepLog() {
        try {
            sleepLog = jsonReader.read();
            System.out.println("Loaded " + sleepLog + " from " + JSON_STORE);

            tableModel.setRowCount(0);
            for (SleepTracker sleepTracker : sleepLog.getSleepLog()) {
                String day = sleepTracker.getDay();
                Integer hours = sleepTracker.getHours();
                String notes = sleepTracker.getNotes();
                tableModel.addRow(new Object[]{day, hours, notes});
            }

        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    //EFFECTS: calculates average sleep time from user input displayed in JTable
    public void average() {
        int average = sleepLog.calculateAverageSleepTime();
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, "Your average sleep time: " + average + " hours");
    }

    //EFFECTS: adds graphic to application
    public void addImage() {
        image = new ImageIcon(getClass().getResource("SleepLogo.png"));
        label = new JLabel(image);
        add(label);
    }

    //EFFECTS: prints out the Event Log on ui console when user exits application.
    public void printLog() {
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                for (Event event : EventLog.getInstance()) {
                    System.out.println(event.getDescription());
                }
                System.exit(0);
            }
        });
    }

    //EFFECTS: runs entire application
    public static void main(String[] args) {
        new SleepGUI();
    }
}


