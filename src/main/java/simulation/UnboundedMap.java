package simulation;

public class UnboundedMap extends AbstractMap{

    public UnboundedMap(int width, int height) {
        super(width, height);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return true;
    }
}
