package simulation;

import java.util.Random;

public class Animal implements IMapElement {

    private Vector2d position;
    private MapDirection direction;
    private final WorldMap map;
    private int energy;
    private final Genotype genotype;
    private final int birthDate;

    public Animal(WorldMap map, Vector2d position, int energy, int birthDate) {

        this.direction = MapDirection.randomDirection();
        this.map = map;
        this.position = position;
        this.genotype = new Genotype();
        this.energy = energy;
        this.birthDate = birthDate;
    }

    public Animal(WorldMap map, Vector2d position, Genotype genotype, int energy, int birthDate) {

        this.direction = MapDirection.randomDirection();
        this.map = map;
        this.position = position;
        this.genotype = genotype;
        this.energy = energy;
        this.birthDate = birthDate;
    }

    public Animal copulateWith(Animal other, int day) {

        int parentEnergy1 = (int) (this.energy * 0.25);
        int parentEnergy2 = (int) (other.energy * 0.25);
        Genotype childGenotype = new Genotype(parentEnergy1, parentEnergy2, this.genotype, other.genotype);

        Animal child = new Animal(this.map, this.position, childGenotype, parentEnergy1 + parentEnergy2, day);
        this.energy -= parentEnergy1;
        other.energy -= parentEnergy2;

        return child;
    }

    public void move(int moveEnergy) {

        Random rng = new Random();
        int moveIndex = rng.nextInt(32 );
        int move = genotype.getGenes()[moveIndex];

        Vector2d newPosition;

        switch (move) {
            case 0 -> {
                newPosition = this.position.add(direction.toUnitVector());
                newPosition = map.resultDestination(position, newPosition);
                if (this.position != newPosition) {
                    this.positionChanged(position, newPosition);
                    this.position = newPosition;
                }
            }
            case 1 -> direction = direction.next();
            case 2 -> direction = direction.next().next();
            case 3 -> direction = direction.next().next().next();
            case 4 -> {
                newPosition = this.position.subtract(direction.toUnitVector());
                newPosition = map.resultDestination(position, newPosition);
                if (this.position != newPosition) {
                    this.positionChanged(position, newPosition);
                    this.position = newPosition;
                    }
                }

            case 5 -> direction = direction.previous();
            case 6 -> direction = direction.previous().previous();
            case 7 -> direction = direction.previous().previous().previous();
        }

        this.energy -= moveEnergy;
    }

    @Override
    public Vector2d getPosition() {
        return this.position;
    }

    public MapDirection getDirection() {
        return this.direction;
    }

    public int getEnergy() {
        return this.energy;
    }

    public int getBirthDate() {
        return this.birthDate;
    }

    public void feed(int energy) {
        this.energy += energy;
    }

    private void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        map.positionChanged(this, oldPosition, newPosition);
    }
    @Override
    public String toString() {
        return Integer.toString(energy);
    }
}
