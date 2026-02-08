package engine;

import physics.Player;
import physics.Wall;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapSaver {
    private static final int TILE_SIZE = 50;

    public static void saveMap(List<Wall> walls, List<Player> players, String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<TileData> tileDataList = new ArrayList<>();

        // Convert Wall objects to TileData
        for (Wall wall : walls) {
            tileDataList.add(new TileData(wall.x, wall.y, TILE_SIZE, TILE_SIZE, wall.color.toString()));
        }
        for(Player player : players)
        {
            tileDataList.add(new TileData(player.x, player.y, TILE_SIZE, TILE_SIZE, "0x0000ffff"));
        }

        // Write to file
        mapper.writeValue(new File(filePath), tileDataList);
    }
}
