/**
 * A thing is an object that can be placed in a knapsack, with a specific value
 * @author Nic
 *
 */
public class Thing implements Comparable<Thing>{
	private final int value;
	private final int weight;
	private int number;

	private final String name;

	public Thing(String name, int value, int weight, int number) {
		super();
		this.name = name;
		this.value = value;
		this.weight = weight;
		this.number = number;
	}

	public int getValue() {
		return value;
	}
	public int getWeight() {
		return weight;
	}

	public String getName() {
		return name;
	}
	public int getNum(){
		return number;
	}
	public void increaseNum(int plusMe){
		this.number += plusMe;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		long temp;
		temp = Double.doubleToLongBits(value);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(weight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (getClass() == obj.getClass()){
			Thing other = (Thing) obj;
			return (other.getName().equals(this.name) && (other.getValue() == this.value)
					&& (other.getWeight() == this.weight) )? true : false;
		}
		return false;
	}

	@Override
	public String toString() {
		return name + "(v:" + value +",w:" + weight +")";
	}

	

	@Override
	public int compareTo(Thing o) {
		//we want the natural order to have the most value for a unit weight be lower on the order (first!)
				double thisVPW = (double)value/(double)weight;
				double otherVPW = (double)o.getValue()/(double)o.getWeight();
				if(thisVPW == otherVPW){return 0;}
				if(thisVPW > otherVPW){return -1;}
				return 1;
	}


}
