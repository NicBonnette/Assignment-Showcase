import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * This class is used to solve the 0-N knapsack problem using a graph search technique
 *
 * The graph is implicit (in that we create concrete nodes of interest as we go, and do not create nodes that
 * we have deduced to be not relevant to the problem solution).  The full conceptual graph is a DAG with each
 * node representing a subset of the available things, and edges
 * representing the "is a superset by one element" relation - which in this
 * problem represents the  addition of one thing to the knapsack.
 *
 * We use an auxilliary method to help determine whether a node is of interest to the problem,
 * namely we can find an upper bound on the maximum value of a knapsack solution involving a particular
 * node.  By keeping track of the "best" solution so far, we can eliminate any search path whose
 * upper bound is not higher than this best solution.  We also use the upper bound as an heuristic
 * to guide the order of graph traversal.
 *
 * We also use the total weight of a node to exclude any further search down paths that exceed the
 * capacity of the knapsack.
 *
 * @author Nic
 *
 */
public class GraphSolverMany {

	private ArrayList<Thing> things;//all of the things that are available for this knapsack problem - should
	//be ordered in decreasing value to weight ratio
	private	int capacity;//capacity of the knapsack

	private PriorityQueue<GSMnode> unexploredNodes = new PriorityQueue<GSMnode>();//natural order is
	//from biggest potential to lowest potential
	private HashSet<GSMnode> exploredNodes = new HashSet<GSMnode>();//all of the nodes that have been
	//visited already - used to eliminate double working

	private int bestSoFar;//the best value found in the graph search so far

	public GraphSolverMany(int capacity,ArrayList<Thing> things) {
		super();
		this.things = things;
		this.capacity = capacity;
		bestSoFar = 0;
		makeStartNode();
	}

	public void makeStartNode(){
		//the start node should be the empty knapsack
		//first make the "still available" array from the things ArrayList - all things will still be available!
		int[] available = new int[things.size()];
		for(int i = 0; i<things.size(); i++){
			available[i] = things.get(i).getNum();
		}
		//the empty knapsack will have no value or weight!, the other arguments are non-variant
		//make the first node, and add it to the priority queue.
		unexploredNodes.add(new GSMnode(things,	available, 0, 0, capacity));
	}

	/**
	 * this method is the main algorithm, it dequeues and enqueues items from the queue
	 *  until there are no items left to look at.  After this method is run, bestSoFar
	 *  will be the optimal value obtainable from the knapsack problem.
	 *  
	 *  returns the number of computations
	 */
	public long solve(){
		long clicker = 0;//a computation counter, items must be sorted before this is called
		Collections.sort(this.things);
		clicker += things.size()*(Math.log(things.size()));//sorting is inherently nlogn
		while(!unexploredNodes.isEmpty()){
			clicker ++;//empty check
			//dequeue a node from the priority queue,
			//the dequeued item will have the highest potential to reach the maximum value
			//if the potential is smaller than the bestSoFar, then there is no point exploring
			//this node - or indeed any other nodes in the queue! we are done.
			//otherwise, enqueue all of the nodes that are reachable (larger by one) from the
			//dequeued node.
			GSMnode next = unexploredNodes.poll();
			clicker++;//extract data

			//if the node was already explored then you don't need to do anything,
			//but if it wasn't you need to do more
			clicker ++;//hashset contains is constant time
			if(! exploredNodes.contains(next)){
				//if it wasn't already explored, check against the bestSoFar and update if better
				int nextVal = next.getValue();
				clicker++;//comparison
				if(nextVal > bestSoFar){
					bestSoFar = nextVal;
					}
				//add to the explored nodes
				exploredNodes.add(next);
				clicker++;//hashset addition is constant time

				//now consider if there is any point looking at the nodes reachable from next (is it
				//possible to find a better solution from them?
				clicker++;//1 comparison
				if (next.getPotential() < bestSoFar){
					//no point exploring further - don't add any nodes accessible from this node
					break;
				}
				//if there _is_ a possibility of finding a better solution
				//add all the "add by one" nodes to the unexploredNodes queue
				for(int i =0; i<things.size(); i++){
					clicker++;//data access
					Thing potThing = things.get(i);
					//don't add anything that goes over the capacity of the knapsack
					clicker +=3;//2 accesses and a comparison
					int potNewWeight = next.getWeight() + potThing.getWeight();
					if(potNewWeight <= capacity){
						int[] thingsLeft = next.getThingsLeft().clone();
						clicker += thingsLeft.length;//array construction
						//if that thing is addable, make up the node and add it to the unexplored nodes queue
						//don't worry if it has actually been explored yet - that will be checked when it is dequeued
						clicker++;//1 comparison
						if(thingsLeft[i]>0){
							clicker +=5;//1 assignment, 1 node creation, 2 accesses
							thingsLeft[i] = thingsLeft[i] - 1;
							int potNewValue = next.getValue() + potThing.getValue();
							GSMnode newNode = new  GSMnode(things,thingsLeft, potNewValue, potNewWeight, capacity);
							clicker += unexploredNodes.size();//queue entry - need to find and insert in order
							unexploredNodes.add(newNode);
							clicker += newNode.getClicksForPotential();
						}
					}
				}
			}
		}
		//when the graph exploration is completed, bestSoFar should be the optimal solution! nothing more to do!
	
		return clicker;
	}


	/*
	 * returns the optimal value found by the graph search 
	 * THE GRAPH SEARCH NEEDS TO BE PERFORMED FIRST (method exploreGraph)
	 */
	public int reportOptimalValue(){
		return bestSoFar;
	}
}
