package homework;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class SwingNavigator {
	static int speed;
	static int angle;

	final static int SPEED_MIN = 0;
	final static int SPEED_MAX = 50;
	final static int SPEED_INIT = 0;

	final static int ANGLE_MIN = -90;
	final static int ANGLE_MAX = 90;
	final static int ANGLE_INIT = 0;

	final static int REFRESH_RATE = 50;

	private static void createAndShowGUI() {

		/* Create the window */
		JFrame frame = new JFrame("Swing Navigator"); // JFrame default layout:
														// BorderLayout
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Optional

		/* Create the major frame components */
		final VisualisationPanel visualisationPanel = new VisualisationPanel();
		visualisationPanel.setBorder(BorderFactory.createBevelBorder(1));
		final JPanel navigationPanel = new JPanel();
		navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.PAGE_AXIS)); // JPanel
																						// default:
																						// FlowLayout

		/* Create the speed text panel */
		final JPanel speedTextPanel = new JPanel(); // JPanel default layout:
													// FlowLayout
		// Create the speed text field
		final TextField speedTextField = new TextField(Integer.toString(SPEED_INIT), 10);
		speedTextPanel.add(new JLabel("Speed"));
		speedTextPanel.add(speedTextField);

		/* Create the speed control panel */
		final JPanel speedPanel = new JPanel();
		speedPanel.setLayout(new GridLayout(1, 3));
		/* Create the speed slider */
		final JSlider speedSlider = new JSlider(JSlider.VERTICAL, SPEED_MIN, SPEED_MAX, SPEED_INIT);
		/* Labels */
		// speedSlider.setMajorTickSpacing( 10 );
		// speedSlider.setPaintTicks( true );
		// Hashtable labelTable = new Hashtable();
		// labelTable.put( new Integer( SPEED_MIN ), new JLabel( "Stop" ) );
		// labelTable.put( new Integer( SPEED_MAX/10 ), new JLabel( "Slow" ) );
		// labelTable.put( new Integer( SPEED_MAX/2 ), new JLabel( "Medium" ) );
		// labelTable.put( new Integer( SPEED_MAX ), new JLabel( "Fast" ) );
		// speedSlider.setLabelTable( labelTable );
		// speedSlider.setPaintLabels( true );
		/* Create the speed meter */
		final JProgressBar speedometer = new JProgressBar(JProgressBar.VERTICAL, SPEED_MIN, SPEED_MAX);
		/* Initialise the critical speed level warning image */
		final JLabel warning = new JLabel(new ImageIcon("img/warning.png"));
		warning.setVisible(false);
		/* Add components to panel */
		speedPanel.add(speedSlider);
		speedPanel.add(speedometer);
		speedPanel.add(warning);

		/* Create the angle text panel */
		final JPanel angleTextPanel = new JPanel(); // JPanel default layout:
													// FlowLayout
		// Create the angle text field
		final TextField angleTextField = new TextField(Integer.toString(ANGLE_INIT), 10);
		angleTextPanel.add(new JLabel("Angle"));
		angleTextPanel.add(angleTextField);

		/* Create the angle slider */
		final JSlider angleSlider = new JSlider(ANGLE_MIN, ANGLE_MAX, ANGLE_INIT); // Default:
																					// JSlider.HORIZONTAL
		/* Labels */
		// angleSlider.setMajorTickSpacing( 45 );
		// angleSlider.setPaintTicks( true );

		/* Speed text field behaviour */
		speedTextField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				speedSlider.setValue(Integer.parseInt(speedTextField.getText()));
			}
		});

		/* Speed slider behaviour */
		speedSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				speed = speedSlider.getValue();
				speedTextField.setText(Integer.toString(speed));
				speedometer.setValue(speed);
				warning.setVisible(speed > (SPEED_MAX * 80 / 100));
			}
		});

		/* Angle text field behaviour */
		angleTextField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				angle = Integer.parseInt(angleTextField.getText());
				angleSlider.setValue(angle);
			}
		});

		/* Angle slider behaviour */
		angleSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				angle = angleSlider.getValue();
				angleTextField.setText(Integer.toString(angle));
			}
		});

		/* Combine navigation sub-components */
		navigationPanel.add(speedTextPanel);
		navigationPanel.add(angleTextPanel);
		navigationPanel.add(speedPanel);
		navigationPanel.add(angleSlider);

		/* Add components to frame */
		frame.add(visualisationPanel, BorderLayout.CENTER);
		frame.add(navigationPanel, BorderLayout.EAST);

		/* Trigger the visualisation */
		ActionListener visualisationRefresh = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				visualisationPanel.repaint();
			}
		};
		new Timer(REFRESH_RATE, visualisationRefresh).start();

		/* Size and display the window */
		frame.setSize(600, 300);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

}
