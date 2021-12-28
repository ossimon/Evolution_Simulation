package simulation;

import org.junit.jupiter.api.Test;

public class SortedAnimalListTest {

    @Test
    public void addTest() {
        SortedAnimalList sortedAnimalList = new SortedAnimalList();
        WorldMap map = new WorldMap(10, 10, 10, true);

        sortedAnimalList.add(new Animal(map, new Vector2d(1, 1), 1));
        sortedAnimalList.add(new Animal(map, new Vector2d(1, 1), -1));
        sortedAnimalList.add(new Animal(map, new Vector2d(1, 1), 2));
        sortedAnimalList.add(new Animal(map, new Vector2d(1, 1), -4));
        sortedAnimalList.add(new Animal(map, new Vector2d(1, 1), -4));
        sortedAnimalList.add(new Animal(map, new Vector2d(1, 1), 2));

        System.out.println(sortedAnimalList);
    }
}
