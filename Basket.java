import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;

class Basket extends GameObject {
    public Basket(int x, int y, int width, int height) {
        super(x, y, width, height, new Color(139, 69, 19)); //Brown
    }

    // Basket movement is handled by the KeyListener in GamePanel, so move() is empty
    @Override
    public void move() { 
        // No autonomous movement
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }
    
    // Public setters for external control (KeyListener/MouseListener)
    public void setX(int x) { this.x = x; }
    public void setColor(Color color) { this.color = color; }
}
