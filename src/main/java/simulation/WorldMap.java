package simulation;

import java.util.*;

public class WorldMap {

    private final Vector2d boundary;
    private final Vector2d jungleBoundary1;
    private final Vector2d jungleBoundary2;
    private final boolean teleport;
    private final int plantEnergy;
    private final int copulatingEnergy;
    private final Map<Vector2d, SortedAnimalList> animals = new HashMap<>();
    private final Map<Vector2d, Plant> plants = new HashMap<>();

    public WorldMap(int[] args, boolean teleport) {

        this.copulatingEnergy = args[3] / 2;
        this.plantEnergy = args[5];
        this.boundary = new Vector2d(args[0] - 1, args[1] - 1);
        this.teleport = teleport;

        double swampRatio = (100. - args[2]) / 100.;
        jungleBoundary1 = new Vector2d((int) (boundary.x / 2 * swampRatio), (int) (boundary.y / 2 * swampRatio));
        jungleBoundary2 = new Vector2d(boundary.x - (int) (boundary.x / 2 * swampRatio),
                boundary.y - (int) (boundary.y / 2* swampRatio));
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

    public void removeAnimal(Vector2d position, Animal animal) {

        if (animals.containsKey(position)) {

            animals.get(position).remove(animal);

            if (animals.get(position).size() == 0) {
                animals.remove(position);
            }
        }

    }

    public IMapElement objectAt(Vector2d position){

        if (animals.containsKey(position) && !animals.get(position).isEmpty()) {
            return animals.get(position).get(0);
        }
        return plants.get(position);
    }

    public void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition) {

        removeAnimal(oldPosition, animal);
        placeAnimal(newPosition, animal);
    }

    public int getNumberOfPlants() {
        return this.plants.size();
    }

    public void growPlants() {

        List<Vector2d> destination = VectorGenerator.generateVectors(this, new Vector2d(0, 0),
                boundary, 1, true);

        if (destination.size() > 0) {
            Vector2d position = destination.get(0);
            plants.put(position, new Plant(position));
        }

        destination = VectorGenerator.generateVectors(this, jungleBoundary1,
                jungleBoundary2, 1, false);

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
            }
        }
        for(Vector2d position: plantsEaten) {
            plants.remove(position);
        }
    }

    public void breedAnimals(SimulationEngine engine, int day) {

        List<Animal> animalList;

        for (Vector2d position: animals.keySet()) {

            animalList = animals.get(position);

            if (animalList.size() > 1) {

                Animal parent1 = animalList.get(0);
                Animal parent2 = animalList.get(1);

                if (parent1.getEnergy() >= copulatingEnergy && parent2.getEnergy() >= copulatingEnergy) {

                    Animal newAnimal = parent1.copulateWith(parent2, day);
                    animalList.remove(parent1);
                    animalList.remove(parent2);
                    animalList.add(parent1);
                    animalList.add(parent2);

                    this.placeAnimal(position, newAnimal);
                    engine.addAnimal(newAnimal);
                }
            }
        }
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

    public boolean isJungle(Vector2d position) {

        return position.follows(jungleBoundary1) && position.precedes(jungleBoundary2);
    }
}
