package simulation;

import simulation.gui.App;

import java.util.*;


public class SimulationEngine implements Runnable {

    private final App app;
    private final List<Animal> animals = new ArrayList<>();

    public SimulationEngine(IMap map, Vector2d[] positions, App app){
        this.app = app;
        for(Vector2d pos: positions){
            Animal animal = new Animal(map, app, pos);
            map.place(animal);
            animals.add(animal);
        }
    }

    public void run() {
        int moveDelay = 500;

        while (true) {
            this.moveAnimals();
            app.positionChanged();

            try {
                Thread.sleep(moveDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void moveAnimals() {
        for(Animal animal: animals){
            animal.move();
        }
    }
}
