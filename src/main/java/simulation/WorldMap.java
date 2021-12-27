package simulation;

import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.*;

public class WorldMap {

    private final Vector2d boundary;
    private final boolean teleport;
    private final int plantEnergy;
    private final Map<Vector2d, List<Animal>> animals = new HashMap<>();
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

    private void swapAnimals(List<Animal> animalList, int index1, int index2) {
        Animal animal1 = animalList.get(index1);
        Animal animal2 = animalList.get(index2);
        animalList.add(index1, animal2);
        animalList.add(index2, animal1);
    }

    public void placeAnimal(Vector2d position, Animal animal) {

        if (animals.containsKey(position)) {

            List<Animal> animalList = animals.get(position);
            animalList.add(animal);

            int currentIndex = animalList.size() - 1;
            Animal animal1 = animalList.get(currentIndex - 1);
            Animal animal2 = animalList.get(currentIndex);

            while (currentIndex > 0 && animal1.getEnergy() < animal2.getEnergy()) {
                swapAnimals(animalList, currentIndex, currentIndex - 1);
                currentIndex -= 1;
                animal1 = animalList.get(currentIndex - 1);
                animal2 = animalList.get(currentIndex);
            }
        }
        else {
            List<Animal> positions = new ArrayList<>();
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

        List<Vector2d> plantsEaten = new ArrayList<>();

        for (Vector2d position: plants.keySet()) {

            IMapElement object = this.objectAt(position);
            if (object instanceof Animal) {
                ((Animal) object).feed(this.plantEnergy);
                plantsEaten.add(position);
//                plants.remove(position);
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
                    this.placeAnimal(position, newAnimal);
                    engineAnimals.add(newAnimal);

//                    positionChanged(parent1, position, position);
//                    positionChanged(parent2, position, position);
                }
            }
        }
    }

    public void killAnimals(List<Animal> engineAnimals) {

        List<Animal> animalList;

        for (Vector2d pos : animals.keySet()) {

            animalList = animals.get(pos);
            int i = animalList.size() - 1;

            while (animalList.get(i).getEnergy() <= 0) {
                engineAnimals.remove(animalList.get(i));
                animalList.remove(i);
                i -= 1;
            }
        }
    }
}
