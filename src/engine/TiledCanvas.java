package engine;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import physics.Player;
import physics.Wall;

import java.util.ArrayList;
import java.util.List;

public class TiledCanvas extends Application {

    private static final int TILE_SIZE = 50;
    private static final int GRID_WIDTH = 20;
    private static final int GRID_HEIGHT = 20;

    private final List<Wall> walls = new ArrayList<>();
    private final List<Player> players = new ArrayList<>();

    private boolean wallSelected = true;

    private final Color canvasBackground = Color.web("#1e1e1e"); // Dark background
    private final Color gridColor = Color.web("#555555");
    private final Color wallColor = Color.web("0x808080ff");
    private final Color playerColor = Color.web("0x0000ffff");

    @Override
    public void start(Stage primaryStage) {

        // --- Canvas setup ---
        Canvas canvas = new Canvas(GRID_WIDTH * TILE_SIZE, GRID_HEIGHT * TILE_SIZE);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        render(gc);

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            int col = (int) (event.getX() / TILE_SIZE);
            int row = (int) (event.getY() / TILE_SIZE);

            if (wallSelected) addWall(gc, col, row);
            else addPlayer(gc, col, row);
        });

        // --- Left Panel (Controls) ---
        Label mapLabel = new Label("Map Name:");
        TextField mapNameField = new TextField();

        Button saveButton = new Button("Save Map");

        Label tileLabel = new Label("Select Tile:");

        Button playerButton = new Button("Player Tile");
        Button wallButton = new Button("Wall Tile");

        VBox leftPanel = new VBox(15, mapLabel, mapNameField, saveButton, tileLabel,
                playerButton, wallButton);
        leftPanel.setStyle("-fx-background-color: #2b2b2b; -fx-padding: 15;");
        leftPanel.setPrefWidth(200);

        // Dark theme styling for controls
        for (javafx.scene.Node node : leftPanel.getChildren()) {
            if (node instanceof Button b) b.setStyle("-fx-background-color: #3c3f41; -fx-text-fill: #f0f0f0;");
            if (node instanceof Label l) l.setTextFill(Color.web("#f0f0f0"));
            if (node instanceof TextField tf) tf.setStyle("-fx-background-color: #3c3f41; -fx-text-fill: #f0f0f0;");
        }

        // --- Right Panel (Tilemap Canvas) ---
        StackPane rightPanel = new StackPane(canvas);
        rightPanel.setStyle("-fx-background-color: #1e1e1e;");

        VBox rightSidePanel = new VBox(15);
        rightSidePanel.setStyle("-fx-background-color: #2b2b2b; -fx-padding: 15;");

        // --- Main Layout ---
        HBox root = new HBox(leftPanel, rightPanel, rightSidePanel);
        root.setHgrow(rightSidePanel, Priority.ALWAYS); //rightSidePanel fills remaining space in editor.

        Scene scene = new Scene(root);

        primaryStage.setTitle("Tiled Canvas - Dark Theme");
        primaryStage.setScene(scene);
        primaryStage.show();

        // --- Button Actions ---
        saveButton.setOnAction(e -> {
            String mapName = mapNameField.getText().trim();
            try {
                MapSaver saver = new MapSaver();
                saver.saveMap(walls, players, "localMaps/" + mapName + ".json");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        //Left Panel Tile Buttons.
        wallButton.setOnAction(e -> {
            wallSelected = true;
        });
        playerButton.setOnAction(e -> {
            wallSelected = false;
        });
    }

    // Draw the grid
    private void drawGrid(GraphicsContext gc) {
        gc.setStroke(gridColor);
        for (int i = 0; i <= GRID_WIDTH; i++)
            gc.strokeLine(i * TILE_SIZE, 0, i * TILE_SIZE, GRID_HEIGHT * TILE_SIZE);
        for (int i = 0; i <= GRID_HEIGHT; i++)
            gc.strokeLine(0, i * TILE_SIZE, GRID_WIDTH * TILE_SIZE, i * TILE_SIZE);
    }

    private void addWall(GraphicsContext gc, int col, int row) {
        walls.add(new Wall(wallColor, col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE));
        render(gc);
    }

    private void addPlayer(GraphicsContext gc, int col, int row) {
        players.add(new Player(col * TILE_SIZE, row * TILE_SIZE));
        render(gc);
    }

    private void render(GraphicsContext gc) {
        // Clear canvas
        gc.setFill(canvasBackground);
        gc.fillRect(0, 0, GRID_WIDTH * TILE_SIZE, GRID_HEIGHT * TILE_SIZE);

        // Draw grid
        drawGrid(gc);

        // Draw walls
        for (Wall wall : walls) {
            gc.setFill(wall.color);
            gc.fillRect(wall.x, wall.y, TILE_SIZE, TILE_SIZE);
        }

        // Draw players
        for (Player player : players) {
            gc.setFill(playerColor);
            gc.fillRect(player.x, player.y, TILE_SIZE, TILE_SIZE);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
