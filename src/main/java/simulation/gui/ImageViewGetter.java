package simulation.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import simulation.MapDirection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImageViewGetter {

    private final Image left;
    private final Image upLeft;
    private final Image up;
    private final Image upRight;
    private final Image right;
    private final Image downRight;
    private final Image down;
    private final Image downLeft;
    private final Image plant;


    public ImageViewGetter() throws FileNotFoundException {

        this.left = new Image(new FileInputStream("src/main/resources/left.png"));
        this.upLeft = new Image(new FileInputStream("src/main/resources/upleft.png"));
        this.up = new Image(new FileInputStream("src/main/resources/up.png"));
        this.upRight = new Image(new FileInputStream("src/main/resources/upright.png"));
        this.right = new Image(new FileInputStream("src/main/resources/right.png"));
        this.downRight = new Image(new FileInputStream("src/main/resources/downright.png"));
        this.down = new Image(new FileInputStream("src/main/resources/down.png"));
        this.downLeft = new Image(new FileInputStream("src/main/resources/downleft.png"));
        this.plant = new Image(new FileInputStream("src/main/resources/plant.png"));
    }

    public ImageView getAnimalImageView(MapDirection direction) {

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

        return imageView;
    }
    public ImageView getPlantImageView() {

        Image image = plant;
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);

        return imageView;
    }
}
