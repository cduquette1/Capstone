import java.io.File;
import java.io.IOException;

/*
 * Courtney Duquette
 * Carolyn Lynch
 * 4/27/2016
 * Web Classification for Bot Detection
 * 
 * Class that will run Wget on website entered by user
 * 
 * Capstone Project
*/

public class WgetPage {

	static void runWget(String siteName) {

		//Strings for command and dir
		String command = "wget -o log --no-check-certificate --adjust-extension --page-requisites "
				+ "--execute robots=off"
				+ " --wait=30 --random-wait --user-agent=Mozilla ";
		
		//Tells Wget where to put file
		File dir = new File("C:/Users/Carolyn Lynch/Documents/College/workspaceMars/Capstone/HTML/sites");

		//Will download html and put in to proper extension
		try {
			Runtime.getRuntime().exec(command + siteName, null, dir);
		} catch (IOException e1) {
		}
	}
}