package simulation;

import java.util.HashMap;
import java.util.Map;

public class WorldMap {


    protected int height;
    protected int width;
    protected Vector2d boundary1;
    protected Vector2d boundary2;
    protected Map<Vector2d, Animal> animals = new HashMap<>();

    public WorldMap(int width, int height) {
        this.width = width;
        this.height = height;
        boundary1 = new Vector2d(0, 0);
        boundary2 = new Vector2d(width - 1, height - 1);
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Animal animal = animals.get(oldPosition);
        animals.remove(oldPosition);
        animals.put(newPosition, animal);
    }

    public boolean canMoveTo (Vector2d position) {
        return position.follows(boundary1) && position.precedes(boundary2);
    }

    public void place (Animal animal) {
        Vector2d pos = animal.getPosition();
        animals.put(pos, animal);
    }

    public IMapElement objectAt(Vector2d position){
        return animals.get(position);
    }

}
