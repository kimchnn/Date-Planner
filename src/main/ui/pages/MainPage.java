package ui.pages;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import model.*;
import persistence.JsonWriter;
import ui.*;
import model.Event;

import java.util.List;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;

// Represents the main window of the Date Planner Application

public class MainPage extends Page implements ActionListener, WindowListener {

    private JPanel mainPanel;
    private JButton button;
    private JButton addActivityButton;
    private JButton viewActivitiesButton;
    private JButton viewScheduledButton;
    private JButton saveButton;
    private JButton quitButton;
    private ActivityCollection activityCollection;
    private DateItinerary dateItinerary;
    private JsonWriter jsonWriter;

    // EFFECTS: Initializes the main window, sets up the frame properties, and
    // initializes the components
    public MainPage(ActivityCollection activityCollection, DateItinerary dateItinerary, JsonWriter jsonWriter) {
        super();
        setTitle("Home");
        this.activityCollection = activityCollection;
        this.dateItinerary = dateItinerary;
        this.jsonWriter = jsonWriter;

        initializeMainPanel();

        setResizable(false);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Initializes the main panel with buttons and adds them to the frame
    public void initializeMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        super.initializePanel(mainPanel);
    }

    // EFFECTS: initializes the components for the main panel
    @Override
    public void initializePanelComponents() {
        mainPanel.add(createWelcomeMessage(), BorderLayout.NORTH);
        mainPanel.add(createCentrePanel(), BorderLayout.CENTER);
        mainPanel.add(createBottomPanel(), BorderLayout.SOUTH);
    }

    // EFFECTS: Creates and returns a JLabel with the welcome message
    public JLabel createWelcomeMessage() {
        JLabel welcomeMessage = new JLabel("Welcome!");
        welcomeMessage.setFont(new Font("Helvetica", Font.BOLD, 35));
        welcomeMessage.setBorder(new EmptyBorder(40, 30, 20, 0));
        welcomeMessage.setBounds(250, 50, 300, 30);
        welcomeMessage.setForeground(Color.decode("#FFABB1"));
        return welcomeMessage;
    }

    // EFFECTS: creates and returns the center panel with the add activity, view
    // activities, and view scheduled activities buttons
    public JPanel createCentrePanel() {
        JPanel centrePanel = new JPanel();
        centrePanel.setLayout(new GridLayout(1, 3, 10, 10));
        centrePanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        centrePanel.setBackground(Color.WHITE);

        addActivityButton = createButton("src/main/model/Assets/AddActivities.png", "Add a New Activity");
        viewActivitiesButton = createButton("src/main/model/Assets/ViewActivities.png", "View Activities");
        viewScheduledButton = createButton("src/main/model/Assets/ViewScheduledActivities.png",
                "View Scheduled Activities");
        centrePanel.add(addActivityButton);
        centrePanel.add(viewActivitiesButton);
        centrePanel.add(viewScheduledButton);

        return centrePanel;
    }

    // EFFECTS: Creates and returns the bottom panel with save and quit buttons
    public JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        bottomPanel.setBorder(new EmptyBorder(20, 30, 40, 30));
        bottomPanel.setBackground(Color.WHITE);

        saveButton = new JButton("Save");
        saveButton.setFont(new Font("Helvetica", Font.BOLD, 15));
        saveButton.setForeground(Color.black);
        saveButton.addActionListener(this);

        quitButton = new JButton("Quit");
        quitButton.setFont(new Font("Helvetica", Font.BOLD, 15));
        quitButton.setForeground(Color.black);
        quitButton.addActionListener(this);

        bottomPanel.add(saveButton);
        bottomPanel.add(quitButton);

        return bottomPanel;
    }

    // EFFECTS: creates and returns button with given image file and label
    public JButton createButton(String imageFile, String label) {
        ImageIcon icon = new ImageIcon(imageFile);
        Image image = icon.getImage().getScaledInstance(75, 75, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(image);
        button = new JButton(icon);
        button.setText(label);
        button.setVerticalTextPosition(SwingConstants.TOP);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setFont(new Font("Helvetica", Font.BOLD, 16));
        button.setForeground(Color.BLACK);
        button.addActionListener(this);
        return button;
    }

    // MODIFIES: this
    // EFFECTS: Handles button clicks and triggers corresponding actions
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == addActivityButton) {
            handleAddActivity();
        } else if (source == viewActivitiesButton) {
            handleViewActivities();
        } else if (source == viewScheduledButton) {
            handleViewScheduled();
        } else if (source == saveButton) {
            handleSave();
        } else if (source == quitButton) {
            handleQuit();
        }
    }

    // MODIFIES: this
    // EFFECTS: Opens a dialog to add a new activity. If an activity with the same
    // name exists, shows an error message. Otherwise, adds the new activity and
    // shows a success message
    public void handleAddActivity() {
        AddActivityDialog dialog = new AddActivityDialog(this);
        dialog.setVisible(true);

        if (dialog.isAddButtonPressed()) {
            String name = dialog.getActivityName();
            String category = dialog.getActivityCategory();
            String location = dialog.getActivityLocation();

            boolean success = activityCollection.addActivity(new Activity(name, category, location));
            if (success) {
                JOptionPane.showMessageDialog(this, "Activity successfully added: " + name);
            } else {
                JOptionPane.showMessageDialog(this, "An activity with the same name exists. Please use another name.");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Opens a window to view the list of activities. If no activities are
    // found, shows an error message
    public void handleViewActivities() {
        List<Activity> activities = activityCollection.getActivities();
        if (activities.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No activities found. Please add an activity first.");
        } else {
            ViewActivitiesWindow viewActivitiesWindow = new ViewActivitiesWindow(activities, activityCollection,
                    dateItinerary, jsonWriter);
            viewActivitiesWindow.setVisible(true);
            this.dispose();
        }
    }

    // MODIFIES: this
    // EFFECTS: Opens a dialog window to either view the full list of scheduled
    // activities or to view scheduled activities by date. If no scheduled
    // activities are found, shows an error message.
    public void handleViewScheduled() {
        List<Activity> scheduledActivities = dateItinerary.getScheduledActivities();
        if (scheduledActivities.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No scheduled activities found. Please schedule an activity first.");
        } else {
            ViewScheduleDialog dialog = new ViewScheduleDialog(this, activityCollection, dateItinerary, jsonWriter);
            dialog.setVisible(true);
        }
    }

    // MODIFIES: this
    // EFFECTS: Saves the activity collection and shows a success message
    public void handleSave() {
        try {
            jsonWriter.open();
            jsonWriter.write(activityCollection);
            jsonWriter.close();
            JOptionPane.showMessageDialog(this, "Saved successfully!");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Unable to save to file.");
        }
    }

    // MODIFIES: this
    // EFFECTS: Shows a goodbye message and exits the application
    public void handleQuit() {
        JOptionPane.showMessageDialog(this, "Thanks for using Date Planner, enjoy your date!");
        windowClosed(new WindowEvent(this, WindowEvent.WINDOW_CLOSED));
        dispose();
        System.exit(0);
    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
        for (Event event : EventLog.getInstance()) {
            System.out.println(event.toString());
        }
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

}
