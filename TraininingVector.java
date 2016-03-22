import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

/*
 * Class that will create training vector, using stemmer and combine list
 * Runs the parser/stem for all files in websites
 * Carolyn Lynch
 * Courtney Duquette
 * Capstone 2016
 */
public class TraininingVector {
    
    protected void buildTrainVector() throws IOException {
        String[] webTextFiles = {"Websites/ArtsEntertainmentWeb.txt", "Websites/BeautyHealthWeb.txt", "Websites/BusinessFinanceWeb.txt", 
                "Websites/FoodDrinkWeb.txt", "Websites/HomeHobbyWeb.txt", "Websites/JournalReferenceWeb.txt", "Websites/PeopleMediaWeb.txt", 
                "Websites/GameSportWeb.txt", "Websites/TechnologyWeb.txt", "Websites/TransportationTravelWeb.txt"
        };
        
        CombineList wordlist = new CombineList();
        wordlist.gatherStems();
        wordlist.makeList(wordlist.getInputFiles());
        ArrayList<String> wordBank = wordlist.getMapWords();
        
        ArrayList<String[]> trainingData = new ArrayList<String[]>();
        
        //navigate to HTML file and parse then stem
        for(int i = 0; i < webTextFiles.length; i++) {
            FileInputStream fileIn = new FileInputStream(webTextFiles[i]);
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
                String category = webTextFiles[i].substring(9);
                int temp = category.indexOf("W");
                category = category.substring(0, temp);
                row[row.length - 1] = category;
                
                //adding row to training data
                trainingData.add(row);
            }
            
            fileIn.close();
        }
        
        //Creating the text file that can be used by Weka
        File outputFile = new File("TrainingData.arff");
        PrintWriter writer = new PrintWriter(outputFile); 
        
        //Part 1: relation
        writer.println("@relation classification");
        writer.println();
        
        //Part 2: attribute
        for(int i = 0; i < wordBank.size(); i++) {
            writer.println("@attribute " + wordBank.get(i) + " real");
        }
        writer.println("@attribute category {ArtsEntertainment, BeautyHealth, BusinessFinance, FoodDrink, " +
                "HomeHobby, JournalReference, PeopleMedia, GameSport, Technology, TransportationTravel}");
        writer.println();
        
        //Part 3: data
        writer.println("@data");
        for(int i = 0; i < trainingData.size(); i++) {
            String[] temp = trainingData.get(i);
            for(int j = 0; j < wordBank.size(); j++) {
                writer.print(temp[j] + ", ");
            }
            writer.print(temp[wordBank.size()]);
            writer.println();
        }
        
        writer.close();
    }
}
