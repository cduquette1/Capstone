import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CombineList {

	private static ArrayList<String> fileNames;
	private static ArrayList<String> outPutItems;

	public CombineList() {

		fileNames = new ArrayList<String>();

		// list to store values
		outPutItems = new ArrayList<String>();
	}

	public ArrayList<String> getListContents() {
		return outPutItems;
	}

	public void gatherStems() {
		fileNames.add("Words/ArtsEntertainment.txt");
		fileNames.add("Words/BeautyHealth.txt");
		fileNames.add("Words/BusinessFinance.txt");
		fileNames.add("Words/FoodDrink.txt");
		fileNames.add("Words/HomeHobby.txt");
		fileNames.add("Words/JournalReference.txt");
		fileNames.add("Words/PeopleMedia.txt");
		fileNames.add("Words/GameSport.txt");
		fileNames.add("Words/Technology.txt");
		fileNames.add("Words/TransportationTravel.txt");
	}

	public void gatherWebsites() {
		fileNames.add("Websites/ArtsEntertainmentWeb.txt");
		fileNames.add("Websites/BeautyHealthWeb.txt");
		fileNames.add("Websites/BusinessFinanceWeb.txt");
		fileNames.add("Websites/FoodDrinkWeb.txt");
		fileNames.add("Websites/HomeHobbyWeb.txt");
		fileNames.add("Websites/JournalReferenceWeb.txt");
		fileNames.add("Websites/PeopleMediaWeb.txt");
		fileNames.add("Websites/GameSportWeb.txt");
		fileNames.add("Websites/TechnologyWeb.txt");
		fileNames.add("Websites/TransportationTravelWeb.txt");
		fileNames.add("Websites/UnknownWeb.txt");
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

					if (!(outPutItems.contains(line)) && !line.equals("")) {
						outPutItems.add(line);
					}
				}

				fileIn.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void addToFile(String fileName, String webSite) {

		try {
			FileWriter fileWriter = new FileWriter(fileName, true);
			BufferedWriter writer = new BufferedWriter(fileWriter);
			writer.newLine();
			writer.write(webSite);
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
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
}