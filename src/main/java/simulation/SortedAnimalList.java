package simulation;

import java.util.ArrayList;

public class SortedAnimalList extends ArrayList<Animal>{

    @Override
    public boolean add(Animal animal) {

        super.add(animal);

        int i = this.size() - 1;

        if (i > 0) {
            Animal animal1;
            Animal animal2;

            while (true) {

                animal1 = get(i - 1);
                animal2 = get(i);

                if (animal1.getEnergy() < animal2.getEnergy()) {
                    add(i - 1, animal2);
                    add(i, animal1);
                }
                else break;

                if (i > 1) i--;
                else break;
            }
        }
        return true;
    }

}
