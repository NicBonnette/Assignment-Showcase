import java.util.ArrayList;

/**
 * This class runs automated tests on the Knapsack class.
 * 
 * The tests are called by the main method in the Knapsack Class of this project
 * 
 * There are also options for running multiple tests:
 * All (same single test sequence and iteration size run on each of the 4 sequence types) 
 * Full (a range of test sequence sizes averaged over 1000 iterations, run on each of the 4 sequence types).
 * 
 * 
 ** @author Nic
 *
 */
public class AutomatedTester {
	
	public AutomatedTester(){
		super();
		fullTest();
	}
	
	public AutomatedTester(int numItems, int numRuns, String solverType) {
		super();
		switch (solverType) {
		case "bf1":  System.out.printf("brute force 0-1 (ave clicks):\t\t%d%n", testbf1(numItems, numRuns));
		break;
		case "bfm":  System.out.printf("brute force 0-N (ave clicks):\t\t%d%n", testbfm(numItems, numRuns));
		break;
		case "dp1":  System.out.printf("Dynamic Prog 0-1 (ave clicks):\t\t%d%n", testdp1(numItems, numRuns));
		break;
		case "dpm":  System.out.printf("Dynamic Prog 0-N (ave clicks):\t\t%d%n", testdpm(numItems, numRuns));
		break;
		case "gsm":  System.out.printf("Graph Search 0-N (ave clicks):\t\t%d%n", testgsm(numItems, numRuns));
		break;		
		case "all":  System.out.println("bf1: "+ testbf1(numItems, numRuns) 
										+ " bfm: " + testbfm(numItems, numRuns)
										+ " dp1: " + testdp1(numItems, numRuns)
										+ " dpm: " + testdpm(numItems, numRuns)
										+ " gsm: " + testgsm(numItems, numRuns));
		break;
		default: System.out.println("Automated tester was called with an invalid search type");
		break;
		}
	}

	private void fullTest() {
		System.out.println("average computations on runs of 100");
		System.out.println("The slow ones:");
		System.out.println("Random value/weight input:");
		System.out.printf("Number of items\t\t%10d\t%10d\t%10d\t%10d%n",10,20,30,40);
		System.out.printf("brute force 01:\t\t%10d\t%10d%n",testbf1(10,100),testbf1(20, 100));
		System.out.printf("brute force 0N:\t\t%10d\t%10d\t%10d\t%10d%n",testbfm(10, 100),testbfm(20, 100),testbfm(30, 100),testbfm(40, 100));
		System.out.println();
		System.out.println("The fast ones:");
		System.out.println("Random value/weight input:");
		System.out.printf("Number of items\t\t\t%10d\t%10d\t%10d\t%10d\t%10d%n",10,100,1000,10000,100000);
		System.out.printf("Dynamic prog 01 (comps):\t%10d\t%10d\t%10d\t%10d\t%10d%n",testdp1(10, 100),testdp1(100, 100),testdp1(1000, 100),testdp1(10000, 100),testdp1(100000, 100));
		System.out.printf("Dynamic prog 0N (comps):\t%10d\t%10d\t%10d\t%10d\t%10d%n",testdpm(10, 100),testdpm(100, 100),testdpm(1000, 100),testdpm(10000, 100),testdpm(100000, 100));
		System.out.printf("Graph Search 0N (comps):\t%10d\t%10d\t%10d\t%10d\t%10d%n",testgsm(10, 100),testgsm(100, 100),testgsm(1000, 100),testgsm(10000, 100),-1);
		System.out.printf("Dynamic prog 01 (nanos):\t%10d\t%10d\t%10d\t%10d\t%10d%n",timeTestdp1(10, 100),timeTestdp1(100, 100),timeTestdp1(1000, 100),timeTestdp1(10000, 100),timeTestdp1(100000, 100));
		System.out.printf("Dynamic prog 0N (nanos):\t%10d\t%10d\t%10d\t%10d\t%10d%n",timeTestdpm(10, 100),timeTestdpm(100, 100),timeTestdpm(1000, 100),timeTestdpm(10000, 100),timeTestdpm(100000, 100));
		System.out.printf("Graph Search 0N (nanos):\t%10d\t%10d\t%10d\t%10d\t%10d%n",timeTestgsm(10, 100),timeTestgsm(100, 100),timeTestgsm(1000, 100),timeTestgsm(10000, 100),-1);
		System.out.println();
		System.out.println("Even value/weight input:");
		System.out.printf("Number of items\t\t\t%10d\t%10d\t%10d\t%10d\t%10d\t%n",10,20,30,100,1000);
		System.out.printf("Dynamic prog 0N (comps):\t%10d\t%10d\t%10d\t%10d\t%10d%n",const_testdpm(10, 100),const_testdpm(20, 100),const_testdpm(30, 100),const_testdpm(100, 100),const_testdpm(1000, 100));
		System.out.printf("Graph Search 0N (comps):\t%10d\t%10d\t%10d\t%10d\t%10d%n",const_testgsm(10, 100),const_testgsm(20, 100),const_testgsm(30, 100),-1,-1);
		System.out.printf("Dynamic prog 0N (nanos):\t%10d\t%10d\t%10d\t%10d\t%10d%n",const_timeTestdpm(10, 100),const_timeTestdpm(20, 100),const_timeTestdpm(30, 100),const_timeTestdpm(100, 100),const_timeTestdpm(1000, 100));
		System.out.printf("Graph Search 0N (nanos):\t%10d\t%10d\t%10d\t%10d\t%10d%n",const_timeTestgsm(10, 100),const_timeTestgsm(20, 100),const_timeTestgsm(30, 100),-1,-1);

	}

