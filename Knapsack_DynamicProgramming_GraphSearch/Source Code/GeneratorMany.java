import java.util.ArrayList;

/**
 * Generates an array of MultiThing objects ready to pass into a 0-N Knapsack problem solver
 * Primarily for testing and analysis purposes
 *
 * @author bonnetnico
 */
public class GeneratorMany {

	/*
	 * this method returns a MultiThing array with size total elements
	 * The values and weights are randomly generated in the range (0-1]
	 * Names are arbitrarily manufactured
	 *
	 * Requires: size >= 0
	 */
	public static ArrayList<Thing> generateRand(int size, int maxMult) {
		if(size<0){
			throw new IllegalArgumentException("generator must be called with a non-negative size");
		}

		ArrayList<Thing> ans = new ArrayList<Thing>();
		int soFar = 0;
		while(soFar<size){
			//make a multiple size
			int num = 1 + (int)(Math.random()*maxMult);//makes a random integer in range [1,maxMult]
			///make sure that the multiple size doesn't go over the total size that we want
			if (num+soFar > size){
				num = size - soFar;
			}
			//now make the random MultiThing
			String name = "thing_" + soFar;
			int value = (int)(Math.random()*1000) +1;
			int weight = (int)(Math.random()*1000) +1;
			ans.add(new Thing(name, value, weight, num));
			soFar += num;
		}
		return ans;
	}

	/*
	 * This generator produces Thing arrays where the value to weight ratio is constant
	 * (arbitrarily set to 1)
	 */
	public static ArrayList<Thing> generateRatiod(int size, int maxMult) {
		if(size<0){
			throw new IllegalArgumentException("generator must be called with a non-negative size");
		}
		ArrayList<Thing> ans = new ArrayList<Thing>();

		int soFar = 0;
		while(soFar<size){
			//make a multiple size
			int num = 1 + (int)(Math.random()*maxMult);//makes a random integer in range [1,maxMult]
			///make sure that the multiple size doesn't go over the total size that we want
			if (num+soFar > size){
				num = size - soFar;
			}
			//now make the random MultiThing
			String name = "thing_" + soFar;
			int value = (int)(Math.random()*1000) + 1;
			int weight = 1000/value;
			ans.add(new Thing(name, value, weight, num));
			soFar += num;
		}
		return ans;
	}

	/*
	 * This generator produces Thing arrays where the weight and value are constant
	 * (arbitrarily set to 1)
	 */
	public ArrayList<Thing> generateConst(int size, int maxMult) {
		if(size<0){
			throw new IllegalArgumentException("generator must be called with a non-negative size");
		}
		ArrayList<Thing> ans = new ArrayList<Thing>();

		int soFar = 0;
		while(soFar<size){
			//make a multiple size
			int num = 1 + (int)(Math.random()*maxMult);//makes a random integer in range [1,maxMult]
			///make sure that the multiple size doesn't go over the total size that we want
			if (num+soFar > size){
				num = size - soFar;
			}
			//now make the random MultiThing
			String name = "thing_" + soFar;
			int value = 1;
			int weight = 1;
			ans.add(new Thing(name, value, weight, num));
			soFar += num;
		}
		return ans;
	}

	/*
	 * This generator produces Thing arrays where the weight and value are in the range (0,weightBound] and
	 * (0,valueBound]
	 */
	public ArrayList<Thing> generateRandBounded(int size, int maxMult, double valueBound, double weightBound) {
		if(size<0){
			throw new IllegalArgumentException("generator must be called with a non-negative size");
		}

		ArrayList<Thing> ans = new ArrayList<Thing>();

		int soFar = 0;
		while(soFar<size){
			//make a multiple size
			int num = 1 + (int)(Math.random()*maxMult);//makes a random integer in range [1,maxMult]
			///make sure that the multiple size doesn't go over the total size that we want
			if (num+soFar > size){
				num = size - soFar;
			}
			//now make the random MultiThing
			String name = "thing_" + soFar;
			int value = (int)(Math.random()*valueBound) + 1;
			int weight = (int)(Math.random()*weightBound) + 1;
			ans.add(new Thing(name, value, weight, num));
			soFar += num;
		}
		return ans;

	}

}
