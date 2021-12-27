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


    public SimulationEngine(WorldMap map, Vector2d[] positions, App app, int startEnergy, int moveEnergy){

        this.map = map;
        this.app = app;
        this.moveEnergy = moveEnergy;
        for(Vector2d pos: positions){
            Animal animal = new Animal(map, pos, startEnergy);
            map.placeAnimal(animal.getPosition(), animal);
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
        int moveDelay = 500;

        while (animals.size() > 0) {

            if (!pause) {
//                System.out.println(animals.size());
//                map.killAnimals(animals);
                removeDeadAnimals();
                moveAnimals();
                map.feedAnimals();
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
        app.positionChanged();
    }

    private void removeDeadAnimals() {

        int i = 0;

        while (i < animals.size()) {
            Animal animal = animals.get(i);
            if (animal.getEnergy() > 0) {
                i += 1;
            }
            else {
                animals.remove(i);
            }
        }
    }

    private void feedAnimals() {

    }
    private void reproduceAnimals() {

    }
    private void growPlants() {

    }
}
