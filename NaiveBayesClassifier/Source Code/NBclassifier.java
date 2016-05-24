import java.util.ArrayList;

/**
 * This class holds a Naive Bayes Classifier
 * 
 * It includes methods to build the classifier from already classified data as
 * well as methods to classify unclassified feature sets
 * 
 * @author Nic
 */
public class NBclassifier {
	ArrayList<int[]> classifiedData;// int arrays must all be of same length!
									// greater than 2!
	int featureSetSize = -1;// size of each feature and class set
							// -1 indicates that data not read yet
							// or was invalid!

	double[][] conditionalProbs;
	double probSpam;

	public NBclassifier(ArrayList<int[]> classifiedData) {
		super();
		this.classifiedData = classifiedData;
		if (!dataOkay()) {
			System.out.println("there was a problem with the data, NBclassifier aborting");
			return;
		}
		buildClassifier();
	}

	private void buildClassifier() {
		// build counts table - including a zero compensation
		// each row represents the count data for one feature
		// each row has 4 columns,
		// column 0: num{class = 0 and feature = 0}
		// column 1: num{class = 0 and feature = 1}
		// column 2: num{class = 1 and feature = 0}
		// column 3: num{class = 1 and feature = 1}
		// from this you can work out the total num with class = 0 and total num
		// class =1
		// then you can work out proportion feature was 0 given class 0 etc
		int[][] countsTable = new int[featureSetSize - 1][4];
		// initialise all counts to 1
		for (int row = 0; row < featureSetSize - 1; row++) {
			for (int col = 0; col < 4; col++) {
				countsTable[row][col] = 1;
			}
		}
		int numSpam = 0;
		// count all input data
		for (int[] input : classifiedData) {
			int classification = input[featureSetSize - 1];
			numSpam += classification;
			for (int feature = 0; feature < featureSetSize - 1; feature++) {
				int colToIncrement = classification * 2 + input[feature];
				// eg if feature has value 1 and classification value 1, will
				// increment column 3
				countsTable[feature][colToIncrement] = countsTable[feature][colToIncrement] + 1;
			}
		}
		// print out counts data:
		System.out.println("Tallied data from classified data: (all counts initialized to 1)");
		System.out.printf("Total feature sets: %d, Total Spam classifications: %d%n",classifiedData.size(),numSpam);
		System.out.println("count(class, feat)\t(c=0,f=0)\t (c=0,f=1)\t"
				+ "(c=1,f=0)\t (c=1,f=1)");
		for(int feature = 0; feature<countsTable.length; feature++){
			System.out.printf("Feature %d:\t\t %d\t\t %d\t\t %d\t\t %d\n", feature, countsTable[feature][0], countsTable[feature][1],
					countsTable[feature][2],countsTable[feature][3]);
		}
		
		
		// calculate probabilities from counts
		conditionalProbs = new double[featureSetSize - 1][4];
		for (int feature = 0; feature < conditionalProbs.length; feature++) {
			double totalNotSpam = countsTable[feature][0] + countsTable[feature][1];
			double totalSpam = countsTable[feature][2] + countsTable[feature][3];
			// enter prob feature = 0 given classification = 0
			conditionalProbs[feature][0] = countsTable[feature][0] / totalNotSpam;
			// enter prob feature = 1 given classification = 0
			conditionalProbs[feature][1] = countsTable[feature][1] / totalNotSpam;
			// enter prob feature = 0 given classification = 1
			conditionalProbs[feature][2] = countsTable[feature][2] / totalSpam;
			// enter prob feature = 1 given classification = 1
			conditionalProbs[feature][3] = countsTable[feature][3] / totalSpam;
		}

		probSpam = numSpam * (1.0) / classifiedData.size();
		
		System.out.println("Probabilities from tallied data:");
		System.out.printf("Probability of Spam: %f, Probability of NotSpam: %f%n",probSpam,1-probSpam);
		System.out.println("Conditional Probabilities:");
		System.out.println("P(featValue|classValue)\t(f=0|c=0)\t (f=1|c=0)\t"
				+ "(f=0|c=1)\t (f=1|c=1)");
		for(int feature = 0; feature<countsTable.length; feature++){
			System.out.printf("Feature %d:\t\t %f\t %f\t %f\t %f\n", feature, 
					conditionalProbs[feature][0], conditionalProbs[feature][1],
					conditionalProbs[feature][2],conditionalProbs[feature][3]);
		}
	}

