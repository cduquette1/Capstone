import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class CombineList {

	private static ArrayList<String> fileInput;
	private static HashMap<Integer, String> outPutItems;
	private static int key;

	public CombineList() {

		fileInput = new ArrayList<String>();

		// hashmap to store values
		outPutItems = new HashMap<>();

		// key will be number, index
		key = 0;

	}

	public HashMap<Integer, String> getMapWords() {
		return outPutItems;
	}

	public void gatherStems() {
		fileInput.add("Words/ArtsEntertainment.txt");
		fileInput.add("Words/BeautyHealth.txt");
		fileInput.add("Words/BusinessFinance.txt");
		fileInput.add("Words/FoodDrink.txt");
		fileInput.add("Words/HomeGarden.txt");
		fileInput.add("Words/JournalReference.txt");
		fileInput.add("Words/PeopleMedia.txt");
		fileInput.add("Words/Recreational.txt");
		fileInput.add("Words/Technology.txt");
		fileInput.add("Words/TransportationTravel.txt");
	}

	public void gatherWebsites() {
		fileInput.add("Websites/ArtsEntertainmentWeb.txt");
		fileInput.add("Websites/BeautyHealthWeb.txt");
		fileInput.add("Websites/BusinessFinanceWeb.txt");
		fileInput.add("Websites/FoodDrinkWeb.txt");
		fileInput.add("Websites/HomeGardenWeb.txt");
		fileInput.add("Websites/JournalReferenceWeb.txt");
		fileInput.add("Websites/PeopleMediaWeb.txt");
		fileInput.add("Websites/RecreationalWeb.txt");
		fileInput.add("Websites/TechnologyWeb.txt");
		fileInput.add("Websites/TransportationTravelWeb.txt");

	}

	public ArrayList<String> getInputFiles() {
		return fileInput;
	}
	
	public boolean deleteFileInput(String fileName) {
		return fileInput.remove(fileName);
	}

	public void addFileInput(String fileName) {
		fileInput.add(fileName);
	}

	public void makeList(ArrayList<String> arrayList) {

		// to go through individual files
		for (int i = 0; i < fileInput.size(); i++) {
			try {

				FileInputStream fileIn = new FileInputStream(fileInput.get(i));
				BufferedReader reader = new BufferedReader(new InputStreamReader(fileIn));

				// go in each file to make new list of all words, reduces
				// duplicates avoided
				String line = "";

				while (((line = reader.readLine()) != null)) {

					if (!outPutItems.containsValue(line) && !line.equals("")) {
						outPutItems.put(key, line);
						key++;
					}
				}

				fileIn.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void writeFile(String fileOutName) {

		/// writes all values of hash map words to output file
		File out = new File(fileOutName);

		try {

			FileWriter fileWriter = new FileWriter(out);
			BufferedWriter writer = new BufferedWriter(fileWriter);

			for (int j = 0; j < outPutItems.size(); j++) {
				writer.write(outPutItems.get(j));
				writer.write("\n");
			}

			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {

	}
}