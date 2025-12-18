import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.*;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Image;


public class LoginPage extends JPanel {

    private JTextField userField = new JTextField(10);
    private JPasswordField passField = new JPasswordField(10);
    private JLabel status = new JLabel("<html> Enough Apples a Day:<br> will keep the Doctor Away<br>Login or Register</html>");    private JLabel status2 = new JLabel("Welcome!");
    private CardLayout card = new CardLayout();
    private static String currentUser;
    private Image loginBackground = new ImageIcon("login.jpg").getImage();


    public LoginPage() {

        this.setLayout(card);
        this.setPreferredSize(new Dimension(400, 400));
        status.setBackground(new Color(0, 0, 0, 150)); // Semi-transparent black

        JPanel login_page = new JPanel();
        status.setOpaque(true);     
        login_page.setOpaque(false);
        login_page.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 5, 5);

        JPanel instruction_page = new JPanel();
        instruction_page.setLayout(new GridBagLayout());
        GridBagConstraints igbc = new GridBagConstraints();
        igbc.gridx = 0;
        igbc.insets = new Insets(5, 5, 5, 5);

        igbc.gridy = 0;

        // Welcome label
        instruction_page.add(status2, igbc);

        // Instructions
        igbc.gridy++;
        instruction_page.add(new JLabel("HOW TO PLAY"), igbc);

        igbc.gridy++;
        instruction_page.add(new JLabel("Use LEFT and RIGHT arrow keys to move the basket."), igbc);

        igbc.gridy++;
        instruction_page.add(new JLabel("Red apples give you +1 point."), igbc);

        igbc.gridy++;
        instruction_page.add(new JLabel("Green apples subtract -1 point."), igbc);

        igbc.gridy++;
        instruction_page.add(new JLabel("You have 30 seconds to reach the target score(10)."), igbc);

        igbc.gridy++;
        instruction_page.add(new JLabel("Catch enough apples to keep the doctor away!"), igbc);

        this.add(login_page, "login");
        this.add(instruction_page, "instruction");

        igbc.gridy++;

        JPanel instructionButtons = new JPanel();
        ImageIcon appleIcon = new ImageIcon("apple.png");
        JLabel appleLabel = new JLabel(appleIcon);

        igbc.gridy++;
        instruction_page.add(appleLabel, igbc);

        JButton startGameBtn = new JButton("Insert Coin to Start Game");
        JButton logoutBtn = new JButton("Log Out");

        instructionButtons.add(startGameBtn);
        instructionButtons.add(logoutBtn);

        instruction_page.add(instructionButtons, igbc);

        startGameBtn.addActionListener(e -> {
            // Open the game window
            new AppleGame();
            JFrame window = (JFrame) SwingUtilities.getWindowAncestor(LoginPage.this);
            window.dispose();
        });

        logoutBtn.addActionListener(e -> {
            card.show(this, "login");
            status.setText("Login or Register");
        });

        try {
            File userFile = new File("userInfo.txt");
            userFile.createNewFile();
        } catch (IOException e) {
            System.out.println("Could not create user file.");
        }

        status.setForeground(Color.white);
        login_page.add(status, gbc);

        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy++;

        JPanel login_section = new JPanel();
        login_section.add(new JLabel("Username:"));
        login_section.add(userField);
        login_page.add(login_section, gbc);

        JPanel passW = new JPanel();
        passW.add(new JLabel("Password:"));
        passW.add(passField);
        gbc.gridy++;
        login_page.add(passW, gbc);

        JPanel button_panel = new JPanel();
        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register");
        button_panel.add(registerBtn);
        button_panel.add(loginBtn);

        gbc.gridy++;
        login_page.add(button_panel, gbc);
        gbc.fill = GridBagConstraints.NONE;

        loginBtn.addActionListener(e -> {
            String user = userField.getText().trim();
            String pass = new String(passField.getPassword());
            if (user.isEmpty() || pass.isEmpty()) {
                status.setText("Fields cannot be empty.");
            } else {
                validateLogin(user, pass);
                userField.setText("");
                passField.setText("");
            }
        });

        registerBtn.addActionListener(e -> {
            String user = userField.getText().trim();
            String pass = new String(passField.getPassword());

            if (user.isEmpty() || pass.isEmpty()) {
                status.setText("Fields cannot be empty.");
            } else if (usernameExists(user)) {
                status.setText("Username already taken, try a different name.");
            } else {
                saveUser(user, pass, 0, 999);
                status.setText("User " + user + " registered!");
            }
        });

        SwingUtilities.invokeLater(() -> {
            card.show(this, "login");
        });
    }

    private boolean validateLogin(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("userInfo.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[0].equals(username) && parts[1].equals(password)) {                    currentUser = username;
                    card.show(this, "instruction");
                    status2.setText("Welcome " + username + "!!");
                    return true;
                }
            }
            status.setText("Username or password does not exist.");
        } catch (IOException e) {
            status.setText("Error reading user file.");
        }
        return false;
    }

    private boolean usernameExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("userInfo.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length >= 1 && parts[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading users file.");
        }
        return false;
    }

    //Save User
    private void saveUser(String username, String password, int bestScore, int bestTime) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("userInfo.txt", true))) {
            writer.write(username + "," + password + ",0,999");
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to users file.");
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(loginBackground, 0, 0, getWidth(), getHeight(), this);
    }

    //Update player's best score for leaderbo
    public static void updateBestScore(int newScore, int newTime) {
        File input = new File("userInfo.txt");
        File temp = new File("temp.txt");

        try (
            BufferedReader reader = new BufferedReader(new FileReader(input));
            BufferedWriter writer = new BufferedWriter(new FileWriter(temp))
        ) {
            String line;
    
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
    
                // SAFETY: upgrade old records
                if (parts.length < 4) {
                    parts = new String[]{parts[0], parts[1], "0", "999"};
                }
    
                if (parts[0].equals(currentUser)) {
                    int bestScore = Integer.parseInt(parts[2]);
                    int bestTime = Integer.parseInt(parts[3]);

                    boolean better =
                        newScore > bestScore ||
                        (newScore == bestScore && newTime < bestTime);

                    if (better) {
                        parts[2] = String.valueOf(newScore);
                        parts[3] = String.valueOf(newTime);
                    }
                }

                writer.write(String.join(",", parts));
                writer.newLine();
            }
        } catch (IOException e) {
        e.printStackTrace();
        }

        input.delete();
        temp.renameTo(input);
    }
}