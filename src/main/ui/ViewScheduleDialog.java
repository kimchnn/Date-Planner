package ui;

import javax.swing.*;

import model.Activity;
import model.ActivityCollection;
import model.DateItinerary;
import persistence.JsonWriter;
import ui.pages.ViewScheduledWindow;

import java.util.List;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

// represents a dialog for viewing all scheduled activities or scheduled activities by date

public class ViewScheduleDialog extends JDialog implements ActionListener {
    private JTextField yearField;
    private JTextField monthField;
    private JTextField dayField;
    private JButton viewAllButton;
    private JButton submitButton;
    private int year;
    private int month;
    private int day;
    private boolean isViewAllButtonPressed;
    private boolean isSubmitButtonPressed;
    private JFrame parent;
    private ActivityCollection activityCollection;
    private DateItinerary dateItinerary;
    private JsonWriter jsonWriter;

    // EFFECTS: Creates a new ViewScheduleDialog instance with the given parent,
    // activityCollection and Date Itinerary
    public ViewScheduleDialog(JFrame parent, ActivityCollection activityCollection, DateItinerary dateItinerary,
            JsonWriter jsonWriter) {
        super(parent, "View Scheduled Activities", true);
        this.activityCollection = activityCollection;
        this.dateItinerary = dateItinerary;
        this.jsonWriter = jsonWriter;
        this.parent = parent;

        add(createContentPanel());

        viewAllButton.addActionListener(this);
        submitButton.addActionListener(this);

        pack();
        setLocationRelativeTo(parent);
    }

    // EFFECTS: creates and returns the content panel for the dialog
    public JPanel createContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        viewAllButton = new JButton("View All Scheduled Activities");
        viewAllButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel instructionLabel = new JLabel("or enter date below to view scheduled activities by date:");
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        submitButton = new JButton("Submit");
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(viewAllButton);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(instructionLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(createYearJPanel());
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contentPanel.add(createMonthJPanel());
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contentPanel.add(createDayJPanel());
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(submitButton);

        return contentPanel;
    }

    // EFFECTS: creates and returns the panel for the year input field
    public JPanel createYearJPanel() {
        JLabel yearLabel = new JLabel("Year (yyyy): ");
        yearField = new JTextField(10);
        JPanel yearPanel = new JPanel();
        yearPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        yearPanel.add(yearLabel);
        yearPanel.add(yearField);

        return yearPanel;
    }

    // EFFECTS: creates and returns the panel for the month input field
    public JPanel createMonthJPanel() {
        JLabel monthLabel = new JLabel("Month (mm): ");
        monthField = new JTextField(10);
        JPanel monthPanel = new JPanel();
        monthPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        monthPanel.add(monthLabel);
        monthPanel.add(monthField);

        return monthPanel;
    }

    // EFFECTS: creates and returns the panel for the day input field
    public JPanel createDayJPanel() {
        JLabel dayLabel = new JLabel("Day (dd): ");
        dayField = new JTextField(10);
        JPanel dayPanel = new JPanel();
        dayPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        dayPanel.add(dayLabel);
        dayPanel.add(dayField);

        return dayPanel;
    }

    // MODIFIES: this
    // EFFECTS: Handles button click events for view all and submit buttons
    // If view all button is clicked, opens window to view list of all scheduled
    // activities
    // If submit button is clicked, opens window to view scheduled activities on the
    // specified date. If not all the fields are filled, it will generate an error
    // message. If there are no scheduled activities on the specified date, it will
    // generate an error message.
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == viewAllButton) {
            isViewAllButtonPressed = true;
            createScheduleWindow();
        } else if (source == submitButton) {
            if (areDateFieldsEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled out.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            } else {
                year = Integer.parseInt(yearField.getText().trim());
                month = Integer.parseInt(monthField.getText().trim());
                day = Integer.parseInt(dayField.getText().trim());

                List<Activity> scheduledActivitiesForDate = getScheduledActivitiesForDate(year, month, day);
                if (scheduledActivitiesForDate.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No activities scheduled for the specified date.",
                            "No Activities Found", JOptionPane.ERROR_MESSAGE);
                } else {
                    isSubmitButtonPressed = true;
                    createScheduleWindow();
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: generates new ViewScheduleWindow and disposes of the current dialog
    // and the MainPage frame
    public void createScheduleWindow() {
        dispose();
        new ViewScheduledWindow(activityCollection, dateItinerary, jsonWriter, year, month,
                day, isSubmitButtonPressed).setVisible(true);
        parent.dispose();
    }

    // EFFECTS: returns true if all the date fields are empty, false otherwise
    public boolean areDateFieldsEmpty() {
        return yearField.getText().trim().isEmpty() || monthField.getText().trim().isEmpty()
                || dayField.getText().trim().isEmpty();
    }

    // EFFECTS: generates a list of activities for the scheduled date
    public List<Activity> getScheduledActivitiesForDate(int year, int month, int day) {
        LocalDate dateInput = LocalDate.of(year, month, day);
        List<Activity> scheduledActivitiesForDate = dateItinerary.getActivitiesForDate(dateInput)
                .getScheduledActivities();
        return scheduledActivitiesForDate;
    }

    // EFFECTS: Returns the year entered by the user
    public int getYear() {
        return year;
    }

    // EFFECTS: Returns the month entered by the user
    public int getMonth() {
        return month;
    }

    // EFFECTS: Returns the day entered by the user
    public int getDay() {
        return day;
    }

    // EFFECTS: Returns true if the Submit button was pressed, false otherwise
    public boolean isSubmitButtonPressed() {
        return isSubmitButtonPressed;
    }

    // EFFECTS: Returns true if the ViewAll button was pressed, false otherwise
    public boolean isViewAllButtonPressed() {
        return isViewAllButtonPressed;
    }
}
