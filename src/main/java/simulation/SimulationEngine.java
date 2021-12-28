package simulation;

import javafx.scene.layout.GridPane;
import simulation.gui.App;

import java.util.ArrayList;
import java.util.List;


public class SimulationEngine implements Runnable {

    private final App app;
    private final WorldMap map;
    private final List<Animal> animals = new ArrayList<>();
    private boolean pause = false;
    private final int moveEnergy;
    private final GridPane grid;
    private final int moveDelay;


    public SimulationEngine(int[] args, List<Vector2d> positions, WorldMap map, App app, GridPane grid) {

        this.map = map;
        this.app = app;
        this.moveEnergy = args[4];
        this.grid = grid;
        this.moveDelay = args[7];

        for(Vector2d pos: positions){
            Animal animal = new Animal(map, pos, args[3]);
            map.placeAnimal(animal.getPosition(), animal);
            animals.add(animal);
        }


    }

    public void run() {

        while (animals.size() > 0) {

            if (!pause) {
                removeDeadAnimals();
                moveAnimals();
                map.feedAnimals();
                removeDeadAnimals();
                map.breedAnimals(animals);
                map.growPlants();
            }

            try {
                Thread.sleep(moveDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void moveAnimals() {
        for(Animal animal: animals) {
            animal.move(moveEnergy);
        }
        app.positionChanged(grid, map);
    }

    private void removeDeadAnimals() {

        int i = 0;

        while (i < animals.size()) {
            Animal animal = animals.get(i);
            if (animal.getEnergy() > 0) {
                i += 1;
            }
            else {
                map.removeAnimal(animal.getPosition(), animal);
                animals.remove(i);
            }
        }
    }
}
