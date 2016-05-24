import java.util.ArrayList;
import java.util.Arrays;

/**
 * this class is used to represent a node in a graph search for the 0-N knapsack problem
 *
 * @author Nic
 *
 */
public class GSMnode implements Comparable<GSMnode>  {
	//two nodes are the same if they contain the same things, or conversely if the same things are _not_
	//in them - we use thingsLeft here
	private final int[] thingsLeft;//an array keeping track of how many of each thing in the thing array are still available
	//to be selected to add into the knapsack (ie aren't in this node yet!)

	private final ArrayList<Thing> things;//all of the things that are available for this knapsack problem - should
	//be ordered in decreasing value to weight ratio
	private final int value;//value of the goods in this node
	private final int weight;//weight of the goods in this node
	private final int capacity;//capacity of the knapsack
	private final double potential;//an upper bound on the value of all nodes reachable from this one.

private int clicksForPotential;//records number of computations made during the calculation of the Potential

	public GSMnode(ArrayList<Thing> things,	int[] thingsLeft, int value, int weight, int capacity) {
		super();
		this.things = things;
		this.thingsLeft = thingsLeft;
		this.value = value;
		this.weight = weight;
		this.capacity = capacity;
		this.potential = maxFromHere();
	}

	//the method to determine the upper bound on knapsack solutions accessible from this node
	public double maxFromHere(){
		double futureWeight = weight;
		double futureValue = value;
		int index = 0;
		int clicker = 0;
		while(futureWeight<capacity && index < thingsLeft.length){
			clicker += 5;//3 data accesses plus two comparisons
			Thing nextThing = things.get(index);
			double nextWeight = nextThing.getWeight();
			int nextNum = thingsLeft[index];
			double numToFill = (capacity - weight)/nextWeight;
			double numToAdd = Math.min(numToFill, nextNum);
			futureWeight = futureWeight + numToAdd*nextWeight;
			futureValue = futureValue + numToAdd*nextThing.getValue();
			index++;
		}
		clicker += 2;//2 final comparisons 
		
		clicksForPotential = clicker;
		return futureValue;		
	}

	public double getPotential(){
		return potential;
	}

	public int[] getThingsLeft() {
		return thingsLeft;
	}

	public int getValue() {
		return value;
	}

	public int getWeight() {
		return weight;
	}

	public int getClicksForPotential() {
		return clicksForPotential;
	}

	public void setClicksForPotential(int clicksForPotential) {
		this.clicksForPotential = clicksForPotential;
	}

	@Override
	public int compareTo(GSMnode o) {
		//we want the natural order to have highest potential be the lowest in the natural order
		//(so that the priority queue in the graph solver will dequeue the node with the highest potential)
		double otherPotential = o.getPotential();
		if(this.potential == otherPotential){return 0;}
		if(this.potential > otherPotential){return -1;}
		return 1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(thingsLeft);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GSMnode other = (GSMnode) obj;
		if (!Arrays.equals(thingsLeft, other.thingsLeft))
			return false;
		return true;
	}


	public String toString(){
		String ret = "";
		for(int i = 0; i<things.size(); i++){
			ret = ret + thingsLeft[i];
		}
		ret = ret + " v:" + value + " w:" + weight + " p:" +potential;
		return ret;
	}
}
