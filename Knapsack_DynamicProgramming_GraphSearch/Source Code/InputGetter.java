import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This class is used for loading knapsack data from a file given as an argument to the main method
 * 
 * @author Nic
 *
 */
public class InputGetter {
	File file;//the file from which to get data
	HashMap<Integer,Thing> thingMap;//stores the Thing data while reading

	public InputGetter(String fileName) {
		super();
		this.file = new File(fileName);
		thingMap = new HashMap<Integer,Thing>();
		loadThings();
	}

	/*
	 * attempts to open file and retrieve the first two lines of text
	 * converting each line into an array of chars to instantiate 
	 * fields A and B
	 */
	public void loadThings(){
		try (Scanner scanner = new Scanner(file)) {
			//first read the header to see if we are doing multi or single items
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				Thing nextThing = deconstruct(line);
				encorporate(nextThing);
			}
			scanner.close();
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
	
	public ArrayList<Thing> getThings(){
		ArrayList<Thing>things = new ArrayList<Thing>(thingMap.values());
		return things;
		}

	/*
	 * deconstruct takes a line of text (retrieved from file in loadSequences) 
	 * and turns it into a thing for possible knapsack addition (could be a MultiThing)
	 */
	private Thing deconstruct(String line) {
		Thing ans = null;
		//the line should be in the order: name, value, weight, number
		try (Scanner scanner = new Scanner(line)) {
			if(!scanner.hasNext()){
				throw new IOException("no name found!");
			}
			String name = scanner.next();
			if(!scanner.hasNextInt()){
				throw new IOException("no value found!");
			}
			int value = scanner.nextInt();
			if(!scanner.hasNextInt()){
				throw new IOException("no weight found!");
			}
			int weight = scanner.nextInt();
			int number = 1;
			if (scanner.hasNextInt()){
				number = scanner.nextInt();}

			ans = new Thing(name, value, weight, number);		
			scanner.close();
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return ans;
	}

	private void encorporate (Thing toAdd){
		Thing oldCopy = thingMap.get(toAdd.hashCode());
		if(oldCopy == null){
			//just add the new Thing
			thingMap.put(toAdd.hashCode(), toAdd);
			return;
		}
		//otherwise we should combine the copies!
		oldCopy.increaseNum(toAdd.getNum());
	}
}


