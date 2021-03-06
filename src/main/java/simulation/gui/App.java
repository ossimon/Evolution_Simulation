package simulation.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import simulation.*;

import java.io.FileNotFoundException;
import java.util.List;


public class App extends Application {

    private int[] args;
    private ImageViewGetter imageViewGetter;
    private GridPane grid1;
    private GridPane grid2;
    private boolean constraintsSet = false;
    private final int maxMapWidth = 700;
    private final int maxMapHeight = (int) (500.0 * 0.86);
    private SimulationEngine engine1;
    private SimulationEngine engine2;
    private VBox statistics;


    @Override
    public void start(Stage primaryStage) {

        grid1 = new GridPane();
        grid2 = new GridPane();
        VBox map1 = new VBox(new Label("Unbounded map"), grid1);
        VBox map2 = new VBox(new Label("Bounded map"), grid2);
        VBox statistics1 = new VBox();
        VBox statistics2 = new VBox();
        statistics = new VBox(statistics1, statistics2);
        HBox maps = new HBox(map1, statistics, map2);

        HBox hBox = new HBox(createMenu(), maps);

        Scene scene = new Scene(hBox);

        primaryStage.setTitle("Evolution Simulation");
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public VBox createMenu() {

        Label label0 = new Label("\t\tMap properties");

        Label label1 = new Label("Map width:\t");
        TextField widthInsert = new TextField("12");
        HBox hBox1 = new HBox(label1, widthInsert);

        Label label2 = new Label("Map height:\t");
        TextField heightInsert = new TextField("8");
        HBox hBox2 = new HBox(label2, heightInsert);

        Label label3 = new Label("Jungle Ratio (%):\t");
        TextField ratioInsert = new TextField("50");
        HBox hBox3 = new HBox(label3, ratioInsert);

        VBox mapProperties = new VBox(label0, hBox1, hBox2, hBox3);


        Label label4 = new Label("\t\tEnergy properties");

        Label label5 = new Label("Start energy:\t");
        TextField startEnergyInsert = new TextField("1000");
        HBox hBox5 = new HBox(label5, startEnergyInsert);

        Label label6 = new Label("Move energy:\t");
        TextField moveEnergyInsert = new TextField("10");
        HBox hBox6 = new HBox(label6, moveEnergyInsert);

        Label label7 = new Label("Plant energy:\t");
        TextField plantEnergyInsert = new TextField("100");
        HBox hBox7 = new HBox(label7, plantEnergyInsert);

        VBox energyProperties = new VBox(label4, hBox5, hBox6, hBox7);


        Label label8 = new Label("\t\t\tOthers:");

        Label label9 = new Label("Animals at start:\t");
        TextField animalNumberInsert = new TextField("10");
        HBox hBox9 = new HBox(label9, animalNumberInsert);

        Label label10 = new Label("Day length (ms):\t");
        TextField delayInsert = new TextField("150");
        HBox hBox10 = new HBox(label10, delayInsert);

        VBox others = new VBox(label8, hBox9, hBox10);

        VBox menu = new VBox(mapProperties, energyProperties, others);

        Button startButton = new Button("\tStart!\t");
        startButton.setOnAction(value ->  {
            int width = Integer.parseInt(widthInsert.getText());
            int height = Integer.parseInt(heightInsert.getText());
            int jungleRatio = Integer.parseInt(ratioInsert.getText());
            int startEnergy = Integer.parseInt(startEnergyInsert.getText());
            int moveEnergy = Integer.parseInt(moveEnergyInsert.getText());
            int plantEnergy = Integer.parseInt(plantEnergyInsert.getText());
            int animalNumber = Integer.parseInt(animalNumberInsert.getText());
            int delay = Integer.parseInt(delayInsert.getText());

            int cellSize = maxMapWidth / width;
            if (maxMapHeight / height < cellSize) cellSize = maxMapHeight / height;

            args = new int[]{width, height, jungleRatio, startEnergy, moveEnergy, plantEnergy, animalNumber, delay, cellSize};

            if (!constraintsSet) setGridConstraints();

            try {
                engine1 = runEngine(grid1, true);
                engine2 = runEngine(grid2, false);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            menu.getChildren().clear();
        });

        menu.getChildren().add(startButton);

        return menu;
    }

    private void setGridConstraints() {

        int width = args[0];
        int height = args[1];
        int cellSize = args[8];

        for (int i = 0; i < width; i++) grid1.getColumnConstraints().add(new ColumnConstraints(cellSize - 1));
        for (int i = 0; i < height; i++) grid1.getRowConstraints().add(new RowConstraints(cellSize * 1.16 - 1));

        for (int i = 0; i < width; i++) grid2.getColumnConstraints().add(new ColumnConstraints(cellSize - 1));
        for (int i = 0; i < height; i++) grid2.getRowConstraints().add(new RowConstraints(cellSize * 1.16 - 1));

        constraintsSet = true;
    }

    private SimulationEngine runEngine(GridPane grid, boolean teleport) throws FileNotFoundException {

        int width = args[0];
        int height = args[1];
        int cellSize = args[8];

        WorldMap map = new WorldMap(args, teleport);
        try {
            imageViewGetter = new ImageViewGetter(cellSize);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<Vector2d> positions = VectorGenerator.generateVectors(map, new Vector2d(0, 0),
                new Vector2d(width - 1, height - 1), 10, false);

        SimulationEngine engine = new SimulationEngine(args, positions, map, this, grid, statistics);

        this.updateGrid(grid, map);

        Thread engineThread = new Thread(engine);
        engineThread.start();

        return engine;
    }

    private void updateGrid(GridPane grid, WorldMap map) throws FileNotFoundException {

        int width = args[0];
        int height = args[1];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                Vector2d position = new Vector2d(j, i);
                boolean isJungle = map.isJungle(position);

                VBox vBox = imageViewGetter.imageAt(position, map, isJungle);
                grid.add(vBox, j, i);
                GridPane.setHalignment(vBox, HPos.CENTER);
            }
        }
    }

    private void updateStatistics(VBox statistics) {

        statistics.getChildren().clear();
        statistics.getChildren().add(getStatistics(engine1, "Unbounded map"));
        statistics.getChildren().add(getStatistics(engine2, "Bounded map"));
    }

    public void positionChanged(GridPane grid, WorldMap map, VBox statistics) {

        Platform.runLater(() -> {
            grid.getChildren().clear();
            try {
                this.updateGrid(grid, map);
                this.updateStatistics(statistics);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private VBox getStatistics(SimulationEngine engine, String name) {

        Label nameLabel = new Label("\n" + name + ":");
        Label dayLabel = new Label("Day: " + engine.getDay());
        Label animalLabel = new Label("Number of animals: " + engine.getNumberOfAnimals());
        Label plantLabel = new Label("Number of plants: " + engine.getNumberOfPlants());
        Label energyLabel = new Label("Average energy: " + engine.getAverageEnergy());
        Label lifespanLabel = new Label("Average lifespan: " + (int) engine.getAverageLifespan());

        return new VBox(nameLabel, dayLabel, animalLabel, plantLabel, energyLabel, lifespanLabel);
    }
}
