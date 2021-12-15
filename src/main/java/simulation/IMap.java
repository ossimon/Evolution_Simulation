package simulation;

public interface IMap {

    public boolean canMoveTo (Vector2d position);
    public void place (Animal animal);
    public boolean isOccupied(Vector2d position);
    public IMapElement objectAt(Vector2d position);
}
