import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;

class Apple extends GameObject {
    private int speedY;

    public Apple(int gameWidth, Random random) {
        // Apples are 20x20
        super(random.nextInt(gameWidth - 20), 0, 20, 20, Color.RED); 
        // Random falling speed (different speeds as requested)
        this.speedY = random.nextInt(5) + 2; 
    }

    @Override
    public void move() {
        // Only moves down
        y += speedY;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, width, height);
        // Draw a small green stem
        g.setColor(Color.GREEN.darker());
        g.fillRect(x + width / 2 - 1, y, 2, 5);
    }
}