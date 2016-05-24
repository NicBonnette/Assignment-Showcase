import java.util.ArrayList;

/**
 * This class is used to solve a 0-1 knapsack problem using a dynamic programming approach
 * The general principle of the algorithm is to build up a table of partial solutions
 * based on integer increments in the weight capacity of the knapsack in one axis,
 * and available items on the other axis.
 * 
 * We are only required to find the maximum value possible to fit in the knapsack, not to produce
 * the items that produce that optimal value. 
 * 
 * @author Nic
 *
 */
public class DPsolver01 {

	private int[][] theMatrix;	//the matrix to be filled out with optimal solution values
	//while solving the knapsack problem

	private ArrayList<Thing> things;//the things that are allowed in the knapsack
	private int capacity;//the maximum weight allowed in the knapsack 


	public DPsolver01(int capacity, ArrayList<Thing> things) {
		super();
		this.things = things;
		this.capacity = capacity;
		this.theMatrix = new int[things.size()][capacity+1];
		//the +1 is in there as we need cells to represent
		//when the weight limit is zero - it is the starting point of our algorithm
	}

	/*
	 * the "meat" of this class, where the dynamic programming algorithm is used to fill out theMatrix
	 * getting the final solution is a separate (trivial) step  
	 * 
	 * returns the number of computations made "clicks"
	 */
	public int solve(){
		int clicker = 0;//records the number of computation/table accesses made and returned at the end of computation.

		//conceptually, we must first fill in the first column... representing the case where there is zero weight capacity, 
		//there is no capacity to add value to the knapsack, so the optimal value is 0 in each case.
		//because the matrix is an integer matrix, all cells will be initialised to 0..so we don't actually 
		//have to do anything here!


		//now fill in the first row... representing the case where there is only one item to choose from, it is either in
		//or out of the knapsack - in if the capacity is large enough, or out otherwise
		int weight = things.get(0).getWeight();
		int value = things.get(0).getValue();
		clicker +=2;//2 accesses
		for(int col = 1; col<capacity+1; col++){
			clicker +=2;//1 comparison, one assignment
			if( weight <= col){
				theMatrix[0][col] = value;
			}
			else{
				theMatrix[0][col] = 0;
			}
			
		}

		//now fill in the other rows, each row can be filled in by considering the 2 possible options relating to the 
		//item represented by that row and weight capacity....
		//either the item is included in the optimal solution, so the total value is that of the item
		//plus the optimal solution of the smaller problem with any of the items in higher rows, and capacity reduced
		//by the weight of this item..
		//or.. the item is not included in the optimal solution, so the total value is that of the optimal solution
		//for the smaller problem with any of the items in higher rows with the same weight capacity.
		for(int row=1; row<things.size(); row++){
			weight = things.get(row).getWeight();
			value = things.get(row).getValue();
			clicker +=2;//2 accesses
			for(int col=1; col<=capacity; col++){
				int maxValueIfOut = theMatrix[row-1][col];
				clicker += 3;//1 access, a comparison next, and an assignment in either of the following cases
				if(weight > col){
					//then this item can't be included as it exceeds the capacity!
					theMatrix[row][col] = maxValueIfOut;
				}
				else{
					int maxValueIfIn = value + theMatrix[row-1][col-weight];
					clicker++;//an access
					theMatrix[row][col] = Math.max(maxValueIfOut, maxValueIfIn);				
				}
			}
		}

		return clicker;
	}

	/*
	 * returns the optimal value found in the filled matrix
	 */
	public int reportOptimalValue(){
		//printMatrix();
		return theMatrix[things.size()-1][capacity];
	}

	//to help with debugging:

	public void printMatrix(){
		System.out.println("knapsack capacity is "+ capacity);
		
		for(int row=0; row<things.size(); row++){
			System.out.printf("%20s: ", things.get(row).toString());
			for(int col=0; col<=capacity; col++){
				System.out.printf("%10d ", theMatrix[row][col]);
			}
			System.out.println();	
		}

	}


}
