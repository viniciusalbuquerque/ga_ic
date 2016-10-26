package pack;

public class FitnessEvaluation {

	public static Gene[] genes = {new Gene(2), new Gene(3), new Gene(1), new Gene(4), new Gene(0), new Gene(5)};
	
	private static int findIndexOfGene(Gene gene) {
		for(int i = 0; i < genes.length; i++) {
			if (gene.getValue() == genes[i].getValue()) {
				return i;
			}
		}
		return -1;
	}
	
	public static double evaluate(Gene[] chromosome) {
		int dist = 0;
		for(int i = 0; i < chromosome.length; i++) {
			int index = findIndexOfGene(chromosome[i]);
			if (index > i) {
				dist += (index - i);
			} else {
				if(index == -1) {
					dist += genes.length;
				} else {
					dist += (i - index);	
				}
			}
		}
		
		
//		for(Gene gene : chromosome) {
//			for(Gene thisGene: genes) {
//				if(gene.getValue() != thisGene.getValue()) {
//					dist++;
//				} else {
//					break;
//				}
//			}
//		}
		return dist;
	}
	
}
