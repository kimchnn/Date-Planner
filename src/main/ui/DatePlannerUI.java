package ui;

// Referenced code from Lab 3 - FlashCard Reviewer 
// Referenced code from JsonSerializationDemo

// A console-based application for adding and managing activities and scheduling dates.
// Users can add activties, view and filter them, schedule activities for specified dates and times, and 
// view scheduled activities by date.

import model.Activity;
import model.ActivityCollection;
import model.DateItinerary;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class DatePlannerUI {

    private ActivityCollection activities;
    private DateItinerary scheduledActivities;
    private Scanner scanner;
    private boolean isProgramRunning;
    private static final String JSON_STORE = "./data/activityCollection.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: creates an instance of the Date Planner console ui application
    public DatePlannerUI() throws FileNotFoundException {
        init();
        printDivider();
        System.out.println("Welcome to the Date Planner app!");
        printDivider();
        while (this.isProgramRunning) {
            handleMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes Date Planner application with starting values
    public void init() {
        this.activities = new ActivityCollection();
        this.scheduledActivities = new DateItinerary();
        this.scanner = new Scanner(System.in);
        this.isProgramRunning = true;
        this.jsonWriter = new JsonWriter(JSON_STORE);
        this.jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: displays and processes inputs for the main menu
    public void handleMenu() {
        displayMenu();
        String input = this.scanner.nextLine();
        processMenuCommands(input);
    }

    // EFFECTS: displays commands that can be used in the main menu
    public void displayMenu() {
        System.out.println("Please select an option:");
        System.out.println("a: Add a new activity");
        System.out.println("v: View activities in the collection");
        System.out.println("d: View scheduled dates");
        System.out.println("s: Save activity collection to file");
        System.out.println("l: Load activity collection from file");
        System.out.println("q: Exit the application");
        printDivider();
    }

    // EFFECTS: processes the user's inputs for the main menu
    public void processMenuCommands(String input) {
        printDivider();
        switch (input) {
            case "a":
                addNewActivityInput();
                break;
            case "v":
                viewActivityCollection();
                break;
            case "d":
                viewScheduledDates();
                break;
            case "s":
                saveActivityCollection();
                break;
            case "l":
                loadActivityCollection();
                break;
            case "q":
                quitApplication();
                break;
            default:
                System.out.println("Invalid option inputted. Please try again.");
        }
    }

    // EFFECTS: processes user's activity name, category and location
    // inputs to add a new activity
    public void addNewActivityInput() {
        System.out.println("Please enter the activity's name:");
        String name = this.scanner.nextLine();

        System.out.println("\nPlease enter the activity's category:");
        String category = this.scanner.nextLine();

        System.out.println("\nPlease enter the activity's location:");
        String location = this.scanner.nextLine();

        Activity activity = new Activity(name, category, location);
        if (!activities.hasDuplicateName(activity)) {
            activities.addActivity(activity);
            System.out.println("\nNew activity has been sucessfully created!");
            printDivider();
        } else {
            System.out
                    .println("\nAn activity named " + activity.getName() + " already exists. Please use another name.");
            printDivider();
        }
        handleMenu();
    }

    // EFFECTS: displays and processes inputs for the view activity collection menu
    public void viewActivityCollection() {
        checkEmptyActivities();
        displayCollectionMenu();
        String collectionInput = this.scanner.nextLine();
        processCollectionMenuCommands(collectionInput);
    }

    // EFFECTS: displays and processes inputs for the view scheduled dates menu
    public void viewScheduledDates() {
        checkEmptyActivities();
        displayScheduledMenu();
        String scheduleInput = this.scanner.nextLine();
        processScheduledMenuCommands(scheduleInput);
    }

    // EFFECTS: displays commands that can be used in the view activity collection
    // menu
    public void displayCollectionMenu() {
        System.out.println("Enter 'a' to view all activities");
        System.out.println("Enter 'c' to view activities by category");
        System.out.println("Enter 'l' to view activities by location");
        System.out.println("Enter 'q' to return to the main menu");
        printDivider();
    }

    // EFFECTS: processes the user's inputs for the activity collection menu
    public void processCollectionMenuCommands(String collectionInput) {
        printDivider();
        switch (collectionInput) {
            case "a":
                viewAllActivities();
                selectActivityCommand();
                break;
            case "c":
                viewActivitiesByCategoryInput();
                selectActivityCommand();
                break;
            case "l":
                viewActivitiesByLocationInput();
                selectActivityCommand();
                break;
            case "q":
                System.out.println("Returning to the main menu...\n");
                printDivider();
                handleMenu();
                break;
            default:
                System.out.println("Invalid option inputted. Please try again.");
        }
    }

    // EFFECTS: Returns a list of all activities added to the activity collection
    public void viewAllActivities() {
        System.out.println("All activities:");
        for (Activity activity : activities.getActivities()) {
            System.out.println("- " + activity.getName());
        }
    }

    // EFFECTS: processes user's category input to filter activities by specified
    // category
    public void viewActivitiesByCategoryInput() {
        System.out.println("Please enter activity category:");
        String categoryInput = this.scanner.nextLine();
        ActivityCollection filteredByCategory = activities.filterByCategory(categoryInput);
        if (filteredByCategory.getActivities().isEmpty()) {
            System.out.println("No activities found with the category: " + categoryInput);
            printDivider();
            handleMenu();
        }
        printDivider();
        System.out.println(categoryInput + " activities:");
        for (Activity filteredActivity : filteredByCategory.getActivities()) {
            System.out.println("- " + filteredActivity.getName());
        }
    }

    // EFFECTS: processes user's location input to filter activities by specified
    // location
    public void viewActivitiesByLocationInput() {
        System.out.println("Please enter activity location:");
        String locationInput = this.scanner.nextLine();
        ActivityCollection filteredByLocation = activities.filterByLocation(locationInput);
        if (filteredByLocation.getActivities().isEmpty()) {
            System.out.println("No activities found with the location: " + locationInput);
            printDivider();
            handleMenu();
        }
        printDivider();
        System.out.println(locationInput + " activities:");
        for (Activity filteredActivity : filteredByLocation.getActivities()) {
            System.out.println("- " + filteredActivity.getName());
        }

    }

    // EFFECTS: processes the user's inputs to select an activity to remove,
    // schedule an activity, or to return to the main menu
    public void selectActivityCommand() {
        printDivider();
        System.out.println("Enter 'q' to return to main menu.");
        System.out.println("Or to select an activity, please enter the activity name:");
        String input = this.scanner.nextLine();
        if (input.equals("q")) {
            printDivider();
            handleMenu();
        } else {
            Activity selectedActivity = activitySelector(input);
            if (selectedActivity == null) {
                printDivider();
                handleMenu();
            }
            displayActivityMenu();
            String activityInput = this.scanner.nextLine();
            handleActivityCommands(activityInput, selectedActivity);
        }
    }

    // EFFECTS: returns the activity based on the specified activity name
    public Activity activitySelector(String input) {

        ActivityCollection filteredByName = activities.filterByName(input);
        if (filteredByName.getActivities().isEmpty()) {
            System.out.println("No activities found with the name: " + input);
            return null;
        } else {
            return filteredByName.getActivities().get(0);
        }
    }

    // EFFECTS: displays a list of commands that can be used on a selected activity
    public void displayActivityMenu() {
        printDivider();
        System.out.println("Enter 'r' to remove this activity from collection");
        System.out.println("Enter 's' to schedule a date and time for this activity");
        System.out.println("Enter 'q' to return to the main menu");
        printDivider();
    }

    // EFFECTS: processes the user's input for a selected activity
    public void handleActivityCommands(String activityInput, Activity selectedActivity) {
        switch (activityInput) {
            case "r":
                removeActivity(selectedActivity);
                break;
            case "s":
                scheduleActivityInput(selectedActivity);
                break;
            case "q":
                System.out.println("Returning to the menu...");
                printDivider();
                handleMenu();
                break;
            default:
                System.out.println("Invalid option inputted. Please try again.");
        }
        handleMenu();
    }

    // MODIFIES: this
    // EFFECTS: removes selected activity from the activity collection
    public void removeActivity(Activity selectedActivity) {
        activities.removeActivity(selectedActivity);
        scheduledActivities.removeActivity(selectedActivity);
        System.out.println(selectedActivity.getName() + " has been successfully removed!");
        printDivider();
    }

    // REQUIRES: valid date and time format (yyyy-MM-dd hh:mm)
    // and valid date and time (e.g. 2024-02-31 25:21 would not be valid)
    // MODIFIES: this, selectedActivity, scheduledActivities
    // EFFECTS: processes user's date input for scheduling an activity
    public void scheduleActivityInput(Activity selectedActivity) {
        System.out.println("Please enter the date and time of the scheduled activity (yyyy-MM-dd hh:mm):");
        String dateTimeInput = scanner.nextLine();
        boolean success = scheduledActivities.scheduleActivity(selectedActivity, dateTimeInput);
        if (success) {
            System.out.println(selectedActivity.getName() + " has been successfully scheduled!");
        } else {
            System.out.println(selectedActivity.getName()
                    + " cannot be scheduled due to a time conflict or incorrect datetime format.");
            System.out.println("Please set another time.");
        }
        printDivider();
    }

    // EFFECTS: displays a list of commands that can be used in the view scheduled
    // dates menu
    public void displayScheduledMenu() {
        System.out.println("Enter 'a' to view all scheduled activities");
        System.out.println("Enter 'd' to view all scheduled activities on a specific date");
        System.out.println("Enter 'q' to return to the main menu");
        printDivider();
    }

    // EFFECTS: processes the user's inputs for the view scheduled dates menu
    public void processScheduledMenuCommands(String scheduledInput) {
        printDivider();
        switch (scheduledInput) {
            case "a":
                viewAllScheduledActivities();
                break;
            case "d":
                viewScheduleByDateInput();
                break;
            case "q":
                System.out.println("Returning to the main menu...\n");
                System.out.println();
                handleMenu();
                break;
            default:
                System.out.println("Invalid option inputted. Please try again.");
        }
    }

    // EFFECTS: Returns a list of all scheduled activities
    public void viewAllScheduledActivities() {
        scheduledActivities.getScheduledActivities();
        if (scheduledActivities.getScheduledActivities().isEmpty()) {
            System.out.println("No scheduled activities. Please schedule an activity first.");
            return;
        }
        System.out.println("Scheduled Activities:");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (Activity activity : scheduledActivities.getScheduledActivities()) {
            System.out.println("- " + activity.getDateTime().format(formatter) + ": " + activity.getName());
        }
        printDivider();
    }

    // REQUIRES: valid year, month, and day inputs (e.g. year > 0, 1 <= month <= 12,
    // 1 <= day <= 31) and date must be valid (e.g. 2024-02-31 would not be valid)
    // EFFECTS: processes the user's date inputs to view scheduled activities by
    // date
    public void viewScheduleByDateInput() {
        System.out.println("Please enter the year (yyyy):");
        int yearInput = this.scanner.nextInt();
        this.scanner.nextLine();
        System.out.println("Please enter the month (mm):");
        int monthInput = this.scanner.nextInt();
        this.scanner.nextLine();
        System.out.println("Please enter the day (dd):");
        int dayInput = this.scanner.nextInt();
        this.scanner.nextLine();

        LocalDate dateInput = LocalDate.of(yearInput, monthInput, dayInput);

        viewScheduleByDate(dateInput);

    }

    // REQUIRES: dateInput must be a valid LocalDate object representing a valid
    // date
    // EFFECTS: Returns list of scheduled acitvities on a specified date
    public void viewScheduleByDate(LocalDate dateInput) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        DateItinerary filteredByDate = scheduledActivities.getActivitiesForDate(dateInput);
        if (filteredByDate.getScheduledActivities().isEmpty()) {
            System.out.println("No scheduled activities found on: " + dateInput);
        } else {
            printDivider();
            for (Activity scheduledActivity : filteredByDate.getScheduledActivities()) {
                System.out.println("- " + scheduledActivity.getDateTime().format(formatter)
                        + ": " + scheduledActivity.getName());
            }
        }
        printDivider();
    }

    // EFFECTS: saves the activityCollection to file
    public void saveActivityCollection() {
        try {
            jsonWriter.open();
            jsonWriter.write(activities);
            jsonWriter.close();
            System.out.println("Saved activities to " + JSON_STORE);
            System.out.println();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
            System.out.println();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads activityCollection from file
    public void loadActivityCollection() {
        try {
            ActivityCollection loadedActivities = jsonReader.read();
            this.activities = loadedActivities;
            for (Activity activity : activities.getActivities()) {
                if (activity.getScheduleStatus()) {
                    scheduledActivities.addActivity(activity);
                }
            }
            System.out.println("Loaded activities from " + JSON_STORE);
            System.out.println();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
            System.out.println();
        }
    }

    // MODIFIES: this
    // EFFECTS: prints a closing message and changes program status to not running
    public void quitApplication() {
        System.out.println("Thanks for using the Date Planner app!");
        System.out.println("Enjoy your date! :)");
        this.isProgramRunning = false;
    }

    // EFFECTS: returns error message if there are no activities to display
    public void checkEmptyActivities() {
        if (activities.getActivities().isEmpty()) {
            System.out.println("No activities found.");
            printDivider();
            handleMenu();
        }
    }

    // EFFECTS: prints out a divider in the form of a line of dashes
    public void printDivider() {
        System.out.println("----------------------------------------------------------------");
    }
}
