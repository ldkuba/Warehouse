package com.rb34.warehouse_interface;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.rb34.general.Robot;
import com.rb34.general.RobotManager;
import com.rb34.general.interfaces.IRobot.Status;
import com.rb34.jobInput.Item;
import com.rb34.jobInput.Job;
import com.rb34.jobInput.Reader;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.FlowLayout;

public class StartScreen {

	private JFrame frame;
	private JTextField textField;
	private JTextField txtX;
	private JTextField txtY;
	private JTextField txtX2;
	private JTextField txtY2;
	private JTextField txtX3;
	private JTextField txtY3;
	private JTabbedPane tabbedPane;
	private JComboBox heading3;
	private JComboBox heading2;
	private JComboBox heading;
	private static int rb;
	private String pathName;
	JOptionPane opt;
	final static Logger log4j = Logger.getLogger(StartScreen.class);
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartScreen window = new StartScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public StartScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		opt = new JOptionPane();
		frame = new JFrame();
		ImageIcon img = new ImageIcon("res/icon.png");
		frame.setIconImage(img.getImage());
		frame.setTitle("Enter Launch Parameters");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblChooseCsvLocation = new JLabel("Choose CSV Location:");
		GridBagConstraints gbc_lblChooseCsvLocation = new GridBagConstraints();
		gbc_lblChooseCsvLocation.anchor = GridBagConstraints.WEST;
		gbc_lblChooseCsvLocation.insets = new Insets(0, 0, 5, 5);
		gbc_lblChooseCsvLocation.gridx = 2;
		gbc_lblChooseCsvLocation.gridy = 1;
		frame.getContentPane().add(lblChooseCsvLocation, gbc_lblChooseCsvLocation);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 2;
		frame.getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);
		textField.setEditable(false);
		
		JButton btnOpen = new JButton("Open ");
		GridBagConstraints gbc_btnOpen = new GridBagConstraints();
		gbc_btnOpen.insets = new Insets(0, 0, 5, 5);
		gbc_btnOpen.gridx = 3;
		gbc_btnOpen.gridy = 2;
		frame.getContentPane().add(btnOpen, gbc_btnOpen);
		
		btnOpen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser("");
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.setDialogTitle("Choose Folder Containing Job Info:");
				int val = fc.showOpenDialog((Component)arg0.getSource());
				if (val == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					pathName = file.getAbsolutePath();
					textField.setText(pathName);
					
				}
				
			}
		});
		
		JLabel lblNumberOfRobots = new JLabel("Number of Robots: ");
		GridBagConstraints gbc_lblNumberOfRobots = new GridBagConstraints();
		gbc_lblNumberOfRobots.anchor = GridBagConstraints.WEST;
		gbc_lblNumberOfRobots.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumberOfRobots.gridx = 2;
		gbc_lblNumberOfRobots.gridy = 3;
		frame.getContentPane().add(lblNumberOfRobots, gbc_lblNumberOfRobots);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Select Robot", "1", "2", "3"}));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 3;
		gbc_comboBox.gridy = 3;
		frame.getContentPane().add(comboBox, gbc_comboBox);
		
		JLabel lblOperatingMode = new JLabel("Operating Mode:");
		GridBagConstraints gbc_lblOperatingMode = new GridBagConstraints();
		gbc_lblOperatingMode.anchor = GridBagConstraints.WEST;
		gbc_lblOperatingMode.insets = new Insets(0, 0, 5, 5);
		gbc_lblOperatingMode.gridx = 2;
		gbc_lblOperatingMode.gridy = 4;
		frame.getContentPane().add(lblOperatingMode, gbc_lblOperatingMode);
		
		JComboBox cmbMode = new JComboBox();
		cmbMode.setModel(new DefaultComboBoxModel(new String[] {"Select Mode", "Localisation", "Manual"}));
		GridBagConstraints gbc_cmbMode = new GridBagConstraints();
		gbc_cmbMode.insets = new Insets(0, 0, 5, 5);
		gbc_cmbMode.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbMode.gridx = 3;
		gbc_cmbMode.gridy = 4;
		frame.getContentPane().add(cmbMode, gbc_cmbMode);
		
		cmbMode.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// ADD CODE TO RUN WAREHOUSE MAP
				
			}
		});
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.gridwidth = 2;
		gbc_tabbedPane.insets = new Insets(5, 5, 5, 5);
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 2;
		gbc_tabbedPane.gridy = 5;
		frame.getContentPane().add(tabbedPane, gbc_tabbedPane);
		
		
		JButton btnSubmit = new JButton("Submit");
		GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
		gbc_btnSubmit.anchor = GridBagConstraints.SOUTH;
		gbc_btnSubmit.insets = new Insets(5, 5, 5, 5);
		gbc_btnSubmit.gridx = 4;
		gbc_btnSubmit.gridy = 5;
		frame.getContentPane().add(btnSubmit, gbc_btnSubmit);
		
		btnSubmit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String type = (String)comboBox.getSelectedItem();
				int val = 0;
				if(!type.equals("Select Robot")) {
					val = Integer.parseInt(type);
				}
				
				RobotManager manager = new RobotManager();
				for(int i = 0; i < val; i++) {
					Robot robot = new Robot();
					manager.addRobot(robot);
				}
				
				String type2 = (String)cmbMode.getSelectedItem();
				boolean shouldRun = true;
				int xpos,ypos,xpos2,ypos2,xpos3,ypos3;
				if(type2.equals("Manual")) {
					if(val == 1) {
						xpos = Integer.parseInt(txtX.getText());
						ypos = Integer.parseInt(txtY.getText());
						
						if(xpos <= 11 && xpos >= 0 && ypos <= 7 && ypos >= 0) {
							manager.getRobot(0).setXLoc(xpos); manager.getRobot(0).setYLoc(ypos);
						} else {
							shouldRun = false;
							opt.showMessageDialog(frame, "Error: Coordinates are out of Range! Max X = 11 and Max Y = 7");
						}
						
						String head = (String)heading.getSelectedItem();
						
						if(head.equals("N") || head.equals("E") || head.equals("S") || head.equals("W")) {
							manager.getRobot(0).setHeading(head);
						} else {
							shouldRun = false;
							opt.showMessageDialog(frame, "Error: Please Select Heading For Robot 1");
						}
					} else if (val == 2) {
						xpos = Integer.parseInt(txtX.getText());
						ypos = Integer.parseInt(txtY.getText());
						if(xpos <= 11 && xpos >= 0 && ypos <= 7 && ypos >= 0) {
							manager.getRobot(0).setXLoc(xpos); manager.getRobot(0).setYLoc(ypos);
						} else {
							shouldRun = false;
							opt.showMessageDialog(frame, "Error: Coordinates are out of Range! Max X = 11 and Max Y = 7");
						}
						
						String head = (String)heading.getSelectedItem();
						
						if(head.equals("N") || head.equals("E") || head.equals("S") || head.equals("W")) {
							manager.getRobot(0).setHeading(head);
						} else {
							shouldRun = false;
							opt.showMessageDialog(frame, "Error: Please Select Heading For Robot 1");
						}
						
						xpos2 = Integer.parseInt(txtX2.getText());
						ypos2 = Integer.parseInt(txtY2.getText());
						if(xpos2 <= 11 && xpos2 >= 0 && ypos2 <= 7 && ypos2 >= 0) {
							manager.getRobot(1).setXLoc(xpos2); manager.getRobot(1).setYLoc(ypos2);
						} else {
							shouldRun = false;
							opt.showMessageDialog(frame, "Error: Coordinates are out of Range! Max X = 11 and Max Y = 7");
						}
						
						String head2 = (String)heading2.getSelectedItem();
						
						if(head2.equals("N") || head2.equals("E") || head2.equals("S") || head2.equals("W")) {
							manager.getRobot(1).setHeading(head2);
						} else {
							shouldRun = false;
							opt.showMessageDialog(frame, "Error: Please Select Heading For Robot 2");
						}
					} else if (val == 3) {
						xpos = Integer.parseInt(txtX.getText());
						ypos = Integer.parseInt(txtY.getText());
						if(xpos <= 11 && xpos >= 0 && ypos <= 7 && ypos >= 0) {
							manager.getRobot(0).setXLoc(xpos); manager.getRobot(0).setYLoc(ypos); 
						} else {
							shouldRun = false;
							opt.showMessageDialog(frame, "Error: Coordinates are out of Range! Max X = 11 and Max Y = 7");
						}
						
						String head = (String)heading.getSelectedItem();
						
						if(head.equals("N") || head.equals("E") || head.equals("S") || head.equals("W")) {
						manager.getRobot(0).setHeading(head);
						} else {
							shouldRun = false;
							opt.showMessageDialog(frame, "Error: Please Select Heading For Robot 1");
						}
						
						xpos2 = Integer.parseInt(txtX2.getText());
						ypos2 = Integer.parseInt(txtY2.getText());
						if(xpos2 <= 11 && xpos2 >= 0 && ypos2 <= 7 && ypos2 >= 0) {
							manager.getRobot(1).setXLoc(xpos2); manager.getRobot(1).setYLoc(ypos2);
						} else {
							shouldRun = false;
							opt.showMessageDialog(frame, "Error: Coordinates are out of Range! Max X = 11 and Max Y = 7");
						}
						
						String head2 = (String)heading2.getSelectedItem();
						
						if(head2.equals("N") || head2.equals("E") || head2.equals("S") || head2.equals("W")) {
						manager.getRobot(1).setHeading(head2);
						} else {
							shouldRun = false;
							opt.showMessageDialog(frame, "Error: Please Select Heading For Robot 2");
						}
						
						xpos3 = Integer.parseInt(txtX3.getText());
						ypos3 = Integer.parseInt(txtY3.getText());
						if(xpos3 <= 11 && xpos3 >= 0 && ypos3 <= 7 && ypos3 >= 0) {
							manager.getRobot(2).setXLoc(xpos3); manager.getRobot(2).setYLoc(ypos3);
						} else {
							shouldRun = false;
							opt.showMessageDialog(frame, "Error: Coordinates are out of Range! Max X = 11 and Max Y = 7");
						}
						
						String head3 = (String)heading3.getSelectedItem();
						
						if(head3.equals("N") || head3.equals("E") || head3.equals("S") || head3.equals("W")) {
							manager.getRobot(2).setHeading(head3);
						} else {
							shouldRun = false;
							//JOptionPane opt = new JOptionPane();
							opt.showMessageDialog(frame, "Error: Please Select Heading For Robot 3");
						}
					}
					
					Reader.setFilePath(pathName);
					// ADD CODE FOR RUNNING JOBS
					
					if(shouldRun == true) {
					MainWindow window = new MainWindow(manager,val);
					window.launch();
					frame.dispose();
					}
					
				} else if (type2.equals("Localisation")) {
					log4j.info("Implement Localisation");
					//ADD LOCALISATION CODE
					Reader.setFilePath(pathName);
					log4j.info("Set Reader Path to: " + pathName);
				} else if(type2.equals("Select Mode")) {
					JOptionPane opt = new JOptionPane();
					opt.showMessageDialog(frame, "Error: Please Select Robot Operating Mode AND Number of Robots");
				}
				
			}
		});
		

		
		JPanel rb1 = new JPanel();
		rb1.setLayout(null);

		JLabel lblStartingPosition = new JLabel("Starting Position: ");
		lblStartingPosition.setBounds(12, 27, 134, 15);
		rb1.add(lblStartingPosition);
		
		txtX = new JTextField();
		txtX.setText("X");
		txtX.setBounds(144, 25, 45, 19);
		rb1.add(txtX);
		txtX.setColumns(10);
		
		txtY = new JTextField();
		txtY.setText("Y");
		txtY.setBounds(201, 25, 45, 19);
		rb1.add(txtY);
		txtY.setColumns(10);
		
		JLabel lblRobotHeading = new JLabel("Robot Heading: ");
		lblRobotHeading.setBounds(12, 54, 134, 15);
		rb1.add(lblRobotHeading);
		
		heading = new JComboBox();
		heading.setModel(new DefaultComboBoxModel(new String[] {"Heading", "N", "E", "S", "W"}));
		heading.setBounds(144, 49, 92, 24);
		rb1.add(heading);
		
		
		//Robot 2 Panel
		JPanel rb2 = new JPanel();
		rb2.setLayout(null);
		
		JLabel lblStartingPosition2 = new JLabel("Starting Position: ");
		lblStartingPosition2.setBounds(12, 27, 134, 15);
		rb2.add(lblStartingPosition2);
		
		txtX2 = new JTextField();
		txtX2.setText("X");
		txtX2.setBounds(144, 25, 45, 19);
		rb2.add(txtX2);
		txtX2.setColumns(10);
		
		txtY2 = new JTextField();
		txtY2.setText("Y");
		txtY2.setBounds(201, 25, 45, 19);
		rb2.add(txtY2);
		txtY2.setColumns(10);
		
		JLabel lblRobotHeading2 = new JLabel("Robot Heading: ");
		lblRobotHeading2.setBounds(12, 54, 134, 15);
		rb2.add(lblRobotHeading2);
		
		heading2 = new JComboBox();
		heading2.setModel(new DefaultComboBoxModel(new String[] {"Heading", "N", "E", "S", "W"}));
		heading2.setBounds(144, 49, 92, 24);
		rb2.add(heading2);
		
		//Robot 3 Panel
		JPanel rb3 = new JPanel();
		rb3.setLayout(null);
		
		JLabel lblStartingPosition3 = new JLabel("Starting Position: ");
		lblStartingPosition3.setBounds(12, 27, 134, 15);
		rb3.add(lblStartingPosition3);
		
		txtX3 = new JTextField();
		txtX3.setText("X");
		txtX3.setBounds(144, 25, 45, 19);
		rb3.add(txtX3);
		txtX3.setColumns(10);
		
		txtY3 = new JTextField();
		txtY3.setText("Y");
		txtY3.setBounds(201, 25, 45, 19);
		rb3.add(txtY3);
		txtY3.setColumns(10);
		
		JLabel lblRobotHeading3 = new JLabel("Robot Heading: ");
		lblRobotHeading3.setBounds(12, 54, 134, 15);
		rb3.add(lblRobotHeading3);
		
		heading3 = new JComboBox();
		heading3.setModel(new DefaultComboBoxModel(new String[] {"Heading", "N", "E", "S", "W"}));
		heading3.setBounds(144, 49, 92, 24);
		rb3.add(heading3);


		//tabbedPane.removeTabAt(2);
		
		comboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String type = (String)comboBox.getSelectedItem();
				int val = Integer.parseInt(type);
				tabbedPane.removeAll();
				tabbedPane.addTab("Robot 1", rb1);
				tabbedPane.addTab("Robot 2", rb2);
				tabbedPane.addTab("Robot 3", rb3);
				
				if(val == 2) {
					tabbedPane.removeTabAt(2);
				} else if (val ==1) {
					tabbedPane.removeTabAt(2);
					tabbedPane.removeTabAt(1);
				}

			}
		});
		
	}
	
}