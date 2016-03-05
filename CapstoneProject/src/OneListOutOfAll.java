import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class OneListOutOfAll {

	private static String fileNames[];

	public static void main(String args[]) {

		// files to check out
		fileNames = new String[] { "ArtsEntertainmentWordsStemmed.txt", "BeautyHealthWordsStemmed.txt",
				"BusinessFinanceWordsStemmed.txt", "FoodDrinkWordsStemmed.txt", "HomeGardenWordsStemmed.txt",
				"JournalReferenceWordsStemmed.txt", "PeopleMediaWordsStemmed.txt",
				"RecreationalHobbiesWordsStemmed.txt", "TechnologySocialMediaWordsStemmed.txt",
				"VehicleTravelTransportationWordsStemmed.txt" };

		//to store values
		HashMap<Integer, String> words = new HashMap<>();
		int key = 0;
		
		// to go through individual files
		for (int i = 0; i < fileNames.length; i++) {
			try {

				FileInputStream fileIn = new FileInputStream(fileNames[i]);
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
		File out = new File("allStemmedWords.txt");

		try {

			FileWriter fileWriter = new FileWriter(out);
			BufferedWriter writer = new BufferedWriter(fileWriter);

			for (int j = 0; j < words.size(); j++) {
				String wordStemmed = words.get(j);
				writer.write(wordStemmed);
				writer.write("\n");
			}

			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
