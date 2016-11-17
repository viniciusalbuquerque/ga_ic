package pack;

import java.util.Arrays;
import java.util.Collections;

public class Chromosome {

	private Gene[] genes;
	private double fitness;
	
	public Chromosome() {}
	
	public Chromosome(Chromosome chromosome) {
		this.genes = chromosome.genes;
		this.fitness = chromosome.fitness;
	}
	
	public void initializeChromosome(int numberOfGenes) {
		genes = new Gene[numberOfGenes];
		initializeGenes();
	}
	
	private void initializeGenes() {
		for(int i = 0; i < genes.length; i++) {
			this.genes[i] = new Gene(i);
		}
		Collections.shuffle(Arrays.asList(genes));
		
	}
	
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	public Gene[] getGenes() {
		return genes;
	}
	
	public double getFitness() {
		return fitness;
	}
	
	public void setGenes(Gene[] genes) {
		this.genes = genes;
	}
	
}
