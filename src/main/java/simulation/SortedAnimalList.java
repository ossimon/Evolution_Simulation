package simulation;

import java.util.ArrayList;

public class SortedAnimalList extends ArrayList<Animal>{

    @Override
    public boolean add(Animal animal) {

        super.add(animal);
        sortLast();
        return true;
    }

    private void sortLast() {


        if (!this.isEmpty()) {

            int lastIndex = this.size() - 1;
            Animal animal = this.get(lastIndex);
            int energy = animal.getEnergy();
            int left = 0;
            int right = lastIndex;
            int middle = 0;

            while (left < right) {
                middle = (left + right) / 2;
                if (this.get(middle).getEnergy() >= energy) left = middle + 1;
                else right = middle;
            }

            if (this.get(middle).getEnergy() <= energy) {
                this.add(middle, animal);
            } else {
                this.add(middle + 1, animal);
            }
            remove(lastIndex + 1);
        }
    }
}