	/**
	 * test brute force
	 */
	private long testbf1(int numItems, int numRuns) {
		long totalClicks = 0;
		for(int run = 0; run<numRuns; run++){
			totalClicks += (new BruteForce01(10, Generator01.generateRand(numItems))).solve();	
		}
		return totalClicks/numRuns;
		}

	private long testbfm(int numItems, int numRuns) {
		long totalClicks = 0;
		for(int run = 0; run<numRuns; run++){
			totalClicks += (new BruteForceMany(10, GeneratorMany.generateRand(numItems,10))).solve();	
		}
		return totalClicks/numRuns;
		}
	
	private long testdp1(int numItems, int numRuns) {
		long totalClicks = 0;
		for(int run = 0; run<numRuns; run++){
			totalClicks += (new DPsolver01(10,Generator01.generateRand(numItems))).solve();	
		}
		return  totalClicks/numRuns;
		}
	
	private long testdpm(int numItems, int numRuns) {
		long totalClicks = 0;
		for(int run = 0; run<numRuns; run++){
			totalClicks += (new DPsolverMany(10,GeneratorMany.generateRand(numItems,20))).solve();	
		}
		return  totalClicks/numRuns;		
	}
	
	private long const_testdpm(int numItems, int numRuns) {
		long totalClicks = 0;
		for(int run = 0; run<numRuns; run++){
			totalClicks += (new DPsolverMany(10,GeneratorMany.generateRatiod(numItems,20))).solve();	
		}
		return  totalClicks/numRuns;		
	}
	
	private long testgsm(int numItems, int numRuns) {
		long totalClicks = 0;
		for(int run = 0; run<numRuns; run++){
			totalClicks += (new GraphSolverMany(10,GeneratorMany.generateRand(numItems,20))).solve();	
		}
		return totalClicks/numRuns;
		}
	
	private long const_testgsm(int numItems, int numRuns) {
		long totalClicks = 0;
		for(int run = 0; run<numRuns; run++){
			totalClicks += (new GraphSolverMany(10,GeneratorMany.generateRatiod(numItems,20))).solve();	
		}
		return totalClicks/numRuns;
		}
	
// =========================================================================
// time measuring tests
		 
