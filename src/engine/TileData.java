package engine;

public class TileData {
    public double x, y, width, height;
    public String color;

    public TileData() {} // Default constructor for deserialization

    public TileData(double x, double y, double width, double height, String color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }
}