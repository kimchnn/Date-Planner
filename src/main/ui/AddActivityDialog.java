package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// represents a dialog window for adding a new activity

public class AddActivityDialog extends JDialog implements ActionListener {
    private JTextField nameField;
    private JTextField categoryField;
    private JTextField locationField;
    private JButton addButton;
    private JButton cancelButton;
    private String name;
    private String category;
    private String location;
    private boolean isAddButtonPressed;

    // EFFECTS: Creates a new AddActivityDialog instance with the given parent
    // frame, initializes the dialog layout and components
    public AddActivityDialog(Frame parent) {
        super(parent, "Add New Activity", true);
        setLayout(new GridLayout(4, 2));

        nameField = new JTextField(10);
        categoryField = new JTextField(10);
        locationField = new JTextField(10);
        addButton = new JButton("Add");
        cancelButton = new JButton("Cancel");

        add(new JLabel("Name: "));
        add(nameField);
        add(new JLabel("Category: "));
        add(categoryField);
        add(new JLabel("Location: "));
        add(locationField);
        add(cancelButton);
        add(addButton);

        cancelButton.addActionListener(this);
        addButton.addActionListener(this);

        pack();
        setLocationRelativeTo(parent);
    }

    // MODIFIES: this
    // EFFECTS: Handles button click events for add and cancel buttons
    // If add button is clicked, stores user input and closes the dialog
    // If cancel button is clicked, closes the dialog
    // Displays error message if any input fields are empty
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        name = nameField.getText().trim();
        category = categoryField.getText().trim();
        location = locationField.getText().trim();

        if (source == cancelButton) {
            dispose();

        } else if (name.isEmpty() || category.isEmpty() || location.isEmpty()) {
            JOptionPane.showMessageDialog(this, "New activity could not be added. All fields must be filled out.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        } else if (source == addButton) {
            isAddButtonPressed = true;
            dispose();
        }
    }

    // EFFECTS: Returns the name entered by the user
    public String getActivityName() {
        return name;
    }

    // EFFECTS: Returns the category entered by the user
    public String getActivityCategory() {
        return category;
    }

    // EFFECTS: Returns the location entered by the user
    public String getActivityLocation() {
        return location;
    }

    // EFFECTS: Returns true if the Add button was pressed, false otherwise
    public boolean isAddButtonPressed() {
        return isAddButtonPressed;
    }
}