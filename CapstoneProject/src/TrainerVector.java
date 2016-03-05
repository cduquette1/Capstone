import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class TrainerVector {

	private static String fileNames[];

	public static void main(String args[]) {

		// files to check out
		fileNames = new String[] { "ArtsEntertainmentWordsStemmed.txt", "BeautyHealthWordsStemmed.txt",
				"BusinessFinanceWordsStemmed.txt", "FoodDrinkWordsStemmed.txt", "HomeGardenWordsStemmed.txt",
				"JournalReferenceWordsStemmed.txt", "PeopleMediaWordsStemmed.txt",
				"RecreationalHobbiesWordsStemmed.txt", "TechnologySocialMediaWordsStemmed.txt",
				"VehicleTravelTransportationWordsStemmed.txt" };

		HashMap<String, Integer> words = new HashMap<>();


		// to go through individual files
		for (int i = 0; i < fileNames.length; i++) {
			try {
				
				FileInputStream fileIn = new FileInputStream(fileNames[i]);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fileIn));
				File out = new File("allStemmedWords.txt");
				FileOutputStream fileOut = new FileOutputStream(out);

				// go in each file to make new list of all words, reduces
				// duplicates
				String line = "";
				while ((line = reader.readLine()) != null) {

					int key = 0;
					if (!words.containsValue(line)) {
						words.put(line, key);
						key++;
					}
				}

				fileIn.close();
				fileOut.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}
