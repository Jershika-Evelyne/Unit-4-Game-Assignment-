import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;
import java.io.*;
import java.awt.Image;

public class EndPage extends JPanel {
    
    //images
    private Image lostBackground = new ImageIcon("lost.jpg").getImage();

    public EndPage(boolean won, int score, int timeUsed) {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("", SwingConstants.CENTER);
        JLabel message = new JLabel("", SwingConstants.CENTER); 

        JButton leaderboardBtn = new JButton("View Leaderboard");
        leaderboardBtn.addActionListener(e -> showLeaderboard());
        
        if (won) { //if Won == true
            JLabel apple = new JLabel(new ImageIcon("apple.png")); // import Image 
            apple.setMaximumSize(new Dimension(300, 400)); //set image size
            add(apple);
            
            setBackground(new Color(200, 255, 200)); // light green
            title.setText("YAY! YOU'RE SAFE!" );
            message.setText("You've kept the doctor away!");
        } else {
            JLabel lost = new JLabel(new ImageIcon("lost.jpg"));
            lost.setPreferredSize(new Dimension(80, 80));
            add(lost);
            setBackground(new Color(255, 200, 200)); // light red
            title.setText("OH NO!");
            message.setText("You failed to keep the doctor away.");
        }

        title.setFont(new Font("Arial", Font.BOLD, 28));
        message.setFont(new Font("Arial", Font.PLAIN, 18));

        JLabel scoreLabel = new JLabel("Final Score: " + score, SwingConstants.CENTER);
        JLabel timeLabel = new JLabel("Time Used: " + timeUsed + " seconds", SwingConstants.CENTER);

        //Adding all components to be displayed 
        add(title);
        add(message);
        add(scoreLabel);
        add(timeLabel);
        add(leaderboardBtn);
    }
    private void showLeaderboard() {
        JFrame frame = new JFrame("Leaderboard");
        JTextArea area = new JTextArea(15, 30);
        area.setEditable(false);
        try (BufferedReader br = new BufferedReader(new FileReader("userInfo.txt"))) {
        String line;
            while ((line = br.readLine()) != null)  { //read line by line
                String[] p = line.split(","); //splits words by comma
                area.append(
                  p[0] + " | Score: " + p[2] + " | Time: " + p[3] + "s\n" //p is indes. i.e p[0] is username
                );
            }
        } catch (IOException e) {
            area.setText("Error loading leaderboard.");
        }
        frame.add(new JScrollPane(area));
        frame.pack(); //sizes window to fit contents
        frame.setLocationRelativeTo(null); //centers window
        frame.setVisible(true); //makes the thing actually appear
    }
}
