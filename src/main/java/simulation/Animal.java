package simulation;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import simulation.gui.App;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Animal implements IMapElement {

    private long ID;
    private Vector2d position;
    private MapDirection direction;
    private ImageView imageView;
    private WorldMap map;
    private int energy;
    private Genotype genotype;

    public Animal(WorldMap map, Vector2d position, int energy) {
        this.direction = MapDirection.randomDirection();
        this.map = map;
        this.position = position;
        this.genotype = new Genotype();
        this.energy = energy;
    }

    public Animal(WorldMap map, Vector2d position, Genotype genotype, int energy) {
        this.direction = MapDirection.randomDirection();
        this.map = map;
        this.position = position;
        this.genotype = genotype;
        this.energy = energy;
    }

    public Animal copulateWith(Animal other) {

        int parentEnergy1 = (int) (this.energy * 0.25);
        int parentEnergy2 = (int) (other.energy * 0.25);
        Genotype childGenotype = new Genotype(parentEnergy1, parentEnergy2, this.genotype, other.genotype);

        Animal child = new Animal(this.map, this.position, childGenotype, parentEnergy1 + parentEnergy2);
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
    public ImageView getImageView() {
//        DO POPRAWY !!! MA SIE TWORZYC JEDEN VIEW I MA BYC ZMIENNA KTORA SPRAWDZA,
//        CZY SIE COS ZMIENILO (JAK NIE TO NIE TWORZYMY NOWEGO)
        try {

            String fileName = switch (this.direction) {
                case NORTH -> "src/main/resources/up.png";
                case NORTHWEST -> "src/main/resources/upleft.png";
                case NORTHEAST -> "src/main/resources/upright.png";
                case SOUTH -> "src/main/resources/down.png";
                case SOUTHWEST -> "src/main/resources/downleft.png";
                case SOUTHEAST -> "src/main/resources/downright.png";
                case WEST -> "src/main/resources/left.png";
                case EAST -> "src/main/resources/right.png";
            };

            Image image = new Image(new FileInputStream(fileName));
            imageView = new ImageView(image);

            imageView.setFitWidth(50);
            imageView.setFitHeight(50);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return imageView;
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

    public boolean exhausted(int exhaustion) {
        energy -= exhaustion;
        return energy <= 0;
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
