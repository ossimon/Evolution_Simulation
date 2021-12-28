package simulation;

public class Plant implements IMapElement, Comparable {

    private final Vector2d position;

    public Plant (Vector2d position) {

        this.position = position;
    }

    public Vector2d getPosition() {
        return this.position;
    }

    @Override
    public int compareTo(Object other) {

        return this.position.compareTo(((Plant) other).position);
    }
}
