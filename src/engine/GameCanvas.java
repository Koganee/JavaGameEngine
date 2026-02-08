package engine;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import physics.Player;
import physics.Wall;
import java.util.ArrayList;
import java.util.List;

public class GameCanvas extends Canvas {

    private static final int TILE_SIZE = 50;
    private static final int GRID_WIDTH = 20;
    private static final int GRID_HEIGHT = 20;

    private final List<Wall> walls = new ArrayList<>();
    private final List<Player> players = new ArrayList<>();

    public Player activePlayer;

    public GameCanvas() {
        super(GRID_WIDTH * TILE_SIZE, GRID_HEIGHT * TILE_SIZE);
        drawGrid();
    }

    public void drawGrid() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setStroke(Color.GRAY);
        for (int i = 0; i <= GRID_WIDTH; i++)
            gc.strokeLine(i * TILE_SIZE, 0, i * TILE_SIZE, GRID_HEIGHT * TILE_SIZE);
        for (int i = 0; i <= GRID_HEIGHT; i++)
            gc.strokeLine(0, i * TILE_SIZE, GRID_WIDTH * TILE_SIZE, i * TILE_SIZE);
    }

    public void addWall(Wall wall) {
        walls.add(wall);
        render();
    }

    public void addPlayer(Player player) {
        players.add(player);
        activePlayer = player; // control the last-added player
        render();
    }


    public void render() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, getWidth(), getHeight());
        drawGrid();

        for (Wall w : walls) {
            gc.setFill(w.color);
            gc.fillRect(w.x, w.y, TILE_SIZE, TILE_SIZE);
        }
        for (Player p : players) {
            gc.setFill(Color.BLUE);
            gc.fillRect(p.x, p.y, TILE_SIZE, TILE_SIZE);
        }
    }

    public List<Wall> getWalls() {
        return walls;
    }

}
