package simulation;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractMap implements IMap, IPositionChangeObserver {


    protected int height;
    protected int width;
    protected Vector2d boundary1;
    protected Vector2d boundary2;
    protected Map<Vector2d, Animal> animals = new HashMap<>();

    public AbstractMap(int width, int height) {
        this.width = width;
        this.height = height;
        boundary1 = new Vector2d(0, 0);
        boundary2 = new Vector2d(width - 1, height - 1);
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {

    }

    public abstract boolean canMoveTo (Vector2d position);

    public void place (Animal animal) {
        Vector2d pos = animal.getPosition();
        animals.put(pos, animal);
    }
    public boolean isOccupied(Vector2d position){
        return this.objectAt(position) != null;
    }
    public IMapElement objectAt(Vector2d position){
        return animals.get(position);
    }

}
