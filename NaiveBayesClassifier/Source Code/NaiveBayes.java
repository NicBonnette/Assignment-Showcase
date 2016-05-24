import java.util.ArrayList;

/**
 * the main class for the NaiveBayes program
 * 
 * handles incoming program calls
 * interprets arguments
 * reads in test data
 * produces output
 * 
 * @author Nic Bonnette 300012059
 *
 */
public class NaiveBayes {

	/**
	 * there is a single argument option allowed: 
	 * [ClassifiedDataFile][UnclassifiedDataFile]
	 * the ClassifiedDataFile is used to create the Naive Bayes Classifier
	 * the UnclassifiedDataFile is presented to the created Naive Bayes Classifier for classification
	 */
	public static void main(String[] args) {
		// if data is in the correct format we may proceed
		if (args.length == 2) {
			// load the test data
			System.out.printf("Reading classified data file %s%n", args[0]);
			SpamInputReader classifiedData = new SpamInputReader(args[0]);
			// build Naive Bayes Classifier from classified data
			System.out.println("Building classifier data");
			NBclassifier classifier = new NBclassifier(classifiedData.getData());
			
			// load the unclassified data
			System.out.printf("Reading unclassified data file %s%n", args[1]);
			SpamInputReader unclassifiedData = new SpamInputReader(args[1]);
			
			// classify the unclassified data
			System.out.println("Classifying the unclassified data:");
			ArrayList<int[]> toClassify = unclassifiedData.getData();
			classifier.classifyData(toClassify);
		}
		else {
			printHelp();
		}

	}

	
	/*
	 * this method is called when the wrong type of arguments are given, it
	 * displays appropriate use of the method.
	 */
	private static void printHelp() {
		System.out.println("Valid argument options:");
		System.out.println("[ClassifiedDataFile][UnclassifiedDataFile]");
	}

}
