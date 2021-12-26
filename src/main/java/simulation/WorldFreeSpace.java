package simulation;

import simulation.Vector2d;

import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class WorldFreeSpace {

    private final Set<Vector2d> freeSpace = new TreeSet<>();
    private final Random rng = new Random();

    public WorldFreeSpace(int width, int height){

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                System.out.println("");
            }
        }
    }

    public void occupy(Vector2d position) {

        freeSpace.add(position);
    }

}
