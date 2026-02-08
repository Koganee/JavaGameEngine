package physics;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Wall {
    private Rectangle view; // Visual representation of the wall
    public static Color color = Color.GRAY; // Wall color
    public double x, y; // Wall position
    public double vx, vy; // Wall velocity
    public double width, height; // Wall dimensions

    public Wall(Color color, double x, double y, double width, double height) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.vx = 0;
        this.vy = 0;
        this.width = 100;
        this.height = 100;

        // Create the visual representation
        this.view = new Rectangle(width, height, color);
        this.view.setTranslateX(x);
        this.view.setTranslateY(y);
    }

    public void update(double dt) {
        // Update position based on velocity
        x += vx * dt;
        y += vy * dt;

        // Update the visual representation
        view.setTranslateX(x);
        view.setTranslateY(y);
    }

    public Bounds getBounds() {
        return view.getBoundsInParent();
    }

    public Rectangle getView() {
        return view;
    }

    public void setVelocity(double vx, double vy) {
        this.vx = vx;
        this.vy = vy;
    }

    public void stopMovement() {
        this.vx = 0;
        this.vy = 0;
    }
}