package simulation;

import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.*;

public class WorldMap {

    private final Vector2d boundary;
    private final boolean teleport;
    private final int plantEnergy;
    private final Map<Vector2d, SortedAnimalList> animals = new HashMap<>();
    private final Map<Vector2d, Plant> plants = new HashMap<>();

    public WorldMap(int width, int height, int plantEnergy, boolean teleport) {
        this.plantEnergy = plantEnergy;
        this.boundary = new Vector2d(width - 1, height - 1);
        this.teleport = teleport;
    }


    public void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition) {
        removeAnimal(oldPosition, animal);
        placeAnimal(newPosition, animal);
    }

    public Vector2d resultDestination (Vector2d oldPosition, Vector2d newPosition) {

        if (newPosition.follows(new Vector2d(0, 0)) && newPosition.precedes(boundary)) {
            return newPosition;
        }
        else if (teleport) {
            newPosition = newPosition.within(boundary);
            return newPosition;
        }
        else {
            return oldPosition;
        }
    }

    public void placeAnimal(Vector2d position, Animal animal) {

        if (animals.containsKey(position)) {

            List<Animal> animalList = animals.get(position);
            animalList.add(animal);
        }
        else {
            SortedAnimalList positions = new SortedAnimalList();
            positions.add(animal);
            animals.put(position, positions);
        }
    }

    public void growPlants() {
        List<Vector2d> destination = VectorGenerator.generateVectors(this, new Vector2d(0, 0), boundary, 1);
        if (destination.size() > 0) {
            Vector2d position = destination.get(0);
            plants.put(position, new Plant(position));
        }
    }

    public void feedAnimals() {

        int size = 0;
        for (Vector2d position: animals.keySet()) {
            List<Animal> animalList = animals.get(position);
            size += animalList.size();
        }


        System.out.println("Map: " + size);
        List<Vector2d> plantsEaten = new ArrayList<>();

        for (Vector2d position: plants.keySet()) {

            IMapElement object = this.objectAt(position);
            if (object instanceof Animal) {
                ((Animal) object).feed(this.plantEnergy);
                plantsEaten.add(position);
            }
        }
        for(Vector2d position: plantsEaten) {
            plants.remove(position);
        }
    }

    public IMapElement objectAt(Vector2d position){

        if (animals.containsKey(position) && !animals.get(position).isEmpty()) {
            return animals.get(position).get(0);
        }
        return plants.get(position);
    }

    public void removeAnimal(Vector2d position, Animal animal) {

        if (animals.containsKey(position)) {
            animals.get(position).remove(animal);

            if (animals.get(position).size() == 0) {
                animals.remove(position);
            }
        }

    }

    public void breedAnimals(List<Animal> engineAnimals) {

        List<Animal> animalList;

        for (Vector2d position: animals.keySet()) {

            animalList = animals.get(position);

            if (animalList.size() > 1) {

                Animal parent1 = animalList.get(0);
                Animal parent2 = animalList.get(1);

                if (parent1.getEnergy() > 0 && parent2.getEnergy() > 0) {

                    Animal newAnimal = parent1.copulateWith(parent2);
                    animalList.remove(parent1);
                    animalList.remove(parent2);
                    animalList.add(parent1);
                    animalList.add(parent2);

                    if (newAnimal.getEnergy() > 0) {
                        this.placeAnimal(position, newAnimal);
                        engineAnimals.add(newAnimal);
                    }
                }
            }
        }
    }

    public void killAnimals() {

        List<Animal> animalList;

        for (Vector2d pos : animals.keySet()) {

            animalList = animals.get(pos);
            int i = animalList.size() - 1;

            while (i >= 0 && animalList.get(i).getEnergy() <= 0) {
                animalList.remove(i);
                i -= 1;
            }
        }
    }
}
