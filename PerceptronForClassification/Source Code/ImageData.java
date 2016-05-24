
public class ImageData {
	int[][] image;
	int target;
	
	public ImageData(int[][] image, int target) {
		this.image = image;
		this.target = target;
	}

	public int[][] getImage() {
		return image;
	}
	
	public int getTarget(){
		return this.target;
	}

}
