
public class FeatureValuesTargetPair {
int[] featureValues;
int target;

public FeatureValuesTargetPair(ImageData image, Feature[] features){
	featureValues = new int[features.length];
	for(int i = 0; i<features.length;i++){
	featureValues[i] = features[i].calculateValue(image.getImage());
	}
	this.target = image.getTarget();	
}

public int[] getFeatureValues() {
	return featureValues;
}

public int getTarget() {
	return target;
}

}
