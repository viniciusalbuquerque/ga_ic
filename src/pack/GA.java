package pack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GA {

	private double mutationValue;
	private double crossoverValue;
	
//	private int numberOfBits = 0;
	
//	private List<Chromosome> chromosomes;
	private Chromosome[] chromosomes;
	
	private Chromosome bestChromosome;
	
	public GA(int numberOfChromosomes, int numberOfGenes, double mutationValue, double crossoverValue) {
		this.mutationValue = mutationValue;
		this.crossoverValue = crossoverValue;
		initializeChromosomes(numberOfChromosomes, numberOfGenes);
	}
	
	/**
	 * Método responsável por gerenciar o algoritmo nas iterações.
	 * @param numberOfIterations
	 */
	public void start(int numberOfIterations) {
		
		double factor = this.mutationValue/(numberOfIterations/10);
		this.chromosomes = evaluateFitnesses(this.chromosomes);
		
		int numIterationsWithoutChanging = 0;
		
		double mutValue = this.mutationValue;
		
		for(int i = 0; i < numberOfIterations; i++) {
			
			Chromosome[] crossChromosomes = crossover();
			crossChromosomes = mutate(crossChromosomes);
			crossChromosomes = evaluateFitnesses(crossChromosomes);
			this.chromosomes = selectIndividuals(crossChromosomes);
			if(this.mutationValue > 0) {
				this.mutationValue -= factor;	
			}
			
			
			Chromosome[] newc = this.chromosomes.clone();
			Arrays.sort(newc, new ChromosomeFitnessComparator());
			System.out.print(String.valueOf(i + 1) + ": ");
			
//			for(Gene gene : newc[0].getGenes()) {
//				System.out.print(String.valueOf(gene.getValue()) + " ");
//			}
//			System.out.println("   " + String.valueOf(newc[0].getFitness()));
			
			
			if (i == 0) {
				this.bestChromosome = newc[0];
//				System.out.println(this.bestChromosome.getFitness());
			}
			
			if(newc[0].getFitness() < this.bestChromosome.getFitness()) {
//				System.out.println(this.bestChromosome.getFitness());
				this.bestChromosome = new Chromosome(newc[0]);
//				this.bestChromosome = newc[0];	
			} else {
//				System.out.println();
				numIterationsWithoutChanging++;
			}
			
			if (newc[0].getFitness() == 0) break;
			
			
			//Tentando saltar pra outro espaço de busca quando a solução fica presa (não funciona direito ainda)
			if(numIterationsWithoutChanging == numberOfIterations/10) {
				this.chromosomes = selectNRandomly(crossChromosomes, this.chromosomes.length);
				mutValue /= 2;
				this.mutationValue = mutValue;
				numIterationsWithoutChanging = 0;
			}
			
		}
		
	}
	
	/**
	 * Função responsável por selecionar os N melhores indivíduos no array passado como parâmetro.
	 * @param crossChros
	 * @return
	 */
	private Chromosome[] selectIndividuals(Chromosome[] crossChros) {
//		Chromosome[] selected = selectNBest(this.chromosomes.length);
		List<Chromosome> chrs = new ArrayList<Chromosome>(Arrays.asList(this.chromosomes.clone()));
		chrs.addAll(Arrays.asList(crossChros));
		Chromosome[] nBest = new Chromosome[chrs.size()];
		chrs.toArray(nBest);
		return selectNBest(nBest, this.chromosomes.length);
	}
	
	/**
	 * Função responsável por retornar o valor inteiro máximo do gene.
	 * @param genes
	 * @return
	 */
	private int getMaxGeneValue(Gene[] genes) {
		int maxValue = 0;
		for(Gene gene : genes) {
			if(gene.getValue() > maxValue) {
				maxValue = gene.getValue();
			}
		}
		return maxValue;
	}
	
	/**
	 * Função que iguala o número de bits para que a conversão seja possível.
	 * @param bitStr
	 * @param maxValueSize
	 * @return
	 */
	private String addZerosToBitStr(String bitStr, int maxValueSize) {
		while(bitStr.length() < maxValueSize) {
			bitStr = "0"+bitStr;
		}
		return bitStr;
	}
	
	
	/**
	 * Retorna um array de Genes em bits.
	 * @param genes
	 * @param maxValueSize
	 * @return
	 */
	private Gene[] getBitGenes(Gene[] genes, int maxValueSize) {
		Gene[] bitGenes = new Gene[genes.length*maxValueSize];
		int indexBitGenes = 0;
		for(Gene gene : genes) {
			String bitStr = Integer.toBinaryString(gene.getValue());
			bitStr = addZerosToBitStr(bitStr, maxValueSize);
			for(int i = 0; i < bitStr.length(); i++, indexBitGenes++) {
				bitGenes[indexBitGenes] = new Gene(Integer.parseInt(String.valueOf(bitStr.charAt(i))));
			}
		}
		return bitGenes;
	}
	
	/**
	 * Função responsável por fazer o flip dos bits randomicamente.
	 * @param bitGenes
	 * @return
	 */
	private Gene[] flipRandomly(Gene[] bitGenes) {
		Random random = new Random();
		int index = random.nextInt(bitGenes.length);
		if(bitGenes[index].getValue() == 0) {
			bitGenes[index].setValue(1);
		} else {
			bitGenes[index].setValue(0);	
		}
		return bitGenes;
	}
	
	/**
	 * Responsável por converter o array de bits em números inteiros.
	 * @param bitGenes
	 * @param maxValue
	 * @param maxValueSize
	 * @return
	 */
	private Gene[] getValoredGenes(Gene[] bitGenes, int maxValue, int maxValueSize) {
		Gene[] valoredGenes = new Gene[bitGenes.length/maxValueSize];
		int valoredGenesIndex = 0;
		
		String bitStr = "";
		for(Gene bitGene : bitGenes) {
			bitStr += String.valueOf(bitGene.getValue());
			if(bitStr.length() == maxValueSize) {
				valoredGenes[valoredGenesIndex] = new Gene(Integer.parseInt(bitStr, 2));
				if (valoredGenes[valoredGenesIndex].getValue() > maxValue) {
					valoredGenes[valoredGenesIndex].setValue(maxValue);
				}
				valoredGenesIndex++;
				bitStr = "";
			}
		}
		return valoredGenes;
	}
	
	/**
	 * Mutação.
	 */
	private Chromosome[] mutate(Chromosome[] chromosomes) {
//		int nMutations = (int)(this.chromosomes.length * mutationValue);
//		int nMutations = (int)(this.chromosomes[0].getGenes().length * mutationValue);
		for(int i = 0; i < chromosomes.length; i++) {
			Chromosome chromosome = chromosomes[i];
//		for(Chromosome chromosome : this.chromosomes) {
			Gene[] genes = chromosome.getGenes();
			int maxValue = getMaxGeneValue(genes);
			int maxValueSize = Integer.toBinaryString(maxValue).length();
			int nMutations = (int) (maxValueSize * genes.length * mutationValue);
			for(int j = 0; j < nMutations; j++) {
//				int maxValue = getMaxGeneValue(genes);
//				int maxValueSize = Integer.toBinaryString(maxValue).length();
				Gene[] bitGenes = getBitGenes(genes, maxValueSize);
				bitGenes = flipRandomly(bitGenes);
				genes = getValoredGenes(bitGenes, maxValue, maxValueSize);
			}	
			chromosome.setGenes(genes);
		}
		return chromosomes;
	}
	
	//Método para escolha dos indivíduos para o crossOver a decidir;
	private Chromosome[] crossover() {
		ArrayList<Chromosome> crossChromosomes = new ArrayList<Chromosome>();
		
		Random random = new Random();
		//Passar quantos "melhores" quer usar para o crossOver
		Chromosome[] bestChromosomes = selectNBest(this.chromosomes,(int)(this.chromosomes.length*this.crossoverValue)); // valor fictício
		for(int i = 0; i < bestChromosomes.length; i++) {
			for(int j = 0; j < bestChromosomes.length; j++) {
				if (i != j) {
					Chromosome chr1 = bestChromosomes[i];
					Chromosome chr2 = bestChromosomes[j];
					int randRange = random.nextInt(chr1.getGenes().length);
					
					List<Gene> genes1_first = new ArrayList<Gene>(Arrays.asList(Arrays.copyOfRange(chr1.getGenes(), 0, randRange)));
					List<Gene> genes1_second = (Arrays.asList(Arrays.copyOfRange(chr1.getGenes(), randRange, chr1.getGenes().length)));
					List<Gene> genes2_first = new ArrayList<Gene>(Arrays.asList(Arrays.copyOfRange(chr2.getGenes(), 0, randRange)));
					List<Gene> genes2_second = Arrays.asList(Arrays.copyOfRange(chr2.getGenes(), randRange, chr2.getGenes().length));
		
					genes1_first.addAll(genes2_second);
					genes2_first.addAll(genes1_second);

//					Gene[] genes = (Gene[]) genes1_first.toArray();
//					Gene[] gns2 = (Gene[]) genes2_first.toArray();
					Gene[] genes = new Gene[genes1_first.size()];
					Gene[] gns2 = new Gene[genes2_first.size()];
					
					genes1_first.toArray(genes);
					genes2_first.toArray(gns2);
					
//					for(int index = 0; index < genes1_first.size(); index++) {
//						genes[index] = genes1_first.get(index);
//						gns2[index] = genes2_first.get(index);
//					}
					
					Chromosome child = new Chromosome();
					Chromosome child2 = new Chromosome();
					
					child.setGenes(genes);
					crossChromosomes.add(child);
					
					child2.setGenes(gns2);
					crossChromosomes.add(child2);
				}								
			}
		}
		
		Chromosome[] childs = new Chromosome[crossChromosomes.size()];
		
		for(int i = 0; i < childs.length; i++) {
			childs[i] = crossChromosomes.get(i);
		}
		return childs;
//		return (Chromosome[]) crossChromosomes.toArray();
	}
	
	private Chromosome[] selectNBest(Chromosome[] chromosomes, int nBest) {
		Chromosome[] chrsSorted = chromosomes.clone();
		Arrays.sort(chrsSorted, new ChromosomeFitnessComparator());
		return Arrays.copyOfRange(chrsSorted, 0, nBest);
	}

	private Chromosome[] selectNRandomly(Chromosome[] chromosomes, int nRandom) {
		Chromosome[] chrRandomed = new Chromosome[nRandom];
		Random random = new Random();
		
		//Not checking if there is repeating chromosomes
		for(int i = 0; i < nRandom; i++) {
			int index = random.nextInt(chromosomes.length);
			chrRandomed[i] = chromosomes[index];
		}
		return chrRandomed;
	}
	
	private Chromosome[] evaluateFitnesses(Chromosome[] chros) {
		for(int i = 0; i < chros.length; i++) {
			double fit = FitnessEvaluation.evaluate(chros[i].getGenes());
			chros[i].setFitness(fit);
		}
		return chros;
//		for(Chromosome chr : this.chromosomes) {
//			double fit = FitnessEvaluation.evaluate(chr.getGenes());
//			chr.setFitness(fit);
//		}
	}
	
	private void initializeChromosomes(int numberOfChromosomes, int numberOfGenes) {
		chromosomes = new Chromosome[numberOfChromosomes];
		for(int i = 0; i < this.chromosomes.length; i++) {
			chromosomes[i] = new Chromosome();
			chromosomes[i].initializeChromosome(numberOfGenes);
//			for(int j = 0; j < chromosomes[i].getGenes().length; j++) {
//				System.out.print(String.valueOf(chromosomes[i].getGenes()[j].getValue()) + " ");
//			}
//			System.out.println();
		}
		
//		System.out.println();
//		for (Chromosome chromosome : this.chromosomes) {
//			chromosome = new Chromosome();
//			chromosome.initializeChromosome(numberOfGenes);
//		}
	}

	public Chromosome[] getChromosomes() {
		return this.chromosomes;
	}
	
	public Chromosome getBestChromosome() {
		return this.bestChromosome;
	}
	
}
