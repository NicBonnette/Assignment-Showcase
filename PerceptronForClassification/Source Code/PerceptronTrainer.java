import java.util.ArrayList;

/**
 * the main class for the Perceptron training program
 * 
 * - handles incoming program calls 
 * - interprets arguments
 * - reads in training data
 * - trains a perceptron
 * - reports results of training
 * 
 * 
 * @author Nic Bonnette 300012059
 *
 */

public class PerceptronTrainer {

	private static int NUM_FEATURES = 50;
	private static int MAX_ITERATIONS = 1000;

	/**
	 * allowed arguments are:
	 * [collated training data file]
	 * [collated training data file] [num features - int]
	 * [collated training data file] [num features - int] [collated test data]...[collated test data]
	 * 
	 */
	public static void main(String[] args) {
		//pseudo-global variables
		Feature[] features;//our chosen feature set
		InputReader training;//our read in training data
		Perceptron perceptron;//our perceptron
		
		if (args.length == 0) {
			printHelp();
			return;
		}
		if (args.length >= 2) {
			NUM_FEATURES = Integer.parseInt(args[1]);
		}
		
		// load the training data
		System.out.println("Loading the training data");
		training = new InputReader(args[0]);

		ArrayList<ImageData> instances = training.getInstances();

		// make an appropriate feature set
		System.out.println("Making feature set");
		features = new Feature[NUM_FEATURES];
		for (int i = 0; i < NUM_FEATURES; i++) {
			features[i] = new Feature(training.getDim1(), training.getDim2());
			// this may result in multiples of the same feature (unlikely)
			System.out.println(features[i].toString());
		}

		// convert training data into feature value input, goal sets
		System.out.println("Converting training data into feature value/goal sets");
		FeatureValuesTargetPair[] FVTinstances = new FeatureValuesTargetPair[instances.size()];
		for (int i = 0; i < instances.size(); i++) {
			FVTinstances[i] = new FeatureValuesTargetPair(instances.get(i), features);
		}

		// make a new perceptron
		System.out.println("Making perceptron");
		perceptron = new Perceptron(NUM_FEATURES);

		// train the perceptron
		System.out.println("Training perceptron");
		int adjustments = 0;
		for (int i = 0; i < MAX_ITERATIONS; i++) {
			adjustments = 0;
			for (int j = 0; j < FVTinstances.length; j++) {
				boolean adjusted = perceptron.trainPerceptron(FVTinstances[j]);
				if (adjusted) {
					adjustments++;
				}
			}
			if (adjustments == 0) {
				System.out.printf("Convergence was achieved after %d iterations%n", i);
				//System.out.printf("Final weights: %s%n",perceptron.toString());
				break;
			}
		}
		if (adjustments > 0) {
			// report information about the training of the perceptron
			System.out.printf("after %d iterations, %d of the %d instances were correctly classified%n",
					MAX_ITERATIONS, perceptron.test(FVTinstances),instances.size());
		}
		
		if (args.length > 2) {
		//the remaining arguments should be test files
			for (int arg = 2; arg<args.length; arg++){
				System.out.printf("Reading test file %s%n",args[arg]);
				//read in argument file
				InputReader testInput = new InputReader(args[arg]);
				ArrayList<ImageData> testData = testInput.getInstances();
				//convert data to feature values
				FeatureValuesTargetPair[] FVTtestData = new FeatureValuesTargetPair[testData.size()];
				for (int i = 0; i < testData.size(); i++) {
					FVTtestData[i] = new FeatureValuesTargetPair(testData.get(i), features);
				}
				//test data in file
				int numCorrect = perceptron.test(FVTtestData);
				//report total result
				System.out.printf("%d of the test instances were correctly classified (out of %d total instances)%n",numCorrect,testData.size());
			}
		
		}
	}

	/*
	 * this method is called when the wrong type of arguments are given, it
	 * displays appropriate use of the method.
	 */
	private static void printHelp() {
		System.out.println("Valid argument options:");
		System.out.println("[training_file]");
		System.out.println("[training_file] [num_features]");
		System.out.println("[training_file] [num_features] [test_file1] ...any number of test files");
	}

}
