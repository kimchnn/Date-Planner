package ui.pages;

import javax.swing.*;

import model.Activity;
import model.ActivityCollection;
import model.DateItinerary;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

// Creates the launch window 
public class LaunchPage extends Page implements ActionListener {

    private JButton getStartedButton;
    private JPanel launchPanel;
    private ActivityCollection activities;
    private DateItinerary scheduledActivities;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/activityCollection.json";

    // EFFECTS: Constructs a Launchpage instance and sets up the application
    // GUI
    public LaunchPage() {
        super();
        setTitle("Date Planner");

        activities = new ActivityCollection();
        scheduledActivities = new DateItinerary();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        initializeMainPanel();

        setResizable(false);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Creates and sets up components for the launch page panel
    public void initializeMainPanel() {
        launchPanel = new JPanel();
        launchPanel.setBackground(Color.decode("#FFABB1"));
        launchPanel.setLayout(null);
        launchPanel.setBounds(0, 0, WIDTH, HEIGHT);
        initializePanelComponents();
        this.add(launchPanel);
    }

    // MODIFIES: this
    // EFFECTS: Creates and sets up components for the launch page panel
    @Override
    public void initializePanelComponents() {

        JLabel titleText = new JLabel("Date Planner", SwingConstants.CENTER);
        titleText.setFont(new Font("Helvetica", Font.BOLD, 40));
        titleText.setBounds(250, 50, 300, 50);
        titleText.setForeground(Color.WHITE);
        launchPanel.add(titleText);

        ImageIcon launchIcon = new ImageIcon("src/main/model/Assets/Icon.png");
        Image image = launchIcon.getImage().getScaledInstance(300, 300, java.awt.Image.SCALE_SMOOTH);
        launchIcon = new ImageIcon(image);
        JLabel launchIconLabel = new JLabel(launchIcon);
        launchIconLabel.setBounds(250, 120, 300, 300);
        launchPanel.add(launchIconLabel);

        getStartedButton = new JButton("Get started");
        getStartedButton.setFont(new Font("Helvetica", Font.BOLD, 15));
        getStartedButton.setBounds(300, 450, 200, 50);
        getStartedButton.setForeground(Color.black);
        getStartedButton.addActionListener(this);
        launchPanel.add(getStartedButton);

        add(launchPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: Calls the handleGetStarted method if source of the ActionEvent is
    // the getStartedButton; otherwise it does nothing
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == getStartedButton) {
            handleGetStarted();
        }
    }

    // MODIFIES: this
    // EFFECTS: Displays a dialog for the user to choose between loading the last
    // session or starting a new session; disposes the current frame and opens the
    // MainWindow
    public void handleGetStarted() {
        Object[] options = { "Load Last Session", "Start New Session" };
        int choice = JOptionPane.showOptionDialog(this, "Would you like to...", "Getting Started",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (choice == JOptionPane.YES_OPTION) {
            handleLoad();
            dispose();
            MainPage mainWindow = new MainPage(activities, scheduledActivities, jsonWriter);
            mainWindow.setVisible(true);
            this.dispose();
        } else if (choice == JOptionPane.NO_OPTION) {
            dispose();
            MainPage mainWindow = new MainPage(activities, scheduledActivities, jsonWriter);
            mainWindow.setVisible(true);
            this.dispose();
        }
    }

    // MODIFIES: this
    // EFFECTS: Loads activities from the last session and displays a message
    // indicating the result; if no activities are found, prompts the user to add an
    // activity
    public void handleLoad() {
        try {
            ActivityCollection loadedActivities = jsonReader.read();
            this.activities = loadedActivities;
            for (Activity activity : activities.getActivities()) {
                if (activity.getScheduleStatus()) {
                    scheduledActivities.addActivity(activity);
                }
            }
            if (activities.getActivities().isEmpty()) {
                JOptionPane.showMessageDialog(this, "No activities found. Starting a new session.");
            } else {
                JOptionPane.showMessageDialog(this, "Activities have been successfully loaded!");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to read from file: " + JSON_STORE);
        }
    }

}