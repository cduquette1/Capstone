import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

/*
 * Class that will create the test vector, using stemmer and combine list
 * Creates vector with missing categories
 * Carolyn Lynch
 * Courtney Duquette
 * Capstone 2016
 */
public class TestVector {
    
    protected static void buildTestVector() throws IOException {
        String webTextFile = "Websites/UnknownWeb.txt";
        
        CombineList wordlist = new CombineList();
        wordlist.gatherStems();
        wordlist.makeList(wordlist.getInputFiles());
        ArrayList<String> wordBank = wordlist.getMapWords();
        
        ArrayList<String[]> testData = new ArrayList<String[]>();
        
        //navigate to HTML file and parse then stem
        FileInputStream fileIn = new FileInputStream(webTextFile);
        BufferedReader reader = new BufferedReader(new InputStreamReader(fileIn));
        
        String website = "";
        while(((website = reader.readLine()) !=  null)) {
            
            //structure name
            website = website.substring(8);
            
            if (website.contains("/")) {
                int index = website.indexOf("/");
                website = website.substring(0, index);
            }
            
            //parse all the HTML website
                Parser.parseHTML(website);
  
                //stems all website HTML
            Stemmer.stemHTML(website + ".txt");
            
            //create row for file
            String [] row = new String[wordBank.size() + 1];
            for(int j = 0; j < row.length; j++) {
                row[j] = "0";
            }
            
            //compare words in stemmed HTML to word bank
            FileInputStream stemFileIn = new FileInputStream("TextStemmed/" + website + ".txt");
            BufferedReader stemReader = new BufferedReader(new InputStreamReader(stemFileIn));
            
            String tempWord = "";
            while((tempWord = stemReader.readLine()) != null) {
                if(wordBank.indexOf(tempWord) != -1) {
                    row[wordBank.indexOf(tempWord)] = "1";
                }
            }
            stemFileIn.close();
            
            //assigning category to row
            String category = "?";
            row[row.length - 1] = category;
            
            //adding row to training data
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
        for(int i = 0; i < wordBank.size(); i++) {
            writer.println("@attribute " + wordBank.get(i) + " real");
        }
        writer.println("@attribute category {ArtsEntertainment, BeautyHealth, BusinessFinance, FoodDrink, " +
                "HomeGarden, JournalReference, PeopleMedia, Recreational, Technology, TransportationTravel}");
        writer.println();
        
        //Part 3: data
        writer.println("@data");
        for(int i = 0; i < testData.size(); i++) {
            String[] temp = testData.get(i);
            for(int j = 0; j < wordBank.size(); j++) {
                writer.print(temp[j] + ", ");
            }
            writer.print(temp[wordBank.size()]);
            writer.println();
        }
        
        writer.close();
    }
}
