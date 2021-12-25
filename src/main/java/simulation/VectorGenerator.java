package simulation;

import java.util.Random;

import static java.lang.Math.sqrt;

public class VectorGenerator {

    public static Vector2d[] generateVectors(Vector2d startBoundary, Vector2d endBoundary, int amount) {

        int xBoundary = endBoundary.x - startBoundary.x + 1;
        int yBoundary = endBoundary.y - startBoundary.y + 1;

        Vector2d pos;
        Random rng = new Random();
        Vector2d[] vector2ds = new Vector2d[amount];

        for (int i = 0; i < amount; i++) {
//            do {
//                pos = new Vector2d(rng.nextInt(boundary + 1), rng.nextInt(boundary + 1));
//            } while (!this.canMoveTo(pos));
            pos = new Vector2d(startBoundary.x + rng.nextInt(xBoundary + 1), startBoundary.y + rng.nextInt(yBoundary + 1));
            vector2ds[i] = pos;
        }

        return vector2ds;
    }
}
