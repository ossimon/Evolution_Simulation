package simulation.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import simulation.*;

public class App extends Application{

    private IMap map;
    private SimulationEngine engine;
    private GridPane grid;
    private Scene scene;
    private final int height = 8;
    private final int width = 10;


    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("Evolution Simulation");

        grid = new GridPane();

        this.updateScene();

        for (int i = 0; i < width; i++) grid.getColumnConstraints().add(new ColumnConstraints(40));
        for (int i = 0; i < height; i++) grid.getRowConstraints().add(new RowConstraints(40));

        scene = new Scene(grid, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.show();

        Thread engineThread = new Thread(engine);
        engineThread.start();
    }
    @Override
    public void init() {

        map = new BoundedMap(10, 8);
        Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
        engine = new SimulationEngine(map, positions, this);

    }

    private void updateScene() {
        System.out.println("scene updated");
        grid.setGridLinesVisible(false);
        grid.setGridLinesVisible(true);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                IMapElement mapElement = map.objectAt(new Vector2d(i, j));

                if (mapElement != null) {
                    System.out.println(mapElement.getPosition());
                    ImageView imageView = mapElement.getImageView();
                    System.out.println(imageView);
                    grid.add(imageView, i, j);
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
