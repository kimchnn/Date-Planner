package ui;

import javax.swing.*;

import ui.pages.LaunchPage;

// the Main class serves as the entry point for the Date Planner Application

public class MainGUI {

    // EFFECTS: runs the Date Planner application (graphical console)
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LaunchPage();
        });
    }
}