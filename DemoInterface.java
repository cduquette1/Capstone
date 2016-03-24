import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DemoInterface extends JFrame {

	public DemoInterface() {
		super("Web Classifer");
		setLayout(new BorderLayout());

		// add more information to the top maybe

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

	public static void main(String[] args) {
		new DemoInterface();

	}

}