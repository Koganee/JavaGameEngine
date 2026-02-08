package engine;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameLoop extends Application {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private double x = 100; // Example object position
    private double y = 100;
    private double vx = 100; // Velocity in pixels per second
    private double vy = 100;

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        AnimationTimer gameLoop = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }

                // Calculate delta time (in seconds)
                double deltaTime = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;

                // Update game state
                update(deltaTime);

                // Render the scene
                render(gc);
            }
        };

        gameLoop.start();

        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root, WIDTH, HEIGHT, Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Game Loop Example");
        primaryStage.show();
    }

    private void update(double dt) {
        // Update object position
        x += vx * dt;
        y += vy * dt;

        // Bounce off walls
        if (x < 0 || x > WIDTH) vx = -vx;
        if (y < 0 || y > HEIGHT) vy = -vy;
    }

    private void render(GraphicsContext gc) {
        // Clear the canvas
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        // Draw the object
        gc.setFill(Color.RED);
        gc.fillOval(x, y, 20, 20);
    }

    public static void main(String[] args) {
        launch(args);
    }
}