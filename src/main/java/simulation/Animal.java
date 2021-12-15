package simulation;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import simulation.gui.App;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Animal implements IMapElement{

    private Vector2d position;
    private MapDirection direction;
    private ImageView imageView;
    private IMap map;
    private int energy;
    private Genotype genotype;
    private List<IPositionChangeObserver> observers = new ArrayList<>();

    public Animal(IMap map, App app, Vector2d position) {
        this.direction = MapDirection.NORTH;
        this.map = map;
        this.position = position;
        this.genotype = new Genotype();
        this.addObserver((IPositionChangeObserver) map);
    }

    public void move() {
        Random rng = new Random();
        int moveIndex = rng.nextInt(31);
        int move = genotype.getGenes()[moveIndex];

        Vector2d newPosition;

        switch (move) {
            case 0 -> {
                newPosition = this.position.add(direction.toUnitVector());
                if (map.canMoveTo(newPosition)) {
                    this.positionChanged(position, newPosition);
                    this.position = newPosition;
                }
            }
            case 1 -> {
                direction = direction.next();
            }
            case 2 -> {
                direction = direction.next().next();
            }
            case 3 -> {
                direction = direction.next().next().next();
            }
            case 4 -> {
                newPosition = this.position.subtract(direction.toUnitVector());
                if (map.canMoveTo(newPosition)) {
                    this.positionChanged(position, newPosition);
                    this.position = newPosition;
                }
            }
            case 5 -> {
                direction = direction.previous();
            }
            case 6 -> {
                direction = direction.previous().previous();
            }
            case 7 -> {
                direction = direction.previous().previous().previous();
            }
        }
//        System.out.println(position);
    }

    @Override
    public ImageView getImageView() {
//        DO POPRAWY !!! MA SIE TWORZYC JEDEN VIEW I MA BYC ZMIENNA KTORA SPRAWDZA,
//        CZY SIE COS ZMIENILO (JAK NIE TO NIE TWORZYMY NOWEGO)


        try {
            Image image = new Image(new FileInputStream("src/main/resources/grass.png"));
            imageView = new ImageView(image);

            imageView.setFitWidth(20);
            imageView.setFitHeight(20);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return imageView;
    }

    @Override
    public Vector2d getPosition() {
        return this.position;
    }

    private void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        for (IPositionChangeObserver observer: observers) {
            observer.positionChanged(oldPosition, newPosition);
        }
    }

    public void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }
    public void removeObserver(IPositionChangeObserver observer){
        observers.remove(observer);
    }
}
