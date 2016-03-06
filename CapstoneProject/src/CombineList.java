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
		fileNames.add("Words/ArtsEntertainmentWordsStemmed.txt");
		fileNames.add("Words/BeautyHealthWordsStemmed.txt");
		fileNames.add("Words/BusinessFinanceWordsStemmed.txt");
		fileNames.add("Words/FoodDrinkWordsStemmed.txt");
		fileNames.add("Words/HomeGardenWordsStemmed.txt");
		fileNames.add("Words/JournalReferenceWordsStemmed.txt");
		fileNames.add("Words/PeopleMediaWordsStemmed.txt");
		fileNames.add("Words/RecreationalHobbiesWordsStemmed.txt");
		fileNames.add("Words/TechnologySocialMediaWordsStemmed.txt");
		fileNames.add("Words/VehicleTravelTransportationWordsStemmed.txt");
	}

	public void gatherWebsites() {
		fileNames.add("Websites/ArtsEntertainment.txt");
		fileNames.add("Websites/BeautyHealth.txt");
		fileNames.add("Websites/BusinessFinance.txt");
		fileNames.add("Websites/FoodDrink.txt");
		fileNames.add("Websites/HomeGarden.txt");
		fileNames.add("Websites/JournalReference.txt");
		fileNames.add("Websites/PeopleMedia.txt");
		fileNames.add("Websites/SportsRecreation.txt");
		fileNames.add("Websites/TechnologySocialMedia.txt");
		fileNames.add("Websites/VehiclesTravelTransportation.txt");

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