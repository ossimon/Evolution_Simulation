package simulation.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import simulation.*;

public class App extends Application {

    private WorldMap map;
    private SimulationEngine engine;
    private GridPane grid;
    private final int height = 5;
    private final int width = 5;


    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("Evolution Simulation");

        grid = new GridPane();

        this.updateScene();

        for (int i = 0; i < width; i++) grid.getColumnConstraints().add(new ColumnConstraints(50));
        for (int i = 0; i < height; i++) grid.getRowConstraints().add(new RowConstraints(50));

        Scene scene = new Scene(grid, 1200, 800);

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

        Genotype gen1 = new Genotype(0);
        Genotype gen2 = new Genotype(1);

        System.out.println(new Genotype(5, 10, gen1, gen2));

        map = new WorldMap(width, height, true);
//        Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(2, 2)};
        Vector2d[] positions =
                VectorGenerator.generateVectors(new Vector2d(0, 0),
                        new Vector2d(width - 1, height - 1), 2);
        engine = new SimulationEngine(map, positions, this, 10);

    }

    private void updateScene() {

        grid.setGridLinesVisible(false);
        grid.setGridLinesVisible(true);

        IMapElement mapElement;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                mapElement = map.objectAt(new Vector2d(j, i));

                if (mapElement != null) {
                    ImageView imageView = mapElement.getImageView();
                    grid.add(imageView, j, i);
                    GridPane.setHalignment(imageView, HPos.CENTER);
                }
            }
        }
    }

    public void positionChanged() {

        Platform.runLater(() -> {
//            System.out.println("position changed");
            grid.getChildren().clear();
            this.updateScene();
        });
    }
}
