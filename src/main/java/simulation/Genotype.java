package simulation;

import java.util.Random;

public class Genotype {

    private int[] genes = new int[32];

    public Genotype() {

        Random rng = new Random();

        for (int i = 0; i < 32; i++) {
            genes[i] = rng.nextInt(7);
        }
    }

    public int[] getGenes() {
        return this.genes;
    }

}
