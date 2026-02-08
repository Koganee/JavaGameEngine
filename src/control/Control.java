package control;

import engine.GameCanvas;
import javafx.scene.Scene;
import physics.Player;
import physics.Wall;

import java.util.List;

public class Control{
    private boolean up, down, left, right;
    private Player player;
    private GameCanvas canvas;

    private boolean canMove = true;

    public Control(GameCanvas playerCanvas, Player player, Scene scene) {
        this.player = player;
        this.canvas = playerCanvas;

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case W -> up = true;
                case S -> down = true;
                case A -> left = true;
                case D -> right = true;
            }
        });

        scene.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case W -> up = false;
                case S -> down = false;
                case A -> left = false;
                case D -> right = false;
            }
            canMove = true;
        });

        scene.setOnMouseClicked(e -> {
            if(e.getButton().name().equals("PRIMARY"))
            {
                player.setPosition(e.getX(), e.getY());
            }
        });
    }

    public void update(Player player) {
        if (!canMove) return;

        if (up) {
            movePlayer(player, 0, -50);
            canMove = false;
        } else if (down) {
            movePlayer(player, 0, 50);
            canMove = false;
        } else if (left) {
            movePlayer(player, -50, 0);
            canMove = false;
        } else if (right) {
            movePlayer(player, 50, 0);
            canMove = false;
        }
    }


    //canvas.movePlayer(0, -50);

    public void movePlayer(Player activePlayer, int dx, int dy) {
        int TILE_SIZE = 50;
        int GRID_WIDTH = 20;
        int GRID_HEIGHT = 20;

        if (activePlayer == null) return;

        double newX = activePlayer.x + dx;
        double newY = activePlayer.y + dy;

        // bounds check
        if (newX < 0 || newY < 0 ||
                newX >= GRID_WIDTH * TILE_SIZE ||
                newY >= GRID_HEIGHT * TILE_SIZE) {
            return;
        }

        List<Wall> walls = canvas.getWalls();

        // wall collision
        for (Wall w : walls) {
            if (w.x == newX && w.y == newY) {
                return;
            }
        }

        activePlayer.x = newX;
        activePlayer.y = newY;
        canvas.render();
    }
}
