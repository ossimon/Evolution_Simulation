package simulation;

import simulation.gui.App;

import java.util.ArrayList;
import java.util.List;


public class SimulationEngine implements Runnable {

    private final App app;
    private final WorldMap map;
    private final List<Animal> animals = new ArrayList<>();
    private boolean pause = false;
    private final int moveEnergy;


    public SimulationEngine(WorldMap map, List<Vector2d> positions, App app, int startEnergy, int moveEnergy){

        this.map = map;
        this.app = app;
        this.moveEnergy = moveEnergy;
        int animalID = 0;

        for(Vector2d pos: positions){
            Animal animal = new Animal(map, pos, startEnergy, animalID);
            animalID += 1;
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
        int moveDelay = 150;

        while (animals.size() > 0) {

            if (!pause) {
//                System.out.println(animals.size());
                map.killAnimals();
                System.out.println("Engine: " + animals.size());
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
                map.removeAnimal(animal.getPosition(), animal);
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
