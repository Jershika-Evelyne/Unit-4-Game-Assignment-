import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;

class GamePanel extends JPanel implements ActionListener, KeyListener, MouseListener {
    
    //Unchangeable game stuff
    private final int GAME_WIDTH = 400;
    private final int GAME_HEIGHT = 600;
    private final int BASKET_SPEED = 15;
    private final int DELAY = 20; 
    private final int APPLE_SPAWN_RATE = 40; 
    private final Color BASKET_DEFAULT_COLOR = new Color(139, 69, 19);
    
    //Game Variables
    private Basket basket;
    private ArrayList<GameObject> apples = new ArrayList<>();
    private JButton startButton; 
    private Color flashColor = BASKET_DEFAULT_COLOR; // Color for collision feedback

    //Game Stats
    private int score = 0;
    private Timer timer;
    private Random random = new Random();
    private boolean isRunning = false;
    private int timeLeft = 30;     // seconds
    private int tickCount = 0;    // counts timer ticks
    
    public GamePanel() {
        setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        setBackground(new Color(135, 206, 235)); 
        setLayout(null); 

        // Initialize Basket 
        int initialBasketX = GAME_WIDTH / 2 - 80 / 2;
        int basketY = GAME_HEIGHT - 40 - 10;
        basket = new Basket(initialBasketX, basketY, 80, 40);

        // Setup Button 
        startButton = new JButton("Ready?");
        startButton.setBounds(GAME_WIDTH / 2 - 100, GAME_HEIGHT / 2 - 25, 200, 50);
        startButton.addActionListener(e -> startGame());
        add(startButton);
        
        addKeyListener(this);
        addMouseListener(this); 
        
        timer = new Timer(DELAY, this);
    }
    
    private void startGame() {
        if (!isRunning) {
            score = 0;
            apples.clear();
            basket.setX(GAME_WIDTH / 2 - basket.width / 2); // Reset position
            startButton.setVisible(false);
            isRunning = true;
            timer.start();
        }
    }
    
    private void stopGame() {
        isRunning = false;
        timer.stop();
        startButton.setText("Restart Game (Final Score: " + score + ")"); //Note: this button was from AI, and due to lack of time and higher priorities --> the restart button wasn't worked on to be made to work
        startButton.setVisible(true);
    }

    //Timer O[perations]
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isRunning) {
            updateGame();
            repaint(); 
        }
    }

    private void updateGame() {
        // Reset flash color
        flashColor = BASKET_DEFAULT_COLOR;
        
        Iterator<GameObject> iterator = apples.iterator();
        
        tickCount++;

        if (tickCount == 50) {   // 50 × 20ms = 1 second
            timeLeft--;
            tickCount = 0;
        }

        if (timeLeft <= 0) {
            endGame();
        }
        
        if (score >= 10) {
            endGame();
        }

        while (iterator.hasNext()) {
            GameObject apple = iterator.next();
            apple.move(); // Move the Apple down
   
            // Collision Detection
            if (apple.isColliding(basket)) {
                iterator.remove(); 
                if (apple instanceof Apple) {
                    score += 1;          // Red apple
                    flashColor = Color.GREEN;
                }
                else if (apple instanceof GApple) {
                    score -= 1;          // Green apple
                    flashColor = Color.RED;
                }
                
            } 
            
            
            else if (apple.y > GAME_HEIGHT) { //Note: From AI. not edited upon due to lack of time and higher priorities
                iterator.remove(); 
                // Simple Game Over condition on a miss 
                // stopGame(); 
            }
        }
        
        // Spawn New Apples
        if (random.nextInt(APPLE_SPAWN_RATE) == 0) {
            if (random.nextBoolean()) { //Gives a 50/50 chance
                apples.add(new Apple(GAME_WIDTH, random));   // red apple
            } else {
                apples.add(new GApple(GAME_WIDTH, random));  // green apple
            }
        }
        
    }
    
    // Graphics
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Draw Basket, using the flash color if a collision just happened
        basket.setColor(flashColor);
        basket.draw(g2d);
        
        // Draw Apples
        for (GameObject apple : apples) {
            apple.draw(g2d);
        }
        
        // Draw Score
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        g2d.drawString("Score: " + score, 10, 30);
    }
    
    // KeyListener Implementation 
    @Override
    public void keyPressed(KeyEvent e) {
        if (!isRunning) return; // Ignore input if game is not running
        
        int key = e.getKeyCode();
        int newX = basket.x;

        if (key == KeyEvent.VK_LEFT) {
            newX = basket.x - BASKET_SPEED;
        }

        if (key == KeyEvent.VK_RIGHT) {
            newX = basket.x + BASKET_SPEED;
        }
        
        // Apply new position, stops it to the screen boundaries
        newX = Math.max(0, newX);
        newX = Math.min(GAME_WIDTH - basket.width, newX);
        basket.setX(newX);
    }

    //Conclusion Pages
    private void endGame() {
        timer.stop();
        isRunning = false;

        boolean won = score >= 10;
        int timeUsed = 30 - timeLeft;
        
        LoginPage.updateBestScore(score, timeUsed);
        
        JFrame window = (JFrame) SwingUtilities.getWindowAncestor(this);
        window.setContentPane(new EndPage(won, score, timeUsed)); //EndPage located under Login Page
        window.revalidate();
    }

    // MouseListener Implementation (Interactive Element 2) 
    @Override
    public void mouseClicked(MouseEvent e) {
        // Change basket color on any mouse click
        if (isRunning) {
            int r = random.nextInt(256);
            int g = random.nextInt(256);
            int b = random.nextInt(256);
            basket.setColor(new Color(r, g, b)); 
            flashColor = basket.color; // Update the flash color base
        }
        // Restore keyboard focus after a mouse click, which often steals it
        requestFocusInWindow(); 
    }
    
    // Unused KeyListener/MouseListener methods
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
