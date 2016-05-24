/**
 * This class models a simple perceptron.
 * 
 * The perceptron integrates a threshold of 1 as its first weight - always set to 1
 * The perceptron has a single binary output (coded as a 0 or 1 digit)
 * 
 * @author Nic Bonnette 300012059
 *
 */

public class Perceptron {
	private int numInputs;//the number of inputs, not including the dummy (threshold) input
	private double[] weights;//the set of weights to be applied to the inputs to determine the output
	
	public Perceptron(int numInputs) {
		this.numInputs = numInputs;
		this.weights = new double[numInputs + 1];
		
		//initialise the weights - initially in range (-0.5,0.5)
		//initial weights just have to be "small" the range is arbitrarily set for ease of use
		weights[0] = 1;//the 0th weight is the dummy variable and is always 1
		for(int i = 0; i<numInputs; i++){
			weights[i+1] = Math.random()-0.5;
		}
	}

	public int calcOutput(int[] featureValues) {
		// double-check that the data is the right size
		if (numInputs != featureValues.length) {
			System.out.println("input and weights are not same size, cannot calculate net input");
			return -1;//an error value
		}
		// calculate the net input
		double netInput = weights[0];//the value of the dummy featureValue is always 1
		for (int i = 0; i < numInputs; i++) {
			netInput += weights[i+1] * featureValues[i];
		}
		// calculate result
		int output = (netInput <= 0) ? 0 : 1;
		return output;
	}

	public boolean trainPerceptron(FeatureValuesTargetPair fvg) {
		
		boolean ans = false;
		// test input using current weights
		int output = calcOutput(fvg.getFeatureValues());
		// compare output with target output
		// if output and target match, do nothing, current weights are okay
		// if output and target do not match, adjust weights to achieve a match
		if (output != fvg.getTarget()) {
			ans = true;
			// if the target was 1 but the output was 0, it is a positive
			// example, weights are too small,
			// add the feature vector to weight vector
			if (output == 0) {
				weights[0] = weights[0]+1;//the featureValue of the dummy value is always 1
				for (int i = 0; i < numInputs; i++) {
					weights[i+1] = weights[i+1] + fvg.getFeatureValues()[i];
				}
			}
			// if the target was 0 but the output was 1, it is a negative
			// example,
			// weights are too high, subtract the feature vector from the weight
			// vector
			else {
				weights[0] = weights[0]-1;//the featureValue of the dummy value is always 1
				for (int i = 0; i < numInputs; i++) {
					weights[i+1] = weights[i+1] - fvg.getFeatureValues()[i];
				}
			}
		}

		return ans;
	}

	public double[] getWeights() {
		return weights;
	}

	public void setWeights(double[] weights) {
		this.weights = weights;
	}

	public int test(FeatureValuesTargetPair[] instances) {
		int numCorrect = 0;
		int numInstances = instances.length;
		for(int i=0; i<numInstances; i++){
			FeatureValuesTargetPair instance = instances[i];
		int output = calcOutput(instance.getFeatureValues());
		if (output == instance.getTarget()) { numCorrect++;}
		}
		return numCorrect;
	}

	
public String toString(){
	String ans = "[ ";
	for(int i = 0; i<weights.length; i++){
		ans = ans + weights[i] + ", ";
	}
	ans = ans + "]";
	return ans;
}

}
