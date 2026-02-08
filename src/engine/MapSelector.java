package engine;

import com.fasterxml.jackson.databind.ObjectMapper;
import control.Control;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import physics.Player;
import physics.Wall;
import java.io.File;

public class MapSelector {

    private static final int TILE_SIZE = 50;

    private String mapName;

    // Constructor receives map name
    public MapSelector(String mapName) {
        this.mapName = mapName;
    }

    public void show(Stage stage) {
        GameCanvas canvas = new GameCanvas();

        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root);

        Control playerControl = new Control(canvas, canvas.activePlayer, scene);

        stage.setScene(scene);
        stage.setTitle("Map Viewer");
        stage.setMaximized(true);
        stage.show();

        try {
            loadMap("localMaps/" + mapName + ".json", canvas);
        } catch (Exception e) {
            e.printStackTrace();
        }

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                playerControl.update(canvas.activePlayer);
                canvas.render();
            }
        };

        timer.start();

    }

    public static void loadMap(String filePath, GameCanvas canvas) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TileData[] tileDataArray = mapper.readValue(new File(filePath), TileData[].class);

        for (TileData tileData : tileDataArray) {
            if(tileData.color.equals("0x808080ff")) {
                Wall wall = new Wall(javafx.scene.paint.Color.GRAY, tileData.x, tileData.y, TILE_SIZE, TILE_SIZE);
                canvas.addWall(wall);
            }
            if(tileData.color.equals("0x0000ffff")) {
                Player player = new Player(tileData.x, tileData.y);
                canvas.addPlayer(player);
            }
        }
    }
}
