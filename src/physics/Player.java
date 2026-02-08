package physics;

import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;

public class Player {
    public double x, y; //Player position.
    public double vx, vy; //Player velocity.

    public double width = 100;
    public double height = 100;

    public double speed = 200; // pixels per second
    public Color color = Color.BLUE;

    public Player(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void update(double dt) {
        x += vx * dt;
        y += vy * dt;
    }

    public Rectangle2D getBounds() {
        return new Rectangle2D(x, y, width, height);
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX(Player player)
    {
        return player.x;
    }
    public double getY(Player player)
    {
        return player.y;
    }
}
