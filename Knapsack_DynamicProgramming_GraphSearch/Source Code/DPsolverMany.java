import java.util.ArrayList;

/**
 * This class is used to solve a 0-N knapsack problem using a dynamic programming approach
 * The general principle of the algorithm is to build up a table of partial solutions
 * based on integer increments in the weight capacity of the knapsack in one axis,
 * and available items on the other axis.
 * 
 * We are only required to find the maximum value possible to fit in the knapsack, not to illustrate
 * the items that produce that optimal value. 
 * 
 * The simplest way to do the 0-N problem is to translate it to a 0-1 problem by making individuallised
 * separate copies of each multiple item. 
 * 
 * @author Nic
 *
 */
public class DPsolverMany {

	private ArrayList<Thing> things;//the things that are allowed in the knapsack
	private ArrayList<Thing> indThings;//the things in things but separated out into
	//individual items

	private DPsolver01 translated;//the translated problem 

	public DPsolverMany(int capacity, ArrayList<Thing> things) {
		super();
		this.things = things;
		individualiseThings();
		this.translated = new DPsolver01(capacity, indThings);
	}

	private void individualiseThings(){
		indThings = new ArrayList<Thing>();
		for(Thing thing : things){
			int num = thing.getNum();
			if (num == 1){
				indThings.add(thing);
			}
			else{
				String name = thing.getName();
				int value = thing.getValue();
				int weight = thing.getWeight();
				for(int i = 0; i<num;i++){
					indThings.add(new Thing(name+i, value,weight,1));
				}
			}
		}
		
	}

	/*
	 * fills in the DP matrix - ie does the dp algorithm... but farms the work out to the translated solver
	 * the return value is the number of computations performed by the algorithm
	 */
	public int solve(){
		return translated.solve();
	}


	/*
	 * returns the optimal value found by the translated solver
	 */
	public int reportOptimalValue(){
		return translated.reportOptimalValue();
	}
}


