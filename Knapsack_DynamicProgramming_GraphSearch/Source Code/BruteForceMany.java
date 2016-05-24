import java.util.ArrayList;


/**
 * The 0-N knapsack problem is, given a maximum weight that can be held by the knapsack
 * and a multiset(each item can have a finite number of copies) of individual items of a
 * given weight and value each, what is the highest total value you can pack in the knapsack.
 *
 * This class finds the best value solution of the 0-N knapsack using a brute force method.
 * One way of finding a solution is to make a direct translation into a 0-1 problem, this
 * method should be a little better because it does not need to consider permutations within
 * copies of the _same_ item - it just considers the possibility that you have M copies (0=<M<=N)
 *
 * @author Nic
 *
 */
public class BruteForceMany {
	private ArrayList<Thing> things;//Thing objects that can be chosen to put in the knapsack
	private double maxWeight;//maximum allowable weight of items in knapsack

	private int solution;
	private int clicker;//amount of computations made during exploration
	
	
	public BruteForceMany(double maxWeight, ArrayList<Thing> things) {
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
	 * @return the maximum value obtainable.
	 *
	 * We are not required to return the make up of items to be added to the knapsack
	 * that achieve the maximal value
	 */
	public int solve(){
		//starting at the first item in things, and initialising the weight so far and value so far,
		//recursively consider the effect of adding each possible multiple of the item into the knapsack (or not)
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
		//if index is beyond the range of things, work out if the decision tree
		//branch was a valid path wrt the weight restriction, returning the value so far
		//if valid, or 0 otherwise
		if(index == things.size()){
			clicker++;
			return (weightSoFar <= maxWeight)? valueSoFar : 0;
		}
		//if index is within range, then recurse down to find the maximum value
		//obtainable if the indexed item is included any of the multiple times that are possible
		//gather up the returned results, finding the best one
		int bestValue = 0;
		for (int i = 0; i<= things.get(index).getNum(); i++){
			clicker +=3;
			int bestFromDecision = solveRec(index +1, valueSoFar + things.get(index).getValue()*i,
				    weightSoFar + things.get(index).getWeight()*i) ;
			clicker++;
			if( bestFromDecision > bestValue){
				bestValue = bestFromDecision;
			}
		}
		return bestValue;
	}

	/*
	 * returns the optimal value found in the filled matrix
	 */
	public int reportOptimalValue(){
		return solution;
	}
}
