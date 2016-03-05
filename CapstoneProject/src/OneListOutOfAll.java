import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class OneListOutOfAll {

	private static ArrayList<String> fileNames;
	private static HashMap<Integer, String> words;
	private static int key;

	public OneListOutOfAll() {


		fileNames = new ArrayList<String>(); 
		
		// hashmap to store values
		words = new HashMap<>();

		// key will be number, index
		key = 0;

	}

	public HashMap<Integer, String> getMap() {
		return words;
	}
	
	public void gatherStems() {
		fileNames.add("ArtsEntertainmentWordsStemmed.txt");
		fileNames.add("BeautyHealthWordsStemmed.txt");
		fileNames.add("BusinessFinanceWordsStemmed.txt");
		fileNames.add("FoodDrinkWordsStemmed.txt");
		fileNames.add("HomeGardenWordsStemmed.txt");
		fileNames.add("JournalReferenceWordsStemmed.txt");
		fileNames.add("PeopleMediaWordsStemmed.txt");
		fileNames.add("RecreationalHobbiesWordsStemmed.txt");
		fileNames.add("TechnologySocialMediaWordsStemmed.txt");
		fileNames.add("VehicleTravelTransportationWordsStemmed.txt");
	}
	
	public void gatherWebsites() {
		fileNames.add("ArtsEntertainment.txt");
		fileNames.add("BeautyHealth.txt");
		fileNames.add("BusinessFinance.txt");
		fileNames.add("FoodDrink.txt");
		fileNames.add("HomeGarden.txt");
		fileNames.add("JournalReference.txt");
		fileNames.add("PeopleMedia.txt");
		fileNames.add("SportsRecreation.txt");
		fileNames.add("TechnologySocialMedia.txt");
		fileNames.add("VehiclesTravelTransportation.txt");
		
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
	
	public void Run(String fileOutName) {

		// to go through individual files
		for (int i = 0; i < fileNames.size(); i++) {
			try {

				FileInputStream fileIn = new FileInputStream(fileNames.get(i));
				BufferedReader reader = new BufferedReader(new InputStreamReader(fileIn));

				// go in each file to make new list of all words, reduces
				// duplicates avoided
				String line = "";

				while (((line = reader.readLine()) != null)) {

					if (!words.containsValue(line) && !line.equals("")) {
						words.put(key, line);
						key++;
					}
				}

				fileIn.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/// writes all values of hash map words to output file
		File out = new File(fileOutName);

		try {

			FileWriter fileWriter = new FileWriter(out);
			BufferedWriter writer = new BufferedWriter(fileWriter);

			for (int j = 0; j < words.size(); j++) {
				writer.write(words.get(j));
				writer.write("\n");
			}

			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		
		OneListOutOfAll oneList = new OneListOutOfAll();
		oneList.gatherWebsites();
		oneList.Run("allWebsites.txt");
	}
}