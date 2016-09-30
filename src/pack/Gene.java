package pack;

public class Gene {
	
	private int value;
	private int numberOfBits;
	private String valueBits;
	
	public Gene(int value) {
		this.value = value;
//		this.numberOfBits = numberOfBits;
//		bitValue();
	}
	
//	private void bitValue() {
//		this.valueBits = Integer.toBinaryString(this.value);
//		while(this.valueBits.length() < this.numberOfBits) {
//			this.valueBits = "0" + this.valueBits;
//		}
//	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
}
