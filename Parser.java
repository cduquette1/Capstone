import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.FilenameUtils;

/**
 * Parse HTML file to pull the text contained in between the two tags
 * Input: HTML file
 * Output: Text file containing word
 * 
 * @author Courtney Duquette
 * @version 2/11/2016
 */

public class Parser {

    public static void main(String[] args) throws IOException {
        File dir = new File("C:/Users/Courtney/Documents/Christopher Newport University/Spring Semester 2016/CPSC 498/HTML");
        
        String[] extensions = {"html"};
        List<File> files = (List<File>) FileUtils.listFiles(dir, extensions, true);
        
        for (File file : files) {
            File outputFile = new File(dir + "/Text/" + FilenameUtils.removeExtension(file.getName()) + ".txt");
            PrintWriter writer = new PrintWriter(outputFile); 
            
            Document doc = Jsoup.parse(file, null);
        
            Elements elements = doc.select("title, li");
    
            for(Element e : elements) {
                if(!(e.text().equals("")))
                    writer.println(e.text());
            }
            
            writer.close();
        }
    }
}