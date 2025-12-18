import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;


/**
 * CollectTheApples.java
 *
 * Simple Java Swing game: a basket at the bottom moves left/right with arrow keys
 * and catches falling apples. Apples fall at different speeds and spawn randomly.
 *
 * How to run:
 * 1. Save this file as CollectTheApples.java
 * 2. Compile: javac CollectTheApples.java
 * 3. Run: java CollectTheApples
 */
public class AppleGame extends JFrame {
    
   public AppleGame() {
        setTitle("Collect the Apple Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GamePanel gamePanel = new GamePanel();
        add(gamePanel);

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        gamePanel.requestFocusInWindow(); // important for keys
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new LoginPage());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}