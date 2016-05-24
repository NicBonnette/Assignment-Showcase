import java.util.ArrayList;

/**
 * Generates an array of Thing objects ready to pass into a 0-1 Knapsack problem solver
 * Primarily for testing and analysis purposes
 *
 * @author bonnetnico
 */
public class Generator01 {

	/*
	 * this method returns a Thing array with size elements
	 * The values and weights are randomly generated in the range (0-1000]
	 * Names are arbitrarily manufactured
	 *
	 * Requires: size >= 0
	 */
	public static ArrayList<Thing> generateRand(int size) {
		if(size<0){
			throw new IllegalArgumentException("generator must be called with a non-negative size");
		}
		ArrayList<Thing> ans = new ArrayList<Thing>();
		for (int i = 0;i<size;i++){
			String name = "thing_" + i;
			int value = (int)(Math.random()*1000) +1;
			int weight = (int)(Math.random()*1000) +1;
			ans.add(new Thing(name, value, weight, 1));
		}
		return ans;
	}

	/*
	 * This generator produces Thing arrays where the value to weight ratio is roughly constant
	 * (arbitrarily set to 1 give or take integer division)
	 */
	public ArrayList<Thing> generateRatiod(int size) {
		if(size<0){
			throw new IllegalArgumentException("generator must be called this a non-negative size");
		}
		ArrayList<Thing> ans = new ArrayList<Thing>();
		for (int i = 0;i<size;i++){
			String name = "thing_" + i;
			int value = (int)(Math.random()*1000) + 1;
			int weight = 1000/value;
			ans.add(new Thing(name, value, weight,1));
		}
		return ans;
	}

	/*
	 * This generator produces Thing arrays where the weight and value are constant
	 * (arbitrarily set to 1)
	 */
	public ArrayList<Thing> generateConst(int size) {
		if(size<0){
			throw new IllegalArgumentException("generator must be called with a non-negative size");
		}
		ArrayList<Thing> ans = new ArrayList<Thing>();
		for (int i = 0;i<size;i++){
			String name = "thing_" + i;
			int value = 1;
			int weight = 1;
			ans.add( new Thing(name, value, weight,1));
		}
		return ans;
	}

	/*
	 * This generator produces Thing arrays where the weight and value are in the range (0,weightBound] and
	 * (0,valueBound]
	 */
	public ArrayList<Thing> generateRandBounded(int size, double valueBound, double weightBound) {
		if(size<0){
			throw new IllegalArgumentException("generator must be called with a non-negative size");
		}
		ArrayList<Thing> ans = new ArrayList<Thing>();
		for (int i = 0;i<size;i++){
			String name = "thing_" + i;
			int value = (int)(Math.random()*valueBound) + 1;
			int weight = (int)(Math.random()*weightBound) + 1;
			ans.add( new Thing(name, value, weight,1));
		}
		return ans;
	}


}