	/*
	 * modifies the passed arraylist, by updating the classification for each
	 * feature/class set.
	 */
	public void classifyData(ArrayList<int[]> toClassify) {
		// double check that the data is of the correct form
		if (!inputOkay(toClassify)) {
			System.out.println("'to classify' data not of appropriate form");
		}
		// classify each feature/class set

		for (int[] datum : toClassify) {
			//first print out the unclassified data
			System.out.println("-----------------------------");
			System.out.printf("Feature values: ");
			for(int i = 0; i<datum.length - 1; i++){
			System.out.printf("%d ", datum[i]);}
			System.out.println();

			// find relative probspam
			double probDatumIsSpam = probSpam;
			for(int feature = 0; feature<featureSetSize-1; feature++){
				int colToCheck = 2 + datum[feature];
				probDatumIsSpam = probDatumIsSpam * conditionalProbs[feature][colToCheck];
			}
			System.out.printf("P(feature values | class=Spam)*P(class=Spam): %f%n",probDatumIsSpam);
			
			// find relative probnotspam
			double probDatumNotSpam = 1 - probSpam;
			for(int feature = 0; feature<featureSetSize-1; feature++){
				int colToCheck = 0 + datum[feature];
				probDatumNotSpam = probDatumNotSpam * conditionalProbs[feature][colToCheck];
			}
			System.out.printf("P(feature values | class=NotSpam)*P(class=NotSpam): %f%n",probDatumNotSpam);
			
			// if probspam bigger, set classifation as spam
			if(probDatumIsSpam > probDatumNotSpam){
				System.out.println("classified as Spam");
				datum[featureSetSize-1] = 1;
			}
			else{
				System.out.println("classified as NotSpam");
				datum[featureSetSize-1] = 0;
			}
			
		}
	}

	/*
	 * checks unclassified data to make sure it is of appropriate composition
	 * for classification
	 */
	private boolean inputOkay(ArrayList<int[]> toClassify) {
		if (toClassify == null || toClassify.size() < 1) {
			System.out.println("to Classify data devoid of content");
			return false;
		}
		for (int[] featureSet : toClassify) {
			int lengthFeatureSet = featureSet.length;
			if (lengthFeatureSet != featureSetSize) {
				return false;
			}
			for (int feature = 0; feature < featureSetSize - 1; feature++) {
				int featureValue = featureSet[feature];
				if (!(featureValue == 0 || featureValue == 1)) {
					System.out.println("data had an incorrectly specified feature value");
					return false;
				}
			}
		}
		return true;
	}

	/*
	 * checks classified data to make sure it is of appropriate composition for
	 * building the Naive Bayes classifier
	 */
	private boolean dataOkay() {
		if (classifiedData == null || classifiedData.size() < 1) {
			System.out.println("classified data devoid of content");
			return false;
		}
		for (int[] featureSet : classifiedData) {
			int lengthFeatureSet = featureSet.length;
			if (this.featureSetSize == -1) {
				this.featureSetSize = lengthFeatureSet;
			} else if (lengthFeatureSet != featureSetSize) {
				System.out.println("classified data of inconsistent feature lengths");
				return false;
			}
			int classification = featureSet[featureSetSize - 1];
			if (!(classification == 0 || classification == 1)) {
				System.out.println("classified data had an incorrectly classified element");
				return false;
			}
			for (int feature = 0; feature < featureSetSize - 1; feature++) {
				int featureValue = featureSet[feature];
				if (!(featureValue == 0 || featureValue == 1)) {
					System.out.println("classified data had an incorrectly specified feature value");
					return false;
				}
			}
		}
		return true;
	}

}
