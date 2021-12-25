package simulation;

import java.util.Arrays;
import java.util.Random;

public class Genotype {

    private int[] genes = new int[32];

    public Genotype() {

        Random rng = new Random();

        for (int i = 0; i < 32; i++) {
            genes[i] = rng.nextInt(8);
        }
    }

    public Genotype(int gene) {

        for (int i = 0; i < 32; i++) {
            genes[i] = gene;
        }
    }

    public Genotype(int energy1, int energy2, Genotype genotype1, Genotype genotype2) {

        Random rng = new Random();
        int side = rng.nextInt(2);

        if (side == 1) {
            int energyHolder = energy1;
            energy1 = energy2;
            energy2 = energyHolder;

            Genotype genotypeHolder = genotype1;
            genotype1 = genotype2;
            genotype2 = genotypeHolder;
        }

        float ratio = (float) energy1 / (energy1 + energy2);
        float leftGenesNumber = ratio * 32;

        for (int i = 0; i < leftGenesNumber; i++) {
            genes[i] = genotype1.genes[i];
        }
        for (int i = (int) leftGenesNumber; i < 32; i++) {
            genes[i] = genotype2.genes[i];
        }

    }

    public int[] getGenes() {
        return this.genes;
    }

    @Override
    public String toString () {

        return Arrays.toString(genes);
    }

}
