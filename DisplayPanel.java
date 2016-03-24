import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

enum Category {
	ArtsEntertainment, BeautyHealth, BusinessFinance, FoodDrink, HomeHobby, JournalReference, PeopleMedia, GameSport, Technology, TransportationTravel
};

@SuppressWarnings("serial")
public class DisplayPanel extends JPanel {
	private JTextField website;
	private String siteName;
	private boolean trained;

	public DisplayPanel() {
		JLabel question = new JLabel("Enter Website to Classify: ");
		add(question, BorderLayout.WEST);

		website = new JTextField();
		website.setPreferredSize(new java.awt.Dimension(200, 20));
		add(website, BorderLayout.EAST);

		JLabel info = new JLabel("Please input website in www.websiteName.com");
		add(info, BorderLayout.SOUTH);

		trained = false;
	}

	protected void classify() {
		siteName = website.getText();

		// format input
		siteName = siteName.toLowerCase();
		boolean flag = checkFormat(siteName);
		if (!flag) {
			return;
		}

		if (!siteName.contains("www."))
			siteName = "www." + siteName;

		siteName = "https://" + siteName;

		// Write to Unknown File
		writeToFile(siteName);

		CombineList wordlist = new CombineList();
		wordlist.gatherWebsites();
		wordlist.makeList(wordlist.getInputFiles());
		ArrayList<String> websiteBank = wordlist.getListContents();

		if (!websiteBank.contains(siteName))
			runWget();
		
		// Run WekaClassifer
		WekaClassifier weka = new WekaClassifier();
		String theClass = weka.runClassifier(trained);
		trained = true;
		int comma = theClass.lastIndexOf(",");
		theClass = theClass.substring(comma + 1, theClass.length());

		int dialogResult = JOptionPane.showConfirmDialog(this,
				" The website " + siteName + " was classified as \n" + theClass + ". \nIs this correct?", null,
				JOptionPane.YES_NO_OPTION);

		if (dialogResult == JOptionPane.YES_OPTION) {
			JOptionPane.showMessageDialog(this, "Congratulations!!");
			website.setText("");

		} else if (dialogResult == JOptionPane.NO_OPTION) {

			Category properClass = (Category) JOptionPane.showInputDialog(this,
					"What should " + siteName + " be classified as?\n" + "Select from the choses below:",
					"Customized Dialog", JOptionPane.QUESTION_MESSAGE, null, Category.values(), "Unknown");

			if (properClass != null) {
				reclassify(properClass);
			}

			website.setText("");
		}
	}

	private boolean runWget() {

		// strings for command and dir
		String command = "wget -o logfile.tx --adjust-extension --page-requisites -l1 --no-parent --execute robots=off --wait=30 --random-wait --user-agent=Mozilla ";
		File dir = new File("C:/Users/Carolyn Lynch/Documents/College/workspaceMars/Capstone/HTML/sites");
		Process process = null;

		// will download html and put in to proper extension
		try {
			System.out.println("Wget called");
			process = Runtime.getRuntime().exec(command + siteName, null, dir);

		} catch (IOException e1) {
			return false;
		}
		process.destroyForcibly();
		return true;
	}

	private void reclassify(Category properClass) {
		String spacer = " ";

		System.out.println(siteName + spacer + properClass);
		CombineList.addToFile("Websites/" + properClass + "Web.txt", siteName);
	}

	private boolean checkFormat(String siteName) {
		if (siteName.contains("..") || siteName.contains(" ")) {
			JOptionPane.showMessageDialog(this, "Blank Space in Input. Please Correct and Try Again.", "Input Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		String[] symbols = { "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "_", "+", "=", "`", "~", "{", "[", "]",
				"}", "|", ";", ":", "\"", "\'", "<", ">", ",", "?" };
		for (int i = 0; i < symbols.length; i++) {
			if (siteName.contains(symbols[i])) {
				JOptionPane.showMessageDialog(this, "Invaild Symbol Present. Please Correct and Try Again.",
						"Input Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
	}

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