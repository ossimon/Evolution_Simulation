package simulation;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Plant implements IMapElement, Comparable {

    private Vector2d position;
    private ImageView imageView;

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
