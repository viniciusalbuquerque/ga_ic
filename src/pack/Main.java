package pack;

public class Main {

	private static final int NumberOfChromosomes = 10;
	private static final int NumberOfIterations = 10;
	
	public static void main(String[] args) {

		GA algGen = new GA(10, 10, 0.1, 0.8);
		algGen.start(10000);
		
	}

}
