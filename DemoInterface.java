import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * Courtney Duquette
 * Carolyn Lynch
 * 4/27/2016
 * Web Classification for Bot Detection
 * Capstone Project
*/

//Enum used instead of String literals
enum Category { ArtsEntertainment, BeautyHealth, BusinessFinance, FoodDrink, GameSport, 
	HomeHobby, JournalReference, PeopleMedia, Technology, TransportationTravel, About }

@SuppressWarnings("serial")
public class DemoInterface extends JFrame {

	//Constructor that builds GUI windows
	public DemoInterface() {
		super("Web Classifer");
		setLayout(new BorderLayout());

		// add more information to the top maybe
		createMenu();

		final DisplayPanel display = new DisplayPanel();
		add(display, BorderLayout.CENTER);

		JPanel button = new JPanel(new FlowLayout());
		add(button, BorderLayout.SOUTH);

		JButton classify = new JButton("Classify");
		classify.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setCursor(new Cursor(Cursor.WAIT_CURSOR));
				display.classify();
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		button.add(classify);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(400, 125);
		setVisible(true);
	}
	
	private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        //Submenu with about
        JMenu descriptionMenu = new JMenu("Description of Categories");
    	
        //Creates a menuItem for each category and add action listener accordingly
        for(Category g : Category.values()) {
        	JMenuItem newItem = descriptionMenu.add(g.toString());
        	
        	newItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					displayDialog(g);
				}
        	});
        }

        //Adding everything to menu bar
        menuBar.add(descriptionMenu);
        menuBar.add(Box.createHorizontalGlue());
   }
	
	//Displays information about each category
    protected void displayDialog(Category cat) {
        String message = "";
        switch (cat) {
            case ArtsEntertainment:   
                message = "Videos, Books, Celebrity, TV, \nand Movie Websites";
                break;
            case BeautyHealth:        
                message = "Fitness, Health, Make-up, \nand Fashion Websites";
                break;
            case BusinessFinance:     
                message = "Business, Banking, Stock Market, \nand Financial Websites";
                break;
            case FoodDrink:           
                message = "Recipes, Restraunts, Beer, \nand Food Blog Websites";
                break;
            case HomeHobby:           
                message = "Home Searching, Home Decor Ideas, \nand Shopping Websites";
                break;
            case JournalReference:    
                message = "Encyclopedia, Wiki, Dictionary, \nand Journal Websites";
                break;
            case PeopleMedia:         
                message = "News, College, Government, \nand Social Media Websites";
                break;
            case GameSport:          
                message = "Online Games, Betting, Sports, \nand Recreation Websites";
                break;
            case Technology:          
                message = "Computer Information, Computer Brands, \nand Science Websites";
                break;
            case TransportationTravel:
                message = "Car Company, Hotel, Airplanes, \nand Public Transportation Websites";
                break;
            default: 
                message = "CPSC 498 Capstone: Web Classifier \nCourtney Duquette & Carolyn Lynch \nSpring 2016";
                break; 
        }
        JOptionPane.showMessageDialog(this, message, cat.toString(), JOptionPane.INFORMATION_MESSAGE);
    }

    //Point that program runs from
	public static void main(String[] args) {
		new DemoInterface();
	}
}