package simulation;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import simulation.gui.App;

import java.util.ArrayList;
import java.util.List;


public class SimulationEngine implements Runnable {

    private final App app;
    private final WorldMap map;
    private final List<Animal> animals = new ArrayList<>();
    private final int moveEnergy;
    private final GridPane grid;
    private final int moveDelay;
    private int day;
    private double averageLifespan;
    private long numberOfAllAnimals;
    private final VBox statistics;

    public SimulationEngine(int[] args, List<Vector2d> positions, WorldMap map, App app, GridPane grid, VBox statistics) {

        this.map = map;
        this.app = app;
        this.moveEnergy = args[4];
        this.grid = grid;
        this.moveDelay = args[7];
        this.day = 0;
        this.averageLifespan = 0;
        this.numberOfAllAnimals = 0;
        this.statistics = statistics;

        for(Vector2d pos: positions){

            Animal animal = new Animal(map, pos, args[3], 1);
            map.placeAnimal(animal.getPosition(), animal);
            addAnimal(animal);
        }
    }

    public void addAnimal(Animal animal) {

        animals.add(animal);
        numberOfAllAnimals += 1;
    }

    public void run() {

        while (animals.size() > 0) {

            day += 1;
            removeDeadAnimals();
            moveAnimals();
            map.feedAnimals();
            removeDeadAnimals();
            map.breedAnimals(this, day);
            map.growPlants();

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
        app.positionChanged(grid, map, statistics);
    }

    private void removeDeadAnimals() {

        int i = 0;

        while (i < animals.size()) {
            Animal animal = animals.get(i);
            if (animal.getEnergy() > 0) {
                i += 1;
            }
            else {
                int lifeSpan = day - animal.getBirthDate();
                averageLifespan = averageLifespan * ((numberOfAllAnimals - 1.) / numberOfAllAnimals)
                        + lifeSpan * (1.0 / numberOfAllAnimals);
                map.removeAnimal(animal.getPosition(), animal);
                animals.remove(i);
            }
        }
    }

    public int getDay() {
        return this.day;
    }

    public int getNumberOfAnimals() {
        return this.animals.size();
    }

    public int getNumberOfPlants() {
        return this.map.getNumberOfPlants();
    }

    public int getAverageEnergy() {

        int size = animals.size();

        return getAverageEnergy(0, size - 1, size);
    }

    public int getAverageEnergy(int left, int right, int size) {

        if (left == right) return animals.get(left).getEnergy();

        int middle = (left + right) / 2;

        if (middle + 1 < size) {
            return (getAverageEnergy(left, middle, size) + getAverageEnergy(middle + 1, right, size)) / 2;
        }
        else return getAverageEnergy(left, middle, size);
    }

    public double getAverageLifespan() {
        return averageLifespan;
    }
}
