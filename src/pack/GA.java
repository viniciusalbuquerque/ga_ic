package pack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GA {

	private double mutationValue;
	private double crossoverValue;
	
//	private List<Chromosome> chromosomes;
	private Chromosome[] chromosomes;
	
	public GA(int numberOfChromosomes, int numberOfGenes, double mutationValue, double crossoverValue) {
		this.mutationValue = mutationValue;
		this.crossoverValue = crossoverValue;
		initializeChromosomes(numberOfChromosomes, numberOfGenes);
	}
	
	public void start(int numberOfIterations) {
		for(int i = 0; i < numberOfIterations; i++) {
			evaluateFitnesses();
			Chromosome[] crossChromosomes = crossover();
			mutate();
			this.chromosomes = selectIndividuals(crossChromosomes);
		}
		
	}
	
	private Chromosome[] selectIndividuals(Chromosome[] crossChros) {
//		Chromosome[] selected = selectNBest(this.chromosomes.length);
		List<Chromosome> chrs = Arrays.asList(this.chromosomes);
		chrs.addAll(chrs);
		return selectNBest((Chromosome[])chrs.toArray(), this.chromosomes.length);
	}
	
	private void mutate() {
		
		
		
	}
	
	//Método para escolha dos indivíduos para o crossOver a decidir;
	private Chromosome[] crossover() {
		ArrayList<Chromosome> crossChromosomes = new ArrayList<Chromosome>();
		
		Random random = new Random();
		//Passar quantos "melhores" quer usar para o crossOver
		Chromosome[] bestChromosomes = selectNBest(this.chromosomes,5); // valor fictício
		for(int i = 0; i < bestChromosomes.length; i++) {
			for(int j = 0; j < bestChromosomes.length; j++) {
				if (i != j) {
					Chromosome chr1 = bestChromosomes[i];
					Chromosome chr2 = bestChromosomes[j];
					int randRange = random.nextInt(chr1.getGenes().length);
					
					List<Gene> genes1 = Arrays.asList(Arrays.copyOfRange(chr1.getGenes(), 0, randRange));
					List<Gene> genes2 = Arrays.asList(Arrays.copyOfRange(chr2.getGenes(), randRange, chr2.getGenes().length));
		
					List<Gene> ch1 = genes1;
//					List<Gene> child2 = genes2;
					
					ch1.addAll(genes2);
					genes2.addAll(genes1);

					Chromosome child = new Chromosome();
					Gene[] genes = (Gene[]) ch1.toArray();
					child.setGenes(genes);
					crossChromosomes.add(child);
					
					Chromosome child2 = new Chromosome();
					Gene[] gns2 = (Gene[]) genes2.toArray();
					child2.setGenes(gns2);
					crossChromosomes.add(child2);
				}								
			}
		}
		
		return (Chromosome[]) crossChromosomes.toArray();
	}
	
	private Chromosome[] selectNBest(Chromosome[] chromosomes, int nBest) {
		Chromosome[] chrsSorted = chromosomes.clone();
		Arrays.sort(chrsSorted, new ChromosomeFitnessComparator());
		return Arrays.copyOfRange(chrsSorted, 0, nBest);
	}

	private void evaluateFitnesses() {
		for(Chromosome chr : this.chromosomes) {
			double fit = FitnessEvaluation.evaluate(chr.getGenes());
			chr.setFitness(fit);
		}
	}
	
	private void initializeChromosomes(int numberOfChromosomes, int numberOfGenes) {
		chromosomes = new Chromosome[numberOfChromosomes];
		for (Chromosome chromosome : this.chromosomes) {
			chromosome = new Chromosome();
			chromosome.initializeChromosome(numberOfGenes);
		}
	}
	
}
