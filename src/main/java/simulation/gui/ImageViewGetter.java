package simulation.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import simulation.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImageViewGetter {

    private final WorldMap map;
    private final Image left;
    private final Image upLeft;
    private final Image up;
    private final Image upRight;
    private final Image right;
    private final Image downRight;
    private final Image down;
    private final Image downLeft;
    private final Image plant;


    public ImageViewGetter(WorldMap map) throws FileNotFoundException {

        this.left = new Image(new FileInputStream("src/main/resources/left.png"));
        this.upLeft = new Image(new FileInputStream("src/main/resources/upleft.png"));
        this.up = new Image(new FileInputStream("src/main/resources/up.png"));
        this.upRight = new Image(new FileInputStream("src/main/resources/upright.png"));
        this.right = new Image(new FileInputStream("src/main/resources/right.png"));
        this.downRight = new Image(new FileInputStream("src/main/resources/downright.png"));
        this.down = new Image(new FileInputStream("src/main/resources/down.png"));
        this.downLeft = new Image(new FileInputStream("src/main/resources/downleft.png"));
        this.plant = new Image(new FileInputStream("src/main/resources/plant.png"));

        this.map = map;
    }

    public VBox imageAt(Vector2d position, boolean jungle) {

        IMapElement element = map.objectAt(position);

        if (element instanceof Animal animal) {
            return getAnimalImageView(animal.getDirection(), animal.getEnergy(), jungle);
        }
        else if (element instanceof Plant) {
            return getPlantImageView(jungle);
        }
        else {
            return new VBox();
        }
    }

    private VBox getAnimalImageView(MapDirection direction, double energy, boolean jungle) {

        Image image = switch (direction) {
            case NORTH -> up;
            case NORTHWEST -> upLeft;
            case NORTHEAST -> upRight;
            case SOUTH -> down;
            case SOUTHWEST -> downLeft;
            case SOUTHEAST -> downRight;
            case WEST -> left;
            case EAST -> right;
        };

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);

        double energyPercentage = energy / 1000;
        if (energyPercentage > 1.0) energyPercentage = 1.0;
        else if (energyPercentage < 0.0) energyPercentage = 0;

        double red = 1.0 - energyPercentage;
        double green = 0.0 + energyPercentage;

        Color color = new Color(red, green, 0, 1);
        Rectangle rectangle = new Rectangle(5 + 45 * energyPercentage, 10, color);

        Color backgroundColor = Color.BLACK;
//        if (jungle) {
//            backgroundColor = new Color(0.725, 0.478, 0.337, 1.0);
//        }
//        else {
//            backgroundColor = new Color(0.768, 1.0, 0.054, 1.0);
//        }
        Rectangle backgroundRectangle = new Rectangle(50 - 5 - 45 * energyPercentage, 10, backgroundColor);

        HBox hBox = new HBox(rectangle, backgroundRectangle);

        return new VBox(imageView, hBox);
    }

    private VBox getPlantImageView(boolean jungle) {

        ImageView imageView = new ImageView(plant);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);

        return new VBox(imageView);
    }
}
