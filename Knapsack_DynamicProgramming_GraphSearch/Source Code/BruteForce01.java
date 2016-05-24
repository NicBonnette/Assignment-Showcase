import java.util.ArrayList;



/**
 * The 0-1 knapsack problem is, given a maximum weight that can be held by the knapsack
 * and a set of individual items of a given weight and value each, what is the highest
 * total value you can pack in the knapsack.
 *
 * This class finds the best value solution of the 0-1 knapsack problem using
 * a brute force method - calculating the value for every single combination
 * of items in a knapsack, and finding the highest value option.
 *
 * @author Nic
 *
 */
public class BruteForce01 {
	private ArrayList<Thing> things;//Thing objects that can be chosen to put in the knapsack
	//multiple copies are ignored!
	private int maxWeight;//maximum allowable weight of items in knapsack

	
	private int solution;
	private int clicker;//keeps track of the number of computations made
	
	public BruteForce01(int maxWeight, ArrayList<Thing> things) {
		super();
		this.maxWeight = maxWeight;
		this.things = things;
		this.clicker=0;
	}


	/*
	 * This is the main method of this class, it starts off a recursion to find the best
	 * value packing of the knapsack from the items in things, with the weight restriction
	 * maxWeight
	 *
	 * @return the number of computations made during the solution
	 *
	 * We are not required to return the make up of items to be added to the knapsack
	 * that achieve the maximal value
	 * 
	 * The maximum solution is retreivable via the report optimalsoluton method
	 */
	public int solve(){
		//starting at the first item in things, and initialising the weight so far and value so far,
		//recursively consider the effect of adding each item into the knapsack (or not)
		//the recursion passes back the best valid value (ie highest value that satisfies
		//the weight restriction)
		solution = solveRec(0, 0, 0);
		return clicker;
	}

	/**
	 * I think we have to do this stupidly, so _even though_ we could discount
	 * some of the recursion based on excessive weight, we have to do it anyway
	 * for the brute force method :-/
	 * @param index current item under consideration in the things array
	 * @param valueSoFar value of earlier decisions (up the decision branch)
	 * @param weightSoFar weight of earlier decisions (up the decision branch)
	 * @return the best knapsack value from this index down in the decision tree
	 */
	public int solveRec(int index, int valueSoFar, int weightSoFar){
		//if index is beyond the range of things, work out in the decision tree
		//branch was a valid path wrt the weight restriction, returning the value so far
		//if valid, or 0 otherwise
		clicker++;
		if(index == things.size()){
			clicker++;
			return (weightSoFar <= maxWeight)? valueSoFar : 0;
		}
		//if index is within range, then recurse down to find the maximum value
		//obtainable if the indexed item is included or if it is not included
		//gather up the returned results, finding the best one
		clicker +=2;
		int valueIfIn = solveRec(index + 1,
				valueSoFar + things.get(index).getValue(), weightSoFar + things.get(index).getWeight());
		int valueIfOut = solveRec(index + 1, valueSoFar, weightSoFar);
		clicker++;
		return Math.max(valueIfIn, valueIfOut);
	}

	
	/*
	 * returns the optimal value found in the filled matrix
	 */
	public int reportOptimalValue(){
		return solution;
	}
}
