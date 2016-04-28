import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

/*
 * Courtney Duquette
 * Carolyn Lynch
 * 4/27/2016
 * Web Classification for Bot Detection
 * 
 * Class that will create the test vector, using stemmer and combine list
 * Creates vector with missing categories
 * 
 * Capstone Project
*/

public class TestVector {

	//Builds test vector with features and websites
	protected void buildTestVector(String webTextFile) throws IOException {
		
		//Gathers stems of all words present in training data
		CombineList wordlist = new CombineList();
		wordlist.gatherStems();

		//Ensures a way to check for duplicaates
		wordlist.makeList(wordlist.getInputFiles());
		ArrayList<String> wordBank = wordlist.getListContents();

		//Array list will hold vector
		ArrayList<String[]> testData = new ArrayList<String[]>();

		//Navigate to HTML file and parse then stem
		FileInputStream fileIn = new FileInputStream(webTextFile);
		BufferedReader reader = new BufferedReader(new InputStreamReader(fileIn));

		String website = "";
		while (((website = reader.readLine()) != null)) {

			//Structure name
			website = website.substring(8);

			if (website.contains("/")) {
				int index = website.indexOf("/");
				website = website.substring(0, index);
			}

			//Parse all the HTML website
			Parser.parseHTML(website);

			//Stems all website HTML
			Stemmer.stemHTML(website + ".txt");

			//Create row for file
			String[] row = new String[wordBank.size() + 1];
			for (int j = 0; j < row.length; j++) {
				row[j] = "0";
			}

			//Compare words in stemmed HTML to word bank
			FileInputStream stemFileIn = new FileInputStream("TextStemmed/" + website + ".txt");
			BufferedReader stemReader = new BufferedReader(new InputStreamReader(stemFileIn));

			String tempWord = "";
			while ((tempWord = stemReader.readLine()) != null) {
				if (wordBank.indexOf(tempWord) != -1) {
					row[wordBank.indexOf(tempWord)] = "1";
				}
			}
			stemFileIn.close();

			//Assigning category to row
			String category = "?";
			row[row.length - 1] = category;

			//Adding row to training data
			testData.add(row);
		}

		fileIn.close();

		//Creating the text file that can be used by Weka
		File outputFile = new File("TestData.arff");
		PrintWriter writer = new PrintWriter(outputFile);

		//Part 1: relation
		writer.println("@relation classification");
		writer.println();

		//Part 2: attribute
		for (int i = 0; i < wordBank.size(); i++) {
			writer.println("@attribute " + wordBank.get(i) + " real");
		}
		writer.println("@attribute category {ArtsEntertainment, BeautyHealth, BusinessFinance, FoodDrink, "
				+ "HomeHobby, JournalReference, PeopleMedia, GameSport, Technology, TransportationTravel}");
		writer.println();

		//Part 3: data
		writer.println("@data");
		for (int i = 0; i < testData.size(); i++) {
			String[] temp = testData.get(i);
			for (int j = 0; j < wordBank.size(); j++) {
				writer.print(temp[j] + ", ");
			}
			writer.print(temp[wordBank.size()]);
			writer.println();
		}
		
		writer.close();
	}
}