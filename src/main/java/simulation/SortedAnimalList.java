package simulation;

import java.util.ArrayList;

public class SortedAnimalList extends ArrayList<Animal>{

    @Override
    public boolean add(Animal animal) {

        super.add(animal);

        int i = this.size();

        if (i > 1) {
            Animal animal1 = get(i - 1);
            Animal animal2 = get(i);
            Animal holder;

            while (i > 0 && animal1.getEnergy() < animal2.getEnergy()) {

                holder = animal1;
                add(i - 1, animal2);
                add(i, holder);
                i--;
                animal1 = get(i - 1);
                animal2 = get(i);
            }
        }
        return true;
    }

}
