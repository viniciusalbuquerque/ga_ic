package pack;

public class FitnessEvaluation {

	public static Gene[] genes = {new Gene(8), new Gene(6), new Gene(1), new Gene(4), new Gene(0), new Gene(5), new Gene(7), new Gene(3), new Gene(2), new Gene(9), new Gene(13), new Gene(12), new Gene(11), new Gene(10), new Gene(9), new Gene(13), new Gene(12), new Gene(11), new Gene(10), new Gene(8), new Gene(6), new Gene(1), new Gene(4), new Gene(0) };
//	public static Gene[] genes = {new Gene(2), new Gene(0), new Gene(1)};
	
	public static double checkRightness(Gene[] resultGenes) {
		double result = 0;
		int count = 0;
		for(int i = 0; i < resultGenes.length; i++) {
			if (resultGenes[i].getValue() == genes[i].getValue()) {
				count++;
			}
		}
		
		result = (double) count/genes.length;
		
		return result;
	}
	
	private static int findIndexOfGene(Gene gs) {
		for(int i = 0; i < genes.length; i++) {
			if (gs.getValue() == genes[i].getValue()) {
				return i;
			}
		}
		return -1;
	}
	
	private static int findIndexOfGene(Gene[] gens, int index) {
		if (genes[index].getValue() == gens[index].getValue()) {
			return index;
		} else {
			for(int i = 0; i < gens.length; i++) {
				if (genes[index].getValue() == gens[i].getValue()) {
					return i;
				}
			}
			return -1;
		}
	}
	
	public static double evaluate(Gene[] chromosome) {
		double dist = 0;
		
		for (int i = 0; i < chromosome.length; i++) {
			if (chromosome[i].getValue() != genes[i].getValue()) {
				dist++;
			}
		}
		
		return dist;
	}
	
//	public static double evaluate(Gene[] chromosome) {
//		int dist = 0;
//		for(int i = 0; i < chromosome.length; i++) {
//			int index = findIndexOfGene(chromosome[i]);
////			int index = findIndexOfGene(chromosome, i);
//			if (index == -1) {
//				dist += genes.length;
//			} else {
//				dist += Math.abs(i - index);	
//			}
//			
////			if (index > i) {
////				dist += (index - i);
////			} else {
////				if(index == -1) {
////					dist += genes.length;
////				} else {
////					dist += (i - index);	
////				}
////			}
//		}
//		
//		return dist;
//	}
	
	
}
