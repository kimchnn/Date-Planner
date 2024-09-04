package ui;

import java.io.FileNotFoundException;

// the Main class serves as the entry point for the Date Planner Application

public class Main {

    // EFFECTS: runs the Date Planner application
    public static void main(String[] args) {
        try {
            new DatePlannerUI();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}