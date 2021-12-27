package simulation.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.stage.WindowEvent;
import simulation.*;

import java.io.FileNotFoundException;
import java.util.List;


public class App extends Application {

    private WorldMap map;
    private SimulationEngine engine;
    private ImageViewGetter imageViewGetter;
    private GridPane grid;
    private final int mapWidth = 600;
    private final int mapHeight = (int) (600.0 * 0.86);
    private final int height = 12;
    private final int width = 12;
    private int plantEnergy = 100;


    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        primaryStage.setTitle("Evolution Simulation");

        grid = new GridPane();

        this.updateScene();

        for (int i = 0; i < width; i++) grid.getColumnConstraints().add(new ColumnConstraints(mapWidth / width));
        for (int i = 0; i < height; i++) grid.getRowConstraints().add(new RowConstraints(mapHeight / height * 1.16));

        Scene scene = new Scene(grid, 1200, 800);

        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });

        Thread engineThread = new Thread(engine);
        engineThread.start();
    }

    @Override
    public void init() {

        map = new WorldMap(width, height, plantEnergy, true);
        try {
            imageViewGetter = new ImageViewGetter(map, mapWidth / width + 1, mapHeight / height + 1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<Vector2d> positions = VectorGenerator.generateVectors(map, new Vector2d(0, 0),
                        new Vector2d(width - 1, height - 1), 10);
        engine = new SimulationEngine(map, positions, this, 1000, 10);

    }

    private void updateScene() throws FileNotFoundException {

//        grid.setGridLinesVisible(false);
//        grid.setGridLinesVisible(true);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                VBox vBox = imageViewGetter.imageAt(new Vector2d(j, i), true);
                grid.add(vBox, j, i);
                GridPane.setHalignment(vBox, HPos.CENTER);
            }
        }
    }

    public void positionChanged() {

        Platform.runLater(() -> {
            grid.getChildren().clear();
            try {
                this.updateScene();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}
