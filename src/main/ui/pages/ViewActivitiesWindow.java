package ui.pages;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import model.Activity;
import model.ActivityCollection;
import model.DateItinerary;
import persistence.JsonWriter;

import java.util.List;

// Represents a window for viewing a list of activities

public class ViewActivitiesWindow extends Page implements ActionListener {

    private JList<String> activityList;
    private JButton backButton;
    private JButton selectButton;
    private List<Activity> activities;
    private DefaultListModel<String> activityListModel;
    private Activity selectedActivity;
    private JPanel mainPanel;
    private ActivityCollection activityCollection;
    private DateItinerary dateItinerary;
    private JsonWriter jsonWriter;

    // EFFECTS: Constructs a frame for viewing activities and sets up the components
    public ViewActivitiesWindow(List<Activity> activities, ActivityCollection activityCollection,
            DateItinerary dateItinerary, JsonWriter jsonWriter) {
        super();
        this.activityCollection = activityCollection;
        this.dateItinerary = dateItinerary;
        this.activities = activities;
        setTitle("Activity Collection");

        initializeMainPanel();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Initializes the viewActivitiesWindow with a header, scroll pane, and
    // button, and adds them to the frame
    public void initializeMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        super.initializePanel(mainPanel);
    }

    @Override
    public void initializePanelComponents() {
        mainPanel.add(createHeader(), BorderLayout.NORTH);
        mainPanel.add(createScrollPane(), BorderLayout.CENTER);
        mainPanel.add(createBottomPanel(), BorderLayout.SOUTH);
    }

    // EFFECTS: creates and returns the header
    public JLabel createHeader() {
        JLabel header = new JLabel("Activity Collection");
        header.setFont(new Font("Helvetica", Font.BOLD, 35));
        header.setForeground(Color.decode("#FFABB1"));
        header.setBorder(new EmptyBorder(40, 40, 20, 40));
        return header;
    }

    // EFFECTS: creates and returns the scroll pane for the list of activities
    public JPanel createScrollPane() {
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BorderLayout());
        listPanel.setBackground(Color.WHITE);
        listPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
        activityListModel = new DefaultListModel<>();
        populateActivityListModel(activities);
        activityList = new JList<>(activityListModel);
        activityList.setFont(new Font("Helvetica", Font.PLAIN, 18));
        JScrollPane scrollPane = new JScrollPane(activityList);
        listPanel.add(scrollPane, BorderLayout.CENTER);
        return listPanel;
    }

    // EFFECTS: Creates and returns the bottom panel with back and select buttons
    public JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        bottomPanel.setBorder(new EmptyBorder(20, 30, 40, 30));
        bottomPanel.setBackground(Color.WHITE);

        backButton = new JButton("Back");
        backButton.setFont(new Font("Helvetica", Font.BOLD, 15));
        backButton.setForeground(Color.black);
        backButton.addActionListener(this);

        selectButton = new JButton("Select");
        selectButton.setFont(new Font("Helvetica", Font.BOLD, 15));
        selectButton.setForeground(Color.black);
        selectButton.addActionListener(this);

        bottomPanel.add(backButton);
        bottomPanel.add(selectButton);

        return bottomPanel;
    }

    // MODIFIES: this
    // EFFECTS: Handles button clicks and triggers corresponding actions
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == backButton) {
            new MainPage(activityCollection, dateItinerary, jsonWriter);
            this.dispose();
        } else {
            handleSelectButton();
        }
    }

    // MODIFIES: this
    // EFFECTS: populate the DefaultListModel with activity names from the list of
    // activities
    public void populateActivityListModel(List<Activity> activities) {
        for (Activity activity : activities) {
            activityListModel
                    .addElement("- " + activity.getName());
        }
    }

    // EFFECTS: Handles the action when the "Select" button is pressed.
    // If an activity is selected, displays a message dialog with the option to
    // remove or schedule a date and time for the activity
    // If no activity is selected, displays a warning message dialog indicating that
    // no activity was selected.
    public void handleSelectButton() {
        int selectedIndex = activityList.getSelectedIndex();
        if (selectedIndex != -1) {
            selectedActivity = activities.get(selectedIndex);
            Object[] options = { "Schedule", "Remove" };
            int choice = JOptionPane.showOptionDialog(this, "Selected activity: " + selectedActivity.getName(),
                    "Select Option", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
                    options[0]);
            if (choice == JOptionPane.YES_OPTION) {
                handleScheduleButton();
            } else if (choice == JOptionPane.NO_OPTION) {
                handleRemoveButton(selectedIndex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No activity selected.");
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts the user to enter a date and time for scheduling the
    // selected activity
    // If the date and time input is invalid, schedules the activity and displays a
    // success message
    // If the input is invalid or conflicts with another scheduled activity,
    // displays an error message
    public void handleScheduleButton() {
        String dateTime = JOptionPane.showInputDialog(this, "Enter date and time for scheduling (yyyy-MM-dd HH:mm):");

        if (dateTime != null && !dateTime.trim().isEmpty()) {
            boolean success = dateItinerary.scheduleActivity(selectedActivity, dateTime);
            if (!success) {
                JOptionPane.showMessageDialog(this, selectedActivity.getName()
                        + " cannot be scheduled due to a time conflict or invalid time input. Please set another time.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(this, selectedActivity.getName() + " scheduled at: " + dateTime);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Date and time input cannot be empty.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes the selected activity from the list and updates the list
    // model, displays a message indicating the activity has been removed
    public void handleRemoveButton(int selectedIndex) {
        activityCollection.removeActivity(activities.get(selectedIndex));
        activityListModel.remove(selectedIndex);
        JOptionPane.showMessageDialog(this, "Activity removed.");

    }

}