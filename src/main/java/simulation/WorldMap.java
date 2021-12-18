package simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldMap {


    private final Vector2d boundary1;
    private final Vector2d boundary2;
    private final boolean teleport;
    private final Map<Vector2d, List<Animal>> animals = new HashMap<>();

    public WorldMap(int width, int height, boolean teleport) {
        boundary1 = new Vector2d(0, 0);
        boundary2 = new Vector2d(width - 1, height - 1);
        this.teleport = teleport;
        System.out.println(width);
        System.out.println(height);
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
            animals.get(position).add(animal);
        }
        else {
            List<Animal> positions = new ArrayList<>();
            positions.add(animal);
            animals.put(position, positions);
        }
    }

    public void feedAnimals(int plantEnergy) {

        for (Vector2d position: animals.keySet()) {

        }
    }

    public IMapElement objectAt(Vector2d position){
        System.out.println(animals.size());
        if (animals.containsKey(position) && !animals.get(position).isEmpty()) {


            return animals.get(position).get(0);
        }
        return null;
    }


    public void removeAnimal(Vector2d position, Animal animal) {

        if (animals.containsKey(position)) {
            animals.get(position).remove(animal);

            if (animals.get(position).isEmpty()) {
                animals.remove(position);
            }
        }
    }

}
