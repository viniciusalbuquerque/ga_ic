package pack;

public class Main {

	private static final int NumberOfChromosomes = 100;
	private static final int NumberOfIterations = 2000;
	
	public static void main(String[] args) {

		GA algGen = new GA(NumberOfChromosomes, FitnessEvaluation.genes.length, 0.05, 0.8);
//		GA algGen = new GA(10, 10, 0.1, 0.8);
		algGen.start(NumberOfIterations);
		System.out.println();
		Chromosome best = algGen.getBestChromosome();
		
		
		for(Gene gene : best.getGenes()) {
			System.out.print(String.valueOf(gene.getValue()) + " ");
		}
		System.out.println();
		
		System.out.println(FitnessEvaluation.evaluate(best.getGenes()));
		
	}

}
