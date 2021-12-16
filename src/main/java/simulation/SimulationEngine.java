package simulation;

import simulation.gui.App;

import java.util.ArrayList;
import java.util.List;


public class SimulationEngine implements Runnable {

    private final App app;
    private WorldMap map;
    private final List<Animal> animals = new ArrayList<>();
    private boolean pause = false;
    private int moveEnergy;


    public SimulationEngine(WorldMap map, Vector2d[] positions, App app, int moveEnergy){
        this.map = map;
        this.app = app;
        this.moveEnergy = moveEnergy;
        for(Vector2d pos: positions){
            Animal animal = new Animal(map, pos, 10);
            map.placeAnimal(animal);
            animals.add(animal);
        }
    }

    public void pause() {
        pause = true;
    }

    public void resume() {
        pause = false;
    }

    public void run() {
        int moveDelay = 150;

        while (true) {

            if (!pause) {
                removeDeadAnimals();
                moveAnimals();
                feedAnimals();
                reproduceAnimals();
                growPlants();
            }

            try {
                Thread.sleep(moveDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void removeDeadAnimals() {
        for (Animal animal: animals) {
            if (animal.exhausted(moveEnergy)) {
                map.removeAnimal(animal);
            }
        }
    }

    private void moveAnimals() {
        for(Animal animal: animals) {
            animal.move();
        }
        app.positionChanged();
    }
    private void feedAnimals() {
        for(Animal animal: animals) {

        }
    }
    private void reproduceAnimals() {

    }
    private void growPlants() {

    }
}
