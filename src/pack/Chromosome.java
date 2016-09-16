package pack;

public class Chromosome {

	private Gene[] genes;
	private double fitness;
	
	public void initializeChromosome(int numberOfGenes) {
		genes = new Gene[numberOfGenes];
		initializeGenes();
	}
	
	private void initializeGenes() {
		for (Gene gene : this.genes) {
			gene = new Gene();
		}
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
