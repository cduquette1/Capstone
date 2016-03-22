import java.awt.BorderLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


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
        
        JLabel info = new JLabel("Please input website in www.websitename.com");
        add(info, BorderLayout.SOUTH);
        
        trained = false;
    }
    
    
    protected void classify(){
        siteName = website.getText();
        
        //format input
        siteName = siteName.toLowerCase();
        checkFormat(siteName);
        siteName = "https://" + siteName;
        
        //Write to Unknown File
        writeToFile(siteName);
        
        // run wget
        //runWget();
        
        // run WekaClassifer
        WekaClassifier weka = new WekaClassifier();
        String theClass = weka.runClassifier(trained);
        trained = true;
        int comma = theClass.lastIndexOf(",");
        theClass = theClass.substring(comma+1, theClass.length());
        System.out.println(theClass);
        
        int dialogResult = JOptionPane.showConfirmDialog(this, " The website " + siteName + " was classified as \n" + theClass +  ". \nIs this correct?", 
                null, JOptionPane.YES_NO_OPTION);
        if(dialogResult == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "Congratulations!!");     
        }
        else if (dialogResult == JOptionPane.NO_OPTION) {
            Object[] possibilities = {"ArtsEntertainment", "BeautyHealth", "BusinessFinance", "FoodDrink", "HomeHobby", "JournalReference", "PeopleMedia", 
                    "GameSport", "Technology", "TransportationTravel"};
            
            String properClass = (String)JOptionPane.showInputDialog(this, "What should " + siteName + " be classified as?\n" + "Select from the choses below:", 
                    "Customized Dialog", JOptionPane.QUESTION_MESSAGE, null, possibilities, "Unknown");
            
            reclassify(properClass);
        }
    }
    
    private void runWget() {
        String whatToRun = "wget -h";
        try {
          Runtime rt = Runtime.getRuntime();
          Process proc = rt.exec(whatToRun);
          int exitVal = proc.waitFor();
          System.out.println("Process exitValue:" + exitVal);
        } 
        catch (Throwable t) {
            t.printStackTrace();
            throw new IllegalArgumentException();
        } 
    }

    private void reclassify(String properClass) {
        switch (properClass) {
            case "ArtsEntertainment":
                System.out.println(siteName + " ArtsEntertainment");
                break;
            case "BeautyHealth":
                System.out.println(siteName + " BeautyHealth");
                break;
            case "BusinessFinance":
                System.out.println(siteName + " BusinessFinance");
                break;
            case "FoodDrink":
                System.out.println(siteName + " FoodDrink");
                break;
            case "HomeHobby":
                System.out.println(siteName + " HomeHobby");
                break;
            case "JournalReference":
                System.out.println(siteName + " JournalReference");
                break;
            case "PeopleMedia":
                System.out.println(siteName + " PeopleMedia");
                break;
            case "GameSport":
                System.out.println(siteName + " GameSport");
                break;
            case "Technology":
                System.out.println(siteName + " Technology");
                break;
            case "TransportationTravel":
                System.out.println(siteName + " TransportationTravel");
                break;
            default:
                //throw new IllegalArgumentException(siteName + " No Classification Type");
                System.out.println(siteName + " No Classification Type");
        }
    }

    private void checkFormat(String siteName) {
        if(siteName.contains(" ")) {
            JOptionPane.showMessageDialog(this, "Blank Space in Input. Please Correct and Try Again.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if(siteName.contains("..")) {
            JOptionPane.showMessageDialog(this, "Blank Space in Input. Please Correct and Try Again.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if(!siteName.contains("www.")) {
            JOptionPane.showMessageDialog(this, "Missing \"www.\". Please Correct and Try Again.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if(!siteName.contains(".com")){
            JOptionPane.showMessageDialog(this, "Missing \".com\". Please Correct and Try Again.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String[] symbols = {"!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "_", "+", "=", "`", "~", "{", "[", "]", "}", "|", ";", ":", "\"", "\'", "<", ">", ",", "?"};
        for(int i = 0; i < symbols.length; i++) {
            if(siteName.contains(symbols[i])) {
                JOptionPane.showMessageDialog(this, "Invaild Symbol Present. Please Correct and Try Again.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
    
    private void writeToFile (String input){
        try {
            File outputFile = new File("Websites/DemoUnknownWeb.txt");
            PrintWriter writer = new PrintWriter(outputFile);
            
            writer.print(input);
            
            writer.close();
        }
        catch (FileNotFoundException e) {
            throw new IllegalArgumentException();
        }  
    }
}