 import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/*
 * Class will stem and root words pulled from html source
 * Input is a file with extracted html code
 * 
 * Carolyn Lynch
 * 2/11/2016
*/

public class Stem {

	File inputFile;

	public Stem(File inputFile) {
		this.inputFile = inputFile;
	}

	public void setFile(File inputFile) {
		this.inputFile = inputFile;
	}

	public void rootWordsFile() {
		String line = "";

		try {
			FileReader reader = new FileReader(inputFile);
			BufferedReader bufferedReader = new BufferedReader(reader);

			while ((line = bufferedReader.readLine()) != null) {
				System.out.println("before: " + line);
				line = findRoot(line);
				System.out.println("after: " + line + "\n");
			}

			reader.close();
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String findRoot(String word) {

		ArrayList<Character> vowels = new ArrayList<>();
		vowels.add('a');
		vowels.add('e');
		vowels.add('i');
		vowels.add('o');
		vowels.add('u');

		// checks -ies, and then adds y
		// EX: supplies or supply
		if ((word.length() - 3 > -1) && (word.substring(word.length() - 3).equals("ies"))) 
		{
			if (!(vowels.contains(word.charAt(word.length() - 4)))) {
				word = word.substring(0, word.length() - 3);
				word = word + "y";
			}
		}
		// checks for -es, deals with -hes and -ses
		else if ((word.length() - 2 > -1) && ((word.substring(word.length() - 2).equals("es")))) 
		{
			ArrayList<Character> possiblities = new ArrayList<>();
			possiblities.add('h');
			possiblities.add('z');
			possiblities.add('s');
			possiblities.add('x');
			possiblities.add('o');
			possiblities.add('c');

			if (possiblities.contains(word.charAt(word.length() - 3))) 
			{
				word = word.substring(0, word.length() - 2);
			}
		}
		// checks for -s but also make sure words with -ss stay corrected
		if ((word.length() - 2 > 0) & (word.charAt(word.length() - 1) == 's')
				&& !(word.substring(word.length() - 2).equals("ss"))) 
		{
			word = word.substring(0, word.length() - 1);
		}

		// separate sbecause independent of plural form
		// checks for -
		if (word.contains("-")) 
		{
			int indexOfHyphen = word.indexOf("-");
			String beginning = word.substring(0, indexOfHyphen);
			String ending = word.substring(indexOfHyphen + 1);
			word = beginning + ending;
		}
		// checks for -ed
		if ((word.length() - 2 > -1) && word.substring(word.length() - 2).equals("ed")) 
		{
			word = word.substring(0, word.length() - 2);
		}

		return word;
	}

	// for testing purposes
	public static void main(String[] args) {
		/*File inputFile = new File("testWords.txt");
		Stem stem = new Stem(inputFile);
		stem.rootWordsFile();
		 */
	}

}
