import java.awt.BorderLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * Courtney Duquette
 * Carolyn Lynch
 * 4/27/2016
 * Web Classification for Bot Detection
 * Capstone Project
*/

//Enum used instead of String literals
enum Category1 {
	ArtsEntertainment, BeautyHealth, BusinessFinance, FoodDrink, HomeHobby, JournalReference, PeopleMedia, GameSport, Technology, TransportationTravel
};

@SuppressWarnings("serial")
public class DisplayPanel extends JPanel {

	private JTextField website;
	private String siteName;
	private boolean trained;

	//Creates popup box for input
	public DisplayPanel() {
		JLabel question = new JLabel("Enter Website to Classify: ");
		add(question, BorderLayout.WEST);

		website = new JTextField();
		website.setPreferredSize(new java.awt.Dimension(200, 20));
		add(website, BorderLayout.EAST);

		JLabel info = new JLabel("Please input website with the format: www.websiteName.com");
		add(info, BorderLayout.SOUTH);

		trained = false;
	}

	//Classifies input
	protected void classify() {
		
		siteName = website.getText();

		boolean flag = checkFormat(siteName);
		if (!flag) {
			return;
		}

		//Gathers all websites and words for training data
		CombineList wordlist = new CombineList();
		wordlist.gatherWebsites();
		wordlist.makeList(wordlist.getInputFiles());
		ArrayList<String> websiteBank = wordlist.getListContents();

		//If website inputed is not included, run to get files
		if (!websiteBank.contains(siteName)) {
			WgetPage.runWget(siteName);
		}
		
		siteName = "https://" + siteName;
		
		//Write to Unknown File
		writeToFile(siteName);

		//Run WekaClassifer
		WekaClassifier weka = new WekaClassifier();
		String theClass = weka.runClassifier(trained);
		trained = true;
		
		if (theClass != null) {
			int comma = theClass.lastIndexOf(",");
			theClass = theClass.substring(comma + 1, theClass.length());

			//Dialog windows upon running Weka
			int dialogResult = 0;

			dialogResult = JOptionPane.showConfirmDialog(this,
					" The website " + siteName + " was classified as \n" + theClass + ". \nIs this correct?", null,
					JOptionPane.YES_NO_OPTION);

			if (dialogResult == JOptionPane.YES_OPTION) {
				JOptionPane.showMessageDialog(this, "Congratulations!!");
				website.setText("");

			} else if (dialogResult == JOptionPane.NO_OPTION) {

				Category1 properClass = (Category1) JOptionPane.showInputDialog(this,
						"What should " + siteName + " be classified as?\n" + "Select from the choses below:",
						"Customized Dialog", JOptionPane.QUESTION_MESSAGE, null, Category1.values(), "Unknown");

				if (properClass != null) {
					reclassify(properClass);
				}
				website.setText("");
			}
		}
	}

	//To put website in correct classification web text file
	private void reclassify(Category1 properClass) {
		CombineList.addToFile("Websites/" + properClass + "Web.txt", siteName);
	}

	//Check format of URL inputed by user
	private boolean checkFormat(String siteName) {
		
		//Format input
		siteName = siteName.toLowerCase();
		
		if (siteName.contains("..") || siteName.contains(" ")) {
			JOptionPane.showMessageDialog(this, "Blank Space in Input. Please Correct and Try Again.", "Input Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		String[] symbols = { "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "_", "+", "=", "`", "~", "{", "[", "]",
				"}", "|", ";", ":", "\'", "<", ">", ",", "?" };
		for (int i = 0; i < symbols.length; i++) {
			if (siteName.contains(symbols[i])) {
				JOptionPane.showMessageDialog(this, "Invaild Symbol Present. Please Correct and Try Again.",
						"Input Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		
		if (!siteName.contains("www."))
			siteName = "www." + siteName;
		
		return true;
	}

	//Append website to web text file for including in training set
	private void writeToFile(String input) {
		try {
			File outputFile = new File("Websites/DemoUnknownWeb.txt");
			PrintWriter writer = new PrintWriter(outputFile);

			writer.print(input);
			writer.close();

		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException();
		}
	}
}