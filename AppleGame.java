import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;

//Main Class (Run This)
public class AppleGame extends JFrame {
    
   public AppleGame() {
        setTitle("Collect the Apple Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create game panel (consists of the game itself)
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
        frame.setContentPane(new LoginPage()); //Login/Register Page
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
