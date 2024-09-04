package ui.pages;

import javax.swing.*;
import java.awt.*;

// A blank page with set dimensions
public abstract class Page extends JFrame {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    // EFFECTS: creates a blank page of WIDTH and HEIGHT dimensions
    public Page() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    // MODIFIES: this
    // EFFECTS: constructs a JPanel and adds it to the JFrame
    public void initializePanel(JPanel panel) {
        panel.setBounds(0, 0, WIDTH, HEIGHT);
        panel.setLayout(new BorderLayout());

        initializePanelComponents();

        this.add(panel);
    }

    // EFFECTS: initializes components to be added to the panel
    public abstract void initializePanelComponents();

}
