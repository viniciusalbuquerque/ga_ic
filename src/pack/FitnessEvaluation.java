package pack;

public class FitnessEvaluation {

	public static Gene[] genes = {new Gene(2), new Gene(3), new Gene(1), new Gene(4), new Gene(0), new Gene(5)};
	
	public static double evaluate(Gene[] chromosome) {
		int dist = 0;
		for(Gene gene : chromosome) {
			for(Gene thisGene: genes) {
				if(gene.getValue() != thisGene.getValue()) {
					dist++;
				} else {
					break;
				}
			}
		}
		return dist;
	}
	
}
