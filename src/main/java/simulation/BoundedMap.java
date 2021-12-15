package simulation;

public class BoundedMap extends AbstractMap {


    public BoundedMap(int width, int height) {
        super(width, height);
    }

    @Override
    public boolean canMoveTo (Vector2d position) {

        return position.follows(boundary1) && position.precedes(boundary2);
    }
}
