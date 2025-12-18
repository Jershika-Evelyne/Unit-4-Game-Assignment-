import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;

abstract class GameObject {
    protected int x, y;
    protected int width, height;
    protected Color color;

    public GameObject(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    // Abstract methods must be implemented by subclasses
    public abstract void move();
    public abstract void draw(Graphics g);

    /**
     * Checks for bounding box collision (used for Apple-Basket interaction).
     */
    public boolean isColliding(GameObject other) {
        Rectangle thisBounds = new Rectangle(x, y, width, height);
        Rectangle otherBounds = new Rectangle(other.x, other.y, other.width, other.height);
        return thisBounds.intersects(otherBounds);
    }
}