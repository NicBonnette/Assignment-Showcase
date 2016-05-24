
public class Feature {
private int[] rows = new int[4];
private int[] cols = new int[4];
private int[] signs = new int[4];

public Feature(int dim1, int dim2) {
	//randomly generate the row values
	for(int i = 0; i<4;i++){
		rows[i] = (int)(dim1*Math.random());
	}
	//randomly generate the col values
	for(int i = 0; i<4;i++){
		cols[i] = (int)(dim2*Math.random());
	}
	//randomly generate the sign values
	for(int i = 0; i<4;i++){
		signs[i] = (int)(2*Math.random());
	}
}

public int calculateValue(int[][] image){
	int matches = 0;
	for(int i = 0; i<4; i++){
		if(image[rows[i]][cols[i]] == signs[i]){
			matches++;
		}
	}
	return ((matches>=3)?1:0);
}

public String toString(){
	String ans = "Rows: ";
	for(int i = 0; i<4;i++){
		ans = ans + rows[i] + " ";
	}
	ans = ans + " Cols: ";
	for(int i = 0; i<4;i++){
		ans = ans + cols[i] + " ";
	}
	
	ans = ans + " Signs: ";
	for(int i = 0; i<4;i++){
		ans = ans + signs[i] + " ";
	}
	return ans;
}

}
