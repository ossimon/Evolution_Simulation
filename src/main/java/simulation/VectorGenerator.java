package simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VectorGenerator {

    public static List<Vector2d> generateVectors(WorldMap map, Vector2d startBoundary, Vector2d endBoundary, int amount) {

        Vector2d position;
        Random rng = new Random();
        List<Vector2d> emptyCells = new ArrayList<>();
        System.out.println(startBoundary);
        System.out.println(endBoundary);

        for (int x = startBoundary.x; x <= endBoundary.x; x++) {
            for (int y = startBoundary.y; y <= endBoundary.y; y++) {

                position = new Vector2d(x, y);
                if (map.objectAt(position) == null) {
                    emptyCells.add(position);
                }
            }
        }

        int n = emptyCells.size();
        if (amount > n) {
            return emptyCells;
        }

        List<Vector2d> result = new ArrayList<>();

        for (int i = n; i > n - amount; i--) {
            int index = rng.nextInt(i);
            position = emptyCells.get(index);
            emptyCells.remove(position);
            result.add(position);
        }

        return result;
    }
}
