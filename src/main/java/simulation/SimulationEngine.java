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


    public SimulationEngine(int width, int height, int startEnergy, int moveEnergy, int plantEnergy, int jungleRatio) {

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
//                System.out.println(animals.size());
//                removeDeadAnimals();
                moveAnimals();
//                feedAnimals();
//                reproduceAnimals();
//                growPlants();
            }

            try {
                Thread.sleep(moveDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void removeDeadAnimals() {

    }

    private void moveAnimals() {
        for(Animal animal: animals) {
            animal.move();
        }
        app.positionChanged();
    }
    private void feedAnimals() {

    }
    private void reproduceAnimals() {

    }
    private void growPlants() {

    }
}
