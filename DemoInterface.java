import java.awt.BorderLayout;
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

@SuppressWarnings("serial")
public class DemoInterface extends JFrame {

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
				display.classify();
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
        
        //About Menu
        JMenu aboutMenu = new JMenu("About");
        JMenuItem about = aboutMenu.add("About");
        
        //Category Menu
        JMenu descriptionMenu = new JMenu("Description of Categories");
        JMenuItem arts = descriptionMenu.add("ArtsEntertainment");
        JMenuItem health = descriptionMenu.add("BeautyHealth");
        JMenuItem busi = descriptionMenu.add("BusinessFinance");
        JMenuItem food = descriptionMenu.add("FoodDrink");
        JMenuItem home = descriptionMenu.add("HomeHobby");
        JMenuItem ref = descriptionMenu.add("JournalReference");
        JMenuItem media = descriptionMenu.add("PeopleMedia");
        JMenuItem sport = descriptionMenu.add("GameSport");
        JMenuItem tech = descriptionMenu.add("Technology");
        JMenuItem travel = descriptionMenu.add("TransportationTravel");
        
        //Adding everything to menu bar
        menuBar.add(descriptionMenu);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(aboutMenu);
        
        about.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                displayDialog("About");
            } 
        });
        
        arts.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                displayDialog("ArtsEntertainment");
            }
        });
        
        health.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                displayDialog("BeautyHealth");
            }
        });
        
        busi.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                displayDialog("BusinessFinance");
            }
            
        });
        
        food.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                displayDialog("FoodDrink");
            }
        });
        
        home.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                displayDialog("HomeHobby");
            }
        });
        
        ref.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                displayDialog("JournalReference");
            }
        });
        
        media.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                displayDialog("PeopleMedia");
            }
            
        });
        
        sport.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                displayDialog("GameSport");
            }
            
        });
        
        tech.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                displayDialog("Technology");
            }
        });
        
        travel.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                displayDialog("TransportationTravel");
            }
        });
        
    }
    
    protected void displayDialog(String cat) {
        String message = "";
        switch (cat) {
            case "ArtsEntertainment":   
                message = "Videos, Books, Celebrity, TV, \nand Movie Websites";
                break;
            case "BeautyHealth":        
                message = "Fitness, Health, Make-up, \nand Fashion Websites";
                break;
            case "BusinessFinance":     
                message = "Business, Banking, Stock Market, \nand Financial Websites";
                break;
            case "FoodDrink":           
                message = "Recipes, Restraunts, Beer, \nand Food Blog Websites";
                break;
            case "HomeHobby":           
                message = "Home Searching, Home Decor Ideas, \nand Shopping Websites";
                break;
            case "JournalReference":    
                message = "Encyclopedia, Wiki, Dictionary, \nand Journal Websites";
                break;
            case "PeopleMedia":         
                message = "News, College, Government, \nand Social Media Websites";
                break;
            case "GameSport":           
                message = "Online Games, Betting, Sports, \nand Recreation Websites";
                break;
            case "Technology":          
                message = "Computer Information, Computer Brands, \nand Science Websites";
                break;
            case "TransportationTravel":
                message = "Car Company, Hotel, Airplanes, \nand Public Transportation Websites";
                break;
            default: 
                message = "CPSC 498 Capstone: Web Classifier \nCourtney Duquette & Carolyn Lynch \nSpring 2016";
                break; 
        }
        JOptionPane.showMessageDialog(this, message, cat, JOptionPane.INFORMATION_MESSAGE);
        
    }

	public static void main(String[] args) {
		new DemoInterface();

	}

}