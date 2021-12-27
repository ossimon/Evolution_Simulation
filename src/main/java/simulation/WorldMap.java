package simulation;

import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.*;

public class WorldMap {

    private final Vector2d boundary1;
    private final Vector2d boundary2;
    private final boolean teleport;
    private final int plantEnergy;
    private final Map<Vector2d, List<Animal>> animals = new HashMap<>();
    private final Map<Vector2d, Plant> plants = new HashMap<>();

    public WorldMap(int width, int height, int plantEnergy, boolean teleport) {
        this.plantEnergy = plantEnergy;
        boundary1 = new Vector2d(0, 0);
        boundary2 = new Vector2d(width - 1, height - 1);
        this.teleport = teleport;
    }

    public void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition) {
        removeAnimal(oldPosition, animal);
        placeAnimal(newPosition, animal);
    }

    public Vector2d resultDestination (Vector2d oldPosition, Vector2d newPosition) {

        if (newPosition.follows(boundary1) && newPosition.precedes(boundary2)) {
            return newPosition;
        }
        else if (teleport) {
            newPosition = newPosition.within(boundary2);
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
            int currentIndex = animalList.size() - 1;
            while (currentIndex > 0 && animalList.get(currentIndex - 1).getEnergy() < animalList.get(currentIndex).getEnergy()) {
                Animal animal1 = animalList.get(currentIndex - 1);
                Animal animal2 = animalList.get(currentIndex - 1);
                animalList.add(currentIndex - 1, animal2);
                animalList.add(currentIndex, animal1);
                currentIndex -= 1;
            }
        }
        else {
            List<Animal> positions = new ArrayList<>();
            positions.add(animal);
            animals.put(position, positions);
        }
    }

    public void growPlants() {

        if (plants.size() < (boundary2.x + 1) * (boundary2.y + 1)) {
            Random rng = new Random();
            Vector2d grassPos;

            do {
                grassPos = new Vector2d(rng.nextInt(boundary2.x + 1), rng.nextInt(boundary2.y + 1));
            } while (plants.containsKey(grassPos));

            plants.put(grassPos, new Plant(grassPos));
        }
        System.out.println(plants.keySet());
    }

    public void feedAnimals() {

        for (Vector2d position: plants.keySet()) {

            IMapElement object = this.objectAt(position);
            if (object instanceof Animal) {
                ((Animal) object).feed(this.plantEnergy);
//                plants.remove(position);
            }
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

            if (animals.get(position).isEmpty()) {
                animals.remove(position);
            }
        }
    }

    public void breedAnimals(List<Animal> engineAnimals) {

        List<Animal> animalList;

        for (Vector2d pos: animals.keySet()) {

            animalList = animals.get(pos);

            if (animalList.size() > 1) {
                Animal parent1 = animalList.get(0);
                Animal parent2 = animalList.get(1);
                if (parent1.getEnergy() > 0 && parent2.getEnergy() > 0) {
                    Animal newAnimal = parent1.copulateWith(parent2);
                    this.placeAnimal(pos, newAnimal);
                    engineAnimals.add(newAnimal);
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
