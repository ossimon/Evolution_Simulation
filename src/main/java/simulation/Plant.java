package simulation;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Plant implements IMapElement {

    private Vector2d position;
    private ImageView imageView;

    public Plant (Vector2d position) {

        this.position = position;
    }

    @Override
    public ImageView getImageView() {


        Image image = null;
        try {
            image = new Image(new FileInputStream("src/main/resources/grass.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        imageView = new ImageView(image);

        imageView.setFitWidth(40);
        imageView.setFitHeight(40);

        return imageView;
    }

    public Vector2d getPosition() {
        return this.position;
    }
}
