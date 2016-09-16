package pack;

import java.util.Comparator;

public class ChromosomeFitnessComparator implements Comparator<Chromosome> {
	@Override
	public int compare(Chromosome c1, Chromosome c2) {
		return c1.getFitness() < c2.getFitness() ? -1 : c1.getFitness() == c2.getFitness() ? 0 : 1; 
	}

}
