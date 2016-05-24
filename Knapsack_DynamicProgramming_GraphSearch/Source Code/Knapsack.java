

/**
 * this is the main class for the knapsack project,
 * it includes the main method that accepts and interprets input arguments
 * depending on the input arguments, it either runs a test (or set of tests), or 
 * finds the maximum value obtainable for items referenced in a file.
 * 
 * @author Nic
 */
public class Knapsack {


	/**
	 * allowed arguments are:
	 * solve [solution method: bf1/bfm/dp1/dpm] [maxweight] [filename]
	 * test [solution method: bf1/bfm/dp1/dpm/all/full] 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length == 4 && args[0].equals("solve")){
			int capacity = Integer.parseInt(args[2]);
			//load the data file
			InputGetter input = new InputGetter(args[3]);
			//send out the data to the correct solver
			switch (args[1]){
			case "bf1" : 
			{BruteForce01 bf1 = new BruteForce01(capacity, input.getThings());
			bf1.solve();
			System.out.printf("maximum value obtainable is %d\n",bf1.reportOptimalValue());
			return;}
			case "bfm" : {BruteForceMany bfm = new BruteForceMany(capacity, input.getThings());
			bfm.solve();
			System.out.printf("maximum value obtainable is %d\n",bfm.reportOptimalValue());
			return;}
			case "dp1": {
				DPsolver01 dp1 = new DPsolver01(capacity, input.getThings());
				dp1.solve();
				System.out.printf("maximum value obtainable is %d\n", dp1.reportOptimalValue());
				return;
				}
			case "dpm": {
				DPsolverMany dpm = new DPsolverMany(capacity, input.getThings());
				dpm.solve();
				System.out.printf("maximum value obtainable is %d\n", dpm.reportOptimalValue());
				return;
				}
			case "gsm": {
				GraphSolverMany gsm = new GraphSolverMany(capacity, input.getThings());
				gsm.solve();
				System.out.printf("maximum value obtainable is %d\n", gsm.reportOptimalValue());
				return;
				}
			default : break;
			}
		}
		else if(args.length == 2 && args[0].equals("test")){
			//perform testing	
			switch (args[1]){
			case "bf1" : {
				//arguments are numItems, numReuns, type of tester...
				new AutomatedTester(30,10,"bf1");
				return;
				}
			case "bfm" : {
				System.out.println("bfm is under construction");
				return;
				}
			case "dp1": {
				System.out.println("dp1 is under construction");
				return;
				}
			case "dpm": {
				System.out.println("dpm is under construction");
				return;
				}
			case "gsm": {
				System.out.println("gsm is under construction");
				return;
				}
			case "all": {
				//arguments are numItems, numReuns, type of tester...
				new AutomatedTester(20,100,"all");				
				return;
				}
			case "full": {
				//arguments are numItems, numReuns, type of tester...
				new AutomatedTester();				
				return;
				}
			default : break;
			}
		}
		
			//there has been a miss-entry of arguments, give some guidance
			System.out.println("Invalid arguments - argument options: \n"
					+ "solve [solution method: bf1/bfm/dp1/dpm/gsm] [knapsackCapacity] [name of file] \n"
					+ "test [solution method: bf1/bfm/dp1/dpm/gsm/all]");

	}
}
