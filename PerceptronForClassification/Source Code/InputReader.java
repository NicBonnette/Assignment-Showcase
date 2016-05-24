import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is used for loading data from a file given as an argument to the
 * main method in the Perceptron problem
 * 
 * @author Nic Bonnette
 *
 */
public class InputReader {
	private final File dataFile;
	private ArrayList<ImageData> instances;
	
	//the dimensions of all the image data read should be the same, 
	//so we keep track of the dimensions to make sure that this holds 
	//for the data read
	private int dim1 = -1;
	private int dim2 = -1;
	
	

	public InputReader(String fileName) {
		this.dataFile = new File(fileName);
		instances = new ArrayList<ImageData>();
		loadData();
	}

	/*
	 * attempts to open data file and retrieve image data blocks
	 * 
	 * each data block starts with the line "P1", then a line giving the
	 * classification, then a line with 2 integers separated by a space, giving
	 * the dimensions of the data then one or more lines of 0/1data
	 */
	public void loadData() {
		try (Scanner scanner = new Scanner(dataFile)) {
			while (scanner.hasNextLine()) {
				// read the P1 header
				String header = scanner.nextLine();
				if (header.equals("P1")) {
					int target = 0;
					int[][] image;
					// read the target classification
					String classification = scanner.nextLine();
					if (classification.equals("#Yes")) {
						target = 1;
					} else if (classification.equals("#other")) {
						target = 0;
					} else {
						System.out.printf("Classification, %s, was not of allowed type%n",classification);
					}
					// read the dimensions
					String dimensions = scanner.nextLine();
					Scanner dimScanner = new Scanner(dimensions);
					int dim = dimScanner.nextInt();
					if(this.dim1 == -1){ this.dim1 = dim;}
					else if(this.dim1 != dim){ 	
						System.out.printf("Dimension of %d, did not match expected value of %d%n",dim, dim1);
					 }
					dim = dimScanner.nextInt();
					if(this.dim2 == -1){ this.dim2 = dim;}
					else if(this.dim2 != dim){ 	
						System.out.printf("Dimension of %d, did not match expected value of %d%n",dim, dim2);
					 }
					dimScanner.close();

					// read the image data
					image = new int[this.dim1][this.dim2];
					int count = 0;
					while (count < this.dim1 * this.dim2) {
						String data = scanner.nextLine();
						for (int i = 0; i < data.length(); i++) {
							count++;
							int xPos = (count-1) / this.dim1;
							int yPos = (count-1) % this.dim1;
							//System.out.printf("Count up to %d out of %d, xPos %d, yPos %d%n",count,dim1 * dim2,xPos,yPos);
							
							char dat = data.charAt(i);
							if (dat == '1') {
								image[xPos][yPos] = 1;
							} else if (dat == '0') {
								image[xPos][yPos] = 0;
							} else {
								System.out.println("Oh-oh, tried to scan data, but char was of wrong from");
							}
							if (count == this.dim1 * this.dim2) {
								break;
							}
						}
					}
				//make the new ImageData element, and add it to the instance array
					instances.add(new ImageData(image, target));
					
				} else {
					System.out.printf("Was expecting a P1 header...got %s instead, skipping%n",header);
				}
			}
			scanner.close();
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		System.out.printf("Read %d instances%n", instances.size());
	}

	public ArrayList<ImageData> getInstances() {
		return instances;
	}

	public int getDim1() {
		return this.dim1;
	}
	
	public int getDim2() {
		return this.dim2;
	}
}
