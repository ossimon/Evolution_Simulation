package simulation;

import org.junit.jupiter.api.Test;

public class SortedAnimalListTest {

    @Test
    public void addTest() {
        SortedAnimalList sortedAnimalList = new SortedAnimalList();
        WorldMap map = new WorldMap(new int[]{10, 10, 10, 10, 10, 10, 10, 10, 10}, true);

        sortedAnimalList.add(new Animal(map, new Vector2d(1, 1), 1, 1));
        sortedAnimalList.add(new Animal(map, new Vector2d(1, 1), -1, 1));
        sortedAnimalList.add(new Animal(map, new Vector2d(1, 1), 2, 1));
        sortedAnimalList.add(new Animal(map, new Vector2d(1, 1), -4, 1));
        sortedAnimalList.add(new Animal(map, new Vector2d(1, 1), -4, 1));
        sortedAnimalList.add(new Animal(map, new Vector2d(1, 1), 2, 1));

        System.out.println(sortedAnimalList);
    }
}
