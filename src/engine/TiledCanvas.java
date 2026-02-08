package engine;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import physics.Player;
import physics.Wall;

import java.util.ArrayList;
import java.util.List;

public class TiledCanvas extends Application {
    private static final int TILE_SIZE = 50; // Size of each tile
    private static final int GRID_WIDTH = 20; // Number of tiles horizontally
    private static final int GRID_HEIGHT = 20; // Number of tiles vertically

    private final List<Wall> walls = new ArrayList<>(); // List to store Wall instances
    private final List<Player> players = new ArrayList<>();

    private boolean wallSelected = true;

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(GRID_WIDTH * TILE_SIZE, GRID_HEIGHT * TILE_SIZE);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Draw the grid
        drawGrid(gc);

        // Handle mouse clicks
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(wallSelected == true)
            {
                int col = (int) (event.getX() / TILE_SIZE);
                int row = (int) (event.getY() / TILE_SIZE);

                // Add a Wall instance at the clicked tile
                addWall(gc, col, row);
            }
            else { //Add player tile.
                int col = (int) (event.getX() / TILE_SIZE);
                int row = (int) (event.getY() / TILE_SIZE);

                addPlayer(gc, col, row);
            }
        });

        Button b = new Button("Save Map");
        Button switchButton = new Button("Switch to Player Tile");

        Label label1 = new Label("Map Name:");
        TextField textField = new TextField ();
        HBox hb = new HBox(10); // Horizontal box with spacing of 10
        hb.getChildren().addAll(label1, textField, b, switchButton);

        BorderPane root = new BorderPane();
        root.setCenter(canvas);
        root.setTop(hb);

        Scene scene = new Scene(root);

        primaryStage.setTitle("Tiled Canvas");
        primaryStage.setScene(scene);
        primaryStage.show();

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                MapSaver saver = new MapSaver();
                String mapName = textField.getText().trim();
                try {
                    saver.saveMap(walls, players, "localMaps/" + mapName + ".json"); // Save the map to a file
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        b.setOnAction(event);

        EventHandler<ActionEvent> buttonSwitchEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                wallSelected = switchButton(wallSelected, switchButton);
            }
        };

        switchButton.setOnAction(buttonSwitchEvent);
    }

    public void drawGrid(GraphicsContext gc) {
        gc.setStroke(Color.GRAY);
        for (int i = 0; i <= GRID_WIDTH; i++) {
            gc.strokeLine(i * TILE_SIZE, 0, i * TILE_SIZE, GRID_HEIGHT * TILE_SIZE);
        }
        for (int i = 0; i <= GRID_HEIGHT; i++) {
            gc.strokeLine(0, i * TILE_SIZE, GRID_WIDTH * TILE_SIZE, i * TILE_SIZE);
        }
    }

    private void addWall(GraphicsContext gc, int col, int row) {
        // Create a Wall instance and add it to the list
        Wall wall = new Wall(Color.GRAY, col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        walls.add(wall);

        // Redraw the canvas
        render(gc);
    }

    private void addPlayer(GraphicsContext gc, int col, int row) {
        Player player = new Player(col * TILE_SIZE, row * TILE_SIZE);
        players.add(player);

        // Redraw the canvas
        render(gc);
    }

    private boolean switchButton(boolean wallSelected, Button switchButton)
    {
        if(wallSelected)
        {
            wallSelected = false;
            switchButton.setText("Switch to Wall Tile");
        }
        else
        {
            wallSelected = true;
            switchButton.setText("Switch to Player Tile");
        }

        return wallSelected;
    }

    private void render(GraphicsContext gc) {
        // Clear the canvas
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, GRID_WIDTH * TILE_SIZE, GRID_HEIGHT * TILE_SIZE);

        // Redraw the grid
        drawGrid(gc);

        // Draw all walls
        for (Wall wall : walls) {
            gc.setFill(wall.color);
            gc.fillRect(wall.x, wall.y, TILE_SIZE, TILE_SIZE);
        }
        for(Player player : players)
        {
            gc.setFill(Color.BLUE);
            gc.fillRect(player.x, player.y, TILE_SIZE, TILE_SIZE);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}