package ui.pages;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import model.Activity;
import model.ActivityCollection;
import model.DateItinerary;
import persistence.JsonWriter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;

// represents a window for viewing the scheduled activities

public class ViewScheduledWindow extends Page implements ActionListener {

    private JList<String> scheduledActivityList;
    private DefaultListModel<String> activityListModel;
    private int year;
    private int month;
    private int day;
    private boolean isSubmitButtonPressed;
    private JPanel mainPanel;
    private JButton backButton;
    private ActivityCollection activityCollection;
    private DateItinerary dateItinerary;
    private JsonWriter jsonWriter;
    private List<Activity> scheduledActivities;

    // EFFECTS: Constructs a frame for viewing scheduled activities and sets up the
    // components
    public ViewScheduledWindow(ActivityCollection activityCollection, DateItinerary dateItinerary,
            JsonWriter jsonWriter, int year, int month,
            int day, boolean isSubmitButtonPressed) {
        super();
        this.activityCollection = activityCollection;
        this.dateItinerary = dateItinerary;
        this.jsonWriter = jsonWriter;
        this.year = year;
        this.month = month;
        this.day = day;
        this.isSubmitButtonPressed = isSubmitButtonPressed;
        this.scheduledActivities = dateItinerary.getScheduledActivities();
        setTitle("Scheduled Activities");

        initializeMainPanel();

        setResizable(false);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Initializes the viewScheduledWindow with a header and scroll pane,
    // and adds them to the frame
    public void initializeMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        super.initializePanel(mainPanel);
    }

    // MODIFIES: this
    // EFFECTS: Creates and sets up components for the main panel

    @Override
    public void initializePanelComponents() {
        mainPanel.add(createHeader(), BorderLayout.NORTH);
        mainPanel.add(createScrollPane(), BorderLayout.CENTER);
        mainPanel.add(createBottomPanel(), BorderLayout.SOUTH);
    }

    // EFFECTS: creates and returns the header
    public JLabel createHeader() {
        JLabel header;

        if (isSubmitButtonPressed) {
            header = new JLabel("Scheduled Activities on " + year + "/" + month + "/" + day);
        } else {
            header = new JLabel("All Scheduled Activities");
        }
        header.setFont(new Font("Helvetica", Font.BOLD, 35));
        header.setForeground(Color.decode("#FFABB1"));
        header.setBorder(new EmptyBorder(40, 40, 20, 40));

        return header;
    }

    // EFFECTS: creates and returns the scroll pane for the list of scheduled
    // activities
    public JPanel createScrollPane() {
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BorderLayout());
        listPanel.setBackground(Color.WHITE);
        listPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
        activityListModel = new DefaultListModel<>();
        populateActivityListModel(scheduledActivities);
        scheduledActivityList = new JList<>(activityListModel);
        scheduledActivityList.setFont(new Font("Helvetica", Font.PLAIN, 18));
        JScrollPane scrollPane = new JScrollPane(scheduledActivityList);
        listPanel.add(scrollPane, BorderLayout.CENTER);
        return listPanel;
    }

    // EFFECTS: Creates and returns the bottom panel with a back button
    public JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        bottomPanel.setBorder(new EmptyBorder(20, 30, 40, 30));
        bottomPanel.setBackground(Color.WHITE);

        backButton = new JButton("Back");
        backButton.setFont(new Font("Helvetica", Font.BOLD, 15));
        backButton.setForeground(Color.black);
        backButton.addActionListener(this);

        bottomPanel.add(backButton);

        return bottomPanel;
    }

    // MODIFIES: this
    // EFFECTS: populate the DefaultListModel with names of scheduled activities
    public void populateActivityListModel(List<Activity> scheduledActivities) {
        if (!isSubmitButtonPressed) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            for (Activity activity : scheduledActivities) {
                activityListModel
                        .addElement("- " + activity.getDateTime().format(formatter) + ": " + activity.getName());
            }
        } else {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalDate dateInput = LocalDate.of(year, month, day);
            List<Activity> scheduledActivitiesForDate = dateItinerary.getActivitiesForDate(dateInput)
                    .getScheduledActivities();
            for (Activity activity : scheduledActivitiesForDate) {
                activityListModel
                        .addElement(
                                "- " + activity.getDateTime().format(timeFormatter) + ": " + activity.getName());
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Handles back button clicks and triggers corresponding action
    @Override
    public void actionPerformed(ActionEvent e) {
        new MainPage(activityCollection, dateItinerary, jsonWriter);
        this.dispose();
    }
}