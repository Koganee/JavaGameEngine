import engine.MapSelector;
import engine.TiledCanvas;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        // ===== ROOT LAYOUT =====
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 600, 600, Color.BLACK);

        // ===== TITLE =====
        Text title = new Text("Welcome to my Java Game Engine!");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 22));

        // ===== BUTTONS =====
        Button newGameBtn = new Button("Create A New Game");
        newGameBtn.setPrefWidth(200);

        Button loadGameBtn = new Button("Load Game");

        // ===== MAP INPUT ROW =====
        Label mapLabel = new Label("Map Name:");
        TextField mapField = new TextField();
        mapField.setPromptText("Enter map name...");

        HBox mapRow = new HBox(10, mapLabel, mapField, loadGameBtn);
        mapRow.setAlignment(Pos.CENTER);

        // ===== MENU COLUMN =====
        VBox menu = new VBox(20, title, newGameBtn, mapRow);
        menu.setAlignment(Pos.CENTER);

        root.setCenter(menu);

        // ===== EVENTS =====
        newGameBtn.setOnAction(e -> {
            TiledCanvas tiledCanvas = new TiledCanvas();
            try {
                tiledCanvas.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        loadGameBtn.setOnAction(e -> {
            String mapName = mapField.getText();
            if(mapName.isEmpty()) {
                System.out.println("Enter a map name!");
                return;
            }
            MapSelector mapSelector = new MapSelector(mapName);
            mapSelector.show(primaryStage);
        });

        //Styling-----
        newGameBtn.setStyle("-fx-background-color: #3c3f41; -fx-text-fill: #f0f0f0;");
        loadGameBtn.setStyle("-fx-background-color: #3c3f41; -fx-text-fill: #f0f0f0;");
        mapLabel.setTextFill(Color.web("#f0f0f0"));
        mapField.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000;");
        title.setFill(Color.web("#ffffff"));
        root.setStyle("-fx-background-color: #3c3f41; -fx-text-fill: #f0f0f0;");

        // ===== SHOW WINDOW =====
        primaryStage.setTitle("Java Game Engine");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
