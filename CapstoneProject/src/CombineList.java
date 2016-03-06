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

	private static ArrayList<String> fileNames;
	private static HashMap<Integer, String> outPutItems;
	private static int key;

	public CombineList() {

		fileNames = new ArrayList<String>();

		// hashmap to store values
		outPutItems = new HashMap<>();

		// key will be number, index
		key = 0;

	}

	public HashMap<Integer, String> getMapWords() {
		return outPutItems;
	}

	public void gatherStems() {
		fileNames.add("Words/ArtsEntertainment.txt");
		fileNames.add("Words/BeautyHealth.txt");
		fileNames.add("Words/BusinessFinance.txt");
		fileNames.add("Words/FoodDrink.txt");
		fileNames.add("Words/HomeGarden.txt");
		fileNames.add("Words/JournalReference.txt");
		fileNames.add("Words/PeopleMedia.txt");
		fileNames.add("Words/Recreational.txt");
		fileNames.add("Words/Technology.txt");
		fileNames.add("Words/TransportationTravel.txt");
	}

	public void gatherWebsites() {
		fileNames.add("Websites/ArtsEntertainmentWeb.txt");
		fileNames.add("Websites/BeautyHealthWeb.txt");
		fileNames.add("Websites/BusinessFinanceWeb.txt");
		fileNames.add("Websites/FoodDrinkWeb.txt");
		fileNames.add("Websites/HomeGardenWeb.txt");
		fileNames.add("Websites/JournalReferenceWeb.txt");
		fileNames.add("Websites/PeopleMediaWeb.txt");
		fileNames.add("Websites/RecreationWeb.txt");
		fileNames.add("Websites/TechnologyWeb.txt");
		fileNames.add("Websites/TransportationTravelWeb.txt");

	}

	public ArrayList<String> getInputFiles() {
		return fileNames;
	}

	public boolean deleteFileInput(String fileName) {
		return fileNames.remove(fileName);
	}

	public void addFileInput(String fileName) {
		fileNames.add(fileName);
	}

	public void makeList(ArrayList<String> arrayList) {

		// to go through individual files
		for (int i = 0; i < fileNames.size(); i++) {
			try {

				FileInputStream fileIn = new FileInputStream(fileNames.get(i));
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

		CombineList oneList = new CombineList();
		oneList.gatherStems();
		oneList.makeList(oneList.getInputFiles());
		oneList.writeFile("IO/allStems.txt");
	}
}