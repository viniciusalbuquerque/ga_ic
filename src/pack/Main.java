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
		
//		Chromosome[] chrs = algGen.getChromosomes();
//		Arrays.sort(chrs, new ChromosomeFitnessComparator());
//		
//		for(Gene gene : chrs[0].getGenes()) {
//			System.out.print(String.valueOf(gene.getValue()) + " ");
//		}
//		System.out.println();
//		System.out.println(chrs[0].getFitness());
//		System.out.println(FitnessEvaluation.checkRightness(chrs[0].getGenes()));
//		
////		for(int i = 0; i < algGen.getChromosomes().length; i++) {
////			Chromosome chr = algGen.getChromosomes()[i];
////			for(int j = 0; j < chr.getGenes().length; j++) {
////				Gene g = chr.getGenes()[j];
////				System.out.print(String.valueOf(g.getValue()) + " ");
////			}
////			System.out.println();
////		}
//		System.out.println();
//		for(Gene gene : FitnessEvaluation.genes) {
//			System.out.print(String.valueOf(gene.getValue()) + " ");	
//		}
		
		
//		8 0 8 8 6 8 2 8 8 3
		
//		Gene[] genes = {new Gene(8),new Gene(0),new Gene(8),new Gene(8),new Gene(6),new Gene(8),new Gene(2),new Gene(8),new Gene(8),new Gene(3)};
		
//		FitnessEvaluation.evaluate(genes);
	}

}
