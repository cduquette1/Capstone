import java.io.File;
import java.io.IOException;

public class WgetPage {

	public static void main(String[] args) {

		runWget("www.youtube.com");
		/*
		 * try { Document document = Jsoup.connect("http://google.com").get(); }
		 * catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
	}

	private static void runWget(String siteName) {

		//strings for command and dir
		String command = "wget --adjust-extension --page-requisites --execute robots=off --wait=30 --random-wait --user-agent=Mozilla ";
		File dir = new File("C:/Users/Carolyn Lynch/Documents/College/workspaceMars/Capstone/HTML/demoSite");
		
		//will download html and put in to proper extension
		try {
			Process process = Runtime.getRuntime().exec(command + siteName,
					   null, dir);
			process.exitValue();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
