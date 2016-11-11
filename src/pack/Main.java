package pack;

import java.util.Arrays;

public class Main {

	private static final int NumberOfChromosomes = 100;
	private static final int NumberOfIterations = 2000;
	
	public static void main(String[] args) {

		GA algGen = new GA(NumberOfChromosomes, FitnessEvaluation.genes.length, 0.1, 0.8);
//		GA algGen = new GA(10, 10, 0.1, 0.8);
		algGen.start(NumberOfIterations);

		
		Chromosome[] chrs = algGen.getChromosomes();
		Arrays.sort(chrs, new ChromosomeFitnessComparator());
		
		for(Gene gene : chrs[0].getGenes()) {
			System.out.print(String.valueOf(gene.getValue()) + " ");
		}
		System.out.println();
		System.out.println(chrs[0].getFitness());
		System.out.println(FitnessEvaluation.checkRightness(chrs[0].getGenes()));
		
//		for(int i = 0; i < algGen.getChromosomes().length; i++) {
//			Chromosome chr = algGen.getChromosomes()[i];
//			for(int j = 0; j < chr.getGenes().length; j++) {
//				Gene g = chr.getGenes()[j];
//				System.out.print(String.valueOf(g.getValue()) + " ");
//			}
//			System.out.println();
//		}
		
	}

}
