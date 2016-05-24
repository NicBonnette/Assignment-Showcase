import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is used for loading data from a file of binary feature sets of spam data used in the
 * NaiveBayes class.
 * 
 * @author Nic Bonnette
 */
public class SpamInputReader {
	private String spamFileName;
	private File spamDataFile;
	
	private ArrayList<int[]> data; //each int[] contains 13 items, the first 12 are binary (0 or 1)
	//data relating to spam feature presence (1)/absence (0).
	//the last int is a classification 0(not spam)/1 (spam).. or -1 to represent unclassified data 

	public SpamInputReader(String spamDataFileName) {
		this.spamFileName = spamDataFileName;
		this.spamDataFile = new File(spamDataFileName);
		loadData();
	}

	/*
	 * attempts to open data file and retrieve cancer attribute data blocks
	 */
	public void loadData() {
		data = new ArrayList<int[]>();
		Scanner scanner;
		try {
			scanner = new Scanner(spamDataFile);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.isEmpty()) {
					break;
				}
				Scanner lineScanner = new Scanner(line);

				int[] featuresClass = new int[13];
				for (int i = 0; i < 12; i++) {
					featuresClass[i] = lineScanner.nextInt();
				}
				if(lineScanner.hasNextInt()){
					featuresClass[12] = lineScanner.nextInt();
				}
				else{
					featuresClass[12] = -1;
				}
				data.add(featuresClass);
				lineScanner.close();
			}
			scanner.close();
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		System.out.printf("Read %d spam records from %s %n", data.size(), spamFileName);
	}

	// ================getters==============================

	public ArrayList<int[]> getData() {
		return data;
	}

}
