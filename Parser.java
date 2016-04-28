import java.io.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*
 * Courtney Duquette
 * Carolyn Lynch
 * 4/27/2016
 * Web Classification for Bot Detection
 * 
 * Parse HTML file to pull the text contained in between the two tags Input:
 * HTML file Output: Text file containing word
 * 
 * Capstone Project
*/

public class Parser {

	//Will parse the HTML source to get only wanted contents
	public static void parseHTML(String filename) throws IOException {
		String folder = "HTML/sites/" + filename + "/";
		File file = new File(folder + "index.html");

		File outputFile = new File("Text/" + filename + ".txt");
		PrintWriter writer = new PrintWriter(outputFile);

		Document doc = Jsoup.parse(file, null);

		Elements elements = doc.select("title, li");

		for (Element e : elements) {
			if (!(e.text().equals("")))
				writer.println(e.text());
		}
		writer.close();
	}
}