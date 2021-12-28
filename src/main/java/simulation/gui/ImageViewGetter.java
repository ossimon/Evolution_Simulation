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
    private final Image[] images;
    private final int cellWidth;
    private final int cellHeight;

    public ImageViewGetter(WorldMap map, int cellWidth, int cellHeight) throws FileNotFoundException {

        this.map = map;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;

        images = new Image[18];
        String directoryName = "src/main/resources/";
        String[] fileNames = {"left", "upLeft", "up", "upRight", "right", "downRight", "down", "downLeft", "plant"};
        String[] terrainNames = {"Swamp", "Jungle"};
        String extensionName = ".png";

        int i = 0;

        for (String terrainName: terrainNames) {
            for (String fileName: fileNames) {
                String fullPath = directoryName + fileName + terrainName + extensionName;
                images[i] = new Image(new FileInputStream(fullPath));
                i += 1;
            }
        }
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
            return getBackgroundImageView(jungle);
        }
    }

    private VBox getAnimalImageView(MapDirection direction, double energy, boolean jungle) {

        Image image;
        int imageIndex;
        if (!jungle) {
            imageIndex = switch (direction) {
                case NORTH -> 2;
                case NORTHWEST -> 1;
                case NORTHEAST -> 3;
                case SOUTH -> 6;
                case SOUTHWEST -> 7;
                case SOUTHEAST -> 5;
                case WEST -> 0;
                case EAST -> 4;
            };
        }
        else {
            imageIndex = switch (direction) {
                case NORTH -> 11;
                case NORTHWEST -> 10;
                case NORTHEAST -> 12;
                case SOUTH -> 15;
                case SOUTHWEST -> 16;
                case SOUTHEAST -> 14;
                case WEST -> 9;
                case EAST -> 13;
            };
        }
        image = images[imageIndex];

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(cellWidth);
        imageView.setFitHeight(cellHeight);

        double energyPercentage = energy / 1000;
        if (energyPercentage > 1.0) energyPercentage = 1.0;
        else if (energyPercentage < 0.0) energyPercentage = 0;

        double red = 1.0 - energyPercentage;
        double green = 0.0 + energyPercentage;

        Color color = new Color(red, green, 0, 1);
        Rectangle rectangle = new Rectangle(cellWidth * energyPercentage, cellHeight * 0.16, color);

        Color backgroundColor = Color.BLACK;
        Rectangle backgroundRectangle = new Rectangle(cellWidth * (1.0 - energyPercentage), cellHeight * 0.16, backgroundColor);

        HBox hBox = new HBox(rectangle, backgroundRectangle);

        return new VBox(imageView, hBox);
    }

    private VBox getPlantImageView(boolean jungle) {

        Image image;
        if (jungle) {
            image = images[17];
        }
        else {
            image = images[8];
        }

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(cellWidth);
        imageView.setFitHeight(cellHeight);

        Color color;
        if (jungle) {
            color = new Color(0.768, 1.0, 0.055, 1.0);
        }
        else {
            color = new Color(0.822, 0.542, 0.382, 1.0);
        }

        Rectangle rectangle = new Rectangle(cellWidth, cellHeight * 0.16, color);
        return new VBox(imageView, rectangle);
    }

    private VBox getBackgroundImageView(boolean jungle) {

        Color color;
        if (jungle) {
            color = new Color(0.768, 1.0, 0.055, 1.0);
        }
        else {
            color = new Color(0.822, 0.542, 0.382, 1.0);
        }

        Rectangle rectangle = new Rectangle(cellWidth, cellHeight * 1.16, color);
        return new VBox(rectangle);
    }
}