	/**
	 * @return time taken in milliseconds
	 */
	private long timeTestgsm(int numItems, int numRuns) {
		 long startTime;
		 long endTime;
		 long duration;
		 long totalTimeTaken = 0;
		 int run = numRuns;
		 while (run > 0) {
		 startTime = System.nanoTime();
		 // run test
		 ArrayList<Thing> items = GeneratorMany.generateRand(numItems,20);
		 (new GraphSolverMany(10,items)).solve();
		 // record end time
		 endTime = System.nanoTime();
		 // update timeTaken
		 duration = (endTime - startTime);
		 totalTimeTaken = totalTimeTaken + duration;
		 run--;
		 }
		
		 // calculate and report average time
		 return totalTimeTaken / numRuns;
		 }
	/**
	 * @return time taken in milliseconds
	 */
	private long const_timeTestgsm(int numItems, int numRuns) {
		 long startTime;
		 long endTime;
		 long duration;
		 long totalTimeTaken = 0;
		 int run = numRuns;
		 while (run > 0) {
		 startTime = System.nanoTime();
		 // run test
		 ArrayList<Thing> items = GeneratorMany.generateRatiod(numItems,20);
		 (new GraphSolverMany(10,items)).solve();
		 // record end time
		 endTime = System.nanoTime();
		 // update timeTaken
		 duration = (endTime - startTime);
		 totalTimeTaken = totalTimeTaken + duration;
		 run--;
		 }
		
		 // calculate and report average time
		 return totalTimeTaken / numRuns;
		 }

	/**
	 * @return time taken in milliseconds
	 */
	private long timeTestdpm(int numItems, int numRuns) {
		 long startTime;
		 long endTime;
		 long duration;
		 long totalTimeTaken = 0;
		 int run = numRuns;
		 while (run > 0) {
		 startTime = System.nanoTime();
		 // run test
		 ArrayList<Thing> items =  GeneratorMany.generateRand(numItems,20);
		 (new DPsolverMany(10,items)).solve();
		 // record end time
		 endTime = System.nanoTime();
		 // update timeTaken
		 duration = (endTime - startTime);
		 totalTimeTaken = totalTimeTaken + duration;
		 run--;
		 }
		
		 // calculate and report average time
		 return totalTimeTaken / numRuns;
		 }

	/**
	 * @return time taken in milliseconds
	 */
	private long const_timeTestdpm(int numItems, int numRuns) {
		 long startTime;
		 long endTime;
		 long duration;
		 long totalTimeTaken = 0;
		 int run = numRuns;
		 while (run > 0) {
		 startTime = System.nanoTime();
		 // run test
		 ArrayList<Thing> items =  GeneratorMany.generateRatiod(numItems,20);
		 (new DPsolverMany(10,items)).solve();
		 // record end time
		 endTime = System.nanoTime();
		 // update timeTaken
		 duration = (endTime - startTime);
		 totalTimeTaken = totalTimeTaken + duration;
		 run--;
		 }
		
		 // calculate and report average time
		 return totalTimeTaken / numRuns;
		 }

	/**
	 * @return time taken in milliseconds
	 */
	private long timeTestdp1(int numItems, int numRuns) {
		 long startTime;
		 long endTime;
		 long duration;
		 long totalTimeTaken = 0;
		 int run = numRuns;
		 while (run > 0) {
		 startTime = System.nanoTime();
		 // run test
		 ArrayList<Thing> items =  Generator01.generateRand(numItems);
		 (new DPsolver01(10,items)).solve();
		 // record end time
		 endTime = System.nanoTime();
		 // update timeTaken
		 duration = (endTime - startTime);
		 totalTimeTaken = totalTimeTaken + duration;
		 run--;
		 }
		
		 // calculate and report average time
		 return totalTimeTaken / numRuns;
		 }

}
