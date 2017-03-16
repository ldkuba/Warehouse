package com.rb34.warehouse_interface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rb34.general.Robot;
import com.rb34.general.RobotManager;
import com.rb34.general.interfaces.IRobot.Status;
import com.rb34.jobInput.Item;

import rp.robotics.visualisation.KillMeNow;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;


public class WarehouseWindow {

	private JFrame frame;
	static RobotManager manager;
	Robot rb1, rb2, rb3;
	JLabel[] mapLabels;
	Container cp;
	JLabel x0y8,x0y7,x0y6,x0y5,x0y4,x0y3,x0y2,x0y1,x0y0;
	JLabel x1y8,x1y7,x1y6,x1y5,x1y4,x1y3,x1y2,x1y1,x1y0;
	JLabel x2y8,x2y7,x2y6,x2y5,x2y4,x2y3,x2y2,x2y1,x2y0;
	JLabel x3y8,x3y7,x3y6,x3y5,x3y4,x3y3,x3y2,x3y1,x3y0;
	JLabel x4y8,x4y7,x4y6,x4y5,x4y4,x4y3,x4y2,x4y1,x4y0;
	JLabel x5y8,x5y7,x5y6,x5y5,x5y4,x5y3,x5y2,x5y1,x5y0;
	JLabel x6y8,x6y7,x6y6,x6y5,x6y4,x6y3,x6y2,x6y1,x6y0;
	JLabel x7y8,x7y7,x7y6,x7y5,x7y4,x7y3,x7y2,x7y1,x7y0;
	JLabel x8y8,x8y7,x8y6,x8y5,x8y4,x8y3,x8y2,x8y1,x8y0;
	JLabel x9y8,x9y7,x9y6,x9y5,x9y4,x9y3,x9y2,x9y1,x9y0;
	JLabel x10y8,x10y7,x10y6,x10y5,x10y4,x10y3,x10y2,x10y1,x10y0;
	JLabel x11y8,x11y7,x11y6,x11y5,x11y4,x11y3,x11y2,x11y1,x11y0;
	JLabel x12y8,x12y7,x12y6,x12y5,x12y4,x12y3,x12y2,x12y1,x12y0;
	JLabel[][] label;
	private static final Logger log4j = LogManager.getLogger(WarehouseWindow.class.getName());

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WarehouseWindow window = new WarehouseWindow();
					window.frame.setVisible(true);
					log4j.trace("Created Warehouse Window");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WarehouseWindow() {
		initialize();
	}

	/**
	 * Initialise the contents of the frame.
	 */
	private void initialize() {
		manager = new RobotManager();
		rb1 = new Robot();
		rb2 = new Robot();
		rb3 = new Robot();
		manager.addRobot(rb1);
		manager.addRobot(rb2);
		manager.addRobot(rb3);
		
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Dialog", Font.PLAIN, 20));
		frame.setResizable(false);
		frame.setBounds(100, 100, 650, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JButton btnLoadJobList = new JButton("Load Job List");
		GridBagConstraints gbc_btnLoadJobList = new GridBagConstraints();
		gbc_btnLoadJobList.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnLoadJobList.anchor = GridBagConstraints.NORTH;
		gbc_btnLoadJobList.insets = new Insets(0, 0, 5, 5);
		gbc_btnLoadJobList.gridx = 0;
		gbc_btnLoadJobList.gridy = 0;
		frame.getContentPane().add(btnLoadJobList, gbc_btnLoadJobList);
		
		
		// Preparation for selecting job list file at runtime
		btnLoadJobList.addActionListener(new ActionListener() {
			
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			JFileChooser fc = new JFileChooser();
			int val = fc.showOpenDialog((Component)arg0.getSource());
			if (val == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				String pathName = file.getAbsolutePath();
				//Should Pass 'pathName' string to the relevant class after integration
				System.out.println(pathName);
			}
				
		}
		});
		
		JButton btnWarehouseMap = new JButton("Warehouse Map");
		GridBagConstraints gbc_btnWarehouseMap = new GridBagConstraints();
		gbc_btnWarehouseMap.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnWarehouseMap.anchor = GridBagConstraints.NORTH;
		gbc_btnWarehouseMap.insets = new Insets(0, 0, 5, 5);
		gbc_btnWarehouseMap.gridx = 2;
		gbc_btnWarehouseMap.gridy = 0;
		frame.getContentPane().add(btnWarehouseMap, gbc_btnWarehouseMap);
		
		btnWarehouseMap.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					JFrame frame2 = new JFrame("Warehouse Map");				
					WarehouseMap(frame2);
					frame2.getContentPane();
					frame.addWindowListener(new KillMeNow());
					frame2.pack();
					frame2.setSize(315,300);
					frame2.setLocationRelativeTo(frame);
					frame2.setLocation(100, 450);
					frame2.setResizable(false);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame2.setVisible(true);
					
					
				/**
				 * 	Polls Robot Position on set interval and updates the position on the map
				 *	Secondary Timer inside which resets the grid position to unoccupied
				 *	as the robot moves forward. 
				 */

					int delay = 2000;
						ActionListener updatePos = new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								int X1 = manager.getRobot(0).getXLoc();
								int Y1 = manager.getRobot(0).getYLoc();
								int destX1 = 11;//manager.getRobot(0).getDestXLoc();
								int destY1 = 7;//manager.getRobot(0).getDestYLoc();
								int prevX1 = X1;
								int prevY1 = Y1;
								setRobotPos(1,label[Y1][X1]);
								setDestination(1, label[destY1][destX1]);
								//Clear previous node if robot is moving
								int delay = 2000;
								if(manager.getRobot(0).getRobotStatus() == Status.RUNNING){
								
								ActionListener clearBoard = new ActionListener() {
									
									@Override
									public void actionPerformed(ActionEvent e) {
										clearPos(label[prevY1][prevX1]);
									}
								};
								new Timer(delay,clearBoard).start();
								}
								
								//Manually setting position to avoid overlap while testing
								int X2 = 11;//manager.getRobot(1).getXLoc();
								int Y2 = 0;//manager.getRobot(1).getYLoc();
								int destX2 = 3;
								int destY2 = 5;
								int prevX2 = X2;
								int prevY2 = Y2;
								setRobotPos(2,label[Y2][X2]);
								setDestination(2, label[destY2][destX2]);
								
								if(manager.getRobot(1).getRobotStatus() == Status.RUNNING){
									
									ActionListener clearBoard = new ActionListener() {
										
										@Override
										public void actionPerformed(ActionEvent e) {
											clearPos(label[prevY2][prevX2]);
										}
									};
									new Timer(delay,clearBoard).start();
								}
								
								//Manually setting postion to avoid overlap while testing
								int X3 = 1;//manager.getRobot(2).getXLoc();
								int Y3 = 7;//manager.getRobot(2).getYLoc();
								int destX3 = 9;
								int destY3 = 4;
								int prevX3 = X3;
								int prevY3 = Y3;
								setRobotPos(3,label[Y3][X3]);
								setDestination(3, label[destY3][destX3]);
								
								if(manager.getRobot(2).getRobotStatus() == Status.RUNNING){
									
									ActionListener clearBoard = new ActionListener() {
										
										@Override
										public void actionPerformed(ActionEvent e) {
											clearPos(label[prevY3][prevX3]);
										}
									};
									new Timer(delay,clearBoard).start();
								}
								
							}
						};
						new Timer(delay,updatePos).start();
					
					
				}
		});
		
			
		JButton btnRewardSummary = new JButton("Reward Summary");
		GridBagConstraints gbc_btnRewardSummary = new GridBagConstraints();
		gbc_btnRewardSummary.insets = new Insets(0, 0, 5, 5);
		gbc_btnRewardSummary.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRewardSummary.gridx = 4;
		gbc_btnRewardSummary.gridy = 0;
		frame.getContentPane().add(btnRewardSummary, gbc_btnRewardSummary);
		
		
		//Preparation for Getting Job Reward History
		btnRewardSummary.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		JLabel lblRobot = new JLabel("Robot 1: ");
		lblRobot.setFont(new Font("Dialog", Font.BOLD, 20));
		GridBagConstraints gbc_lblRobot = new GridBagConstraints();
		gbc_lblRobot.anchor = GridBagConstraints.WEST;
		gbc_lblRobot.insets = new Insets(0, 0, 5, 5);
		gbc_lblRobot.gridx = 0;
		gbc_lblRobot.gridy = 1;
		frame.getContentPane().add(lblRobot, gbc_lblRobot);
		
		lblRobot.addMouseListener(new MouseAdapter() {
			
			//Contains Robot 1 Specific Job Breakdown
			public void mouseClicked(MouseEvent e) {
				JFrame frame3 = new JFrame();
				frame3.getContentPane();
				frame3.setLocationRelativeTo(frame);
				frame.addWindowListener(new KillMeNow());
				frame3.pack();
				frame3.setLocation(760, 100);
				frame3.setSize(300, 300);
				frame3.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
		
		JLabel lblStatus = new JLabel("ACQUIRING STATUS");
		lblStatus.setFont(new Font("Dialog", Font.BOLD, 15));
		GridBagConstraints gbc_lblStatus = new GridBagConstraints();
		gbc_lblStatus.anchor = GridBagConstraints.WEST;
		gbc_lblStatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblStatus.gridx = 2;
		gbc_lblStatus.gridy = 1;
		frame.getContentPane().add(lblStatus, gbc_lblStatus);
		
		JLabel lblRobot3 = new JLabel("Robot 3:");
		lblRobot3.setFont(new Font("Dialog", Font.BOLD, 20));
		GridBagConstraints gbc_lblRobot3 = new GridBagConstraints();
		gbc_lblRobot3.anchor = GridBagConstraints.WEST;
		gbc_lblRobot3.insets = new Insets(0, 0, 5, 5);
		gbc_lblRobot3.gridx = 4;
		gbc_lblRobot3.gridy = 1;
		frame.getContentPane().add(lblRobot3, gbc_lblRobot3);
		
		JLabel lblStatus3 = new JLabel("ACQUIRING STATUS");
		lblStatus3.setFont(new Font("Dialog", Font.BOLD, 15));
		GridBagConstraints gbc_lblStatus3 = new GridBagConstraints();
		gbc_lblStatus3.anchor = GridBagConstraints.WEST;
		gbc_lblStatus3.insets = new Insets(0, 0, 5, 5);
		gbc_lblStatus3.gridx = 5;
		gbc_lblStatus3.gridy = 1;
		frame.getContentPane().add(lblStatus3, gbc_lblStatus3);
		
		JLabel lblRobotLocation = new JLabel("Robot Location: ");
		GridBagConstraints gbc_lblRobotLocation = new GridBagConstraints();
		gbc_lblRobotLocation.anchor = GridBagConstraints.WEST;
		gbc_lblRobotLocation.insets = new Insets(0, 0, 5, 5);
		gbc_lblRobotLocation.gridx = 0;
		gbc_lblRobotLocation.gridy = 2;
		frame.getContentPane().add(lblRobotLocation, gbc_lblRobotLocation);
		
		JLabel lblCoords = new JLabel("coords");
		GridBagConstraints gbc_lblCoords = new GridBagConstraints();
		gbc_lblCoords.anchor = GridBagConstraints.WEST;
		gbc_lblCoords.insets = new Insets(0, 0, 5, 5);
		gbc_lblCoords.gridx = 2;
		gbc_lblCoords.gridy = 2;
		frame.getContentPane().add(lblCoords, gbc_lblCoords);
		
		JLabel lblRobotLocation3 = new JLabel("Robot Location:");
		GridBagConstraints gbc_lblRobotLocation3 = new GridBagConstraints();
		gbc_lblRobotLocation3.anchor = GridBagConstraints.WEST;
		gbc_lblRobotLocation3.insets = new Insets(0, 0, 5, 5);
		gbc_lblRobotLocation3.gridx = 4;
		gbc_lblRobotLocation3.gridy = 2;
		frame.getContentPane().add(lblRobotLocation3, gbc_lblRobotLocation3);
		
		JLabel lblCoords3 = new JLabel("coords3");
		GridBagConstraints gbc_lblCoords3 = new GridBagConstraints();
		gbc_lblCoords3.anchor = GridBagConstraints.WEST;
		gbc_lblCoords3.insets = new Insets(0, 0, 5, 5);
		gbc_lblCoords3.gridx = 5;
		gbc_lblCoords3.gridy = 2;
		frame.getContentPane().add(lblCoords3, gbc_lblCoords3);
		
		JLabel lblCurrentJob = new JLabel("Current Job: ");
		GridBagConstraints gbc_lblCurrentJob = new GridBagConstraints();
		gbc_lblCurrentJob.anchor = GridBagConstraints.WEST;
		gbc_lblCurrentJob.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurrentJob.gridx = 0;
		gbc_lblCurrentJob.gridy = 3;
		frame.getContentPane().add(lblCurrentJob, gbc_lblCurrentJob);
		
		JLabel lblJobid = new JLabel("jobID");
		GridBagConstraints gbc_lblJobid = new GridBagConstraints();
		gbc_lblJobid.anchor = GridBagConstraints.WEST;
		gbc_lblJobid.insets = new Insets(0, 0, 5, 5);
		gbc_lblJobid.gridx = 2;
		gbc_lblJobid.gridy = 3;
		frame.getContentPane().add(lblJobid, gbc_lblJobid);
		
		JLabel lblCurrentJob3 = new JLabel("Current Job:");
		GridBagConstraints gbc_lblCurrentJob3 = new GridBagConstraints();
		gbc_lblCurrentJob3.anchor = GridBagConstraints.WEST;
		gbc_lblCurrentJob3.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurrentJob3.gridx = 4;
		gbc_lblCurrentJob3.gridy = 3;
		frame.getContentPane().add(lblCurrentJob3, gbc_lblCurrentJob3);
		
		JLabel lblJobid3 = new JLabel("jobId3");
		GridBagConstraints gbc_lblJobid3 = new GridBagConstraints();
		gbc_lblJobid3.anchor = GridBagConstraints.WEST;
		gbc_lblJobid3.insets = new Insets(0, 0, 5, 5);
		gbc_lblJobid3.gridx = 5;
		gbc_lblJobid3.gridy = 3;
		frame.getContentPane().add(lblJobid3, gbc_lblJobid3);
		
		JLabel lblPotentialReward = new JLabel("Potential Reward:");
		GridBagConstraints gbc_lblPotentialReward = new GridBagConstraints();
		gbc_lblPotentialReward.anchor = GridBagConstraints.WEST;
		gbc_lblPotentialReward.insets = new Insets(0, 0, 5, 5);
		gbc_lblPotentialReward.gridx = 0;
		gbc_lblPotentialReward.gridy = 4;
		frame.getContentPane().add(lblPotentialReward, gbc_lblPotentialReward);
		
		JLabel lblReward = new JLabel("reward1");
		GridBagConstraints gbc_lblReward = new GridBagConstraints();
		gbc_lblReward.anchor = GridBagConstraints.WEST;
		gbc_lblReward.insets = new Insets(0, 0, 5, 5);
		gbc_lblReward.gridx = 2;
		gbc_lblReward.gridy = 4;
		frame.getContentPane().add(lblReward, gbc_lblReward);
		
		JLabel lblPotentialReward3 = new JLabel("Potential Reward:");
		GridBagConstraints gbc_lblPotentialReward3 = new GridBagConstraints();
		gbc_lblPotentialReward3.anchor = GridBagConstraints.WEST;
		gbc_lblPotentialReward3.insets = new Insets(0, 0, 5, 5);
		gbc_lblPotentialReward3.gridx = 4;
		gbc_lblPotentialReward3.gridy = 4;
		frame.getContentPane().add(lblPotentialReward3, gbc_lblPotentialReward3);
		
		JLabel lblReward3 = new JLabel("reward3");
		GridBagConstraints gbc_lblReward3 = new GridBagConstraints();
		gbc_lblReward3.anchor = GridBagConstraints.WEST;
		gbc_lblReward3.insets = new Insets(0, 0, 5, 5);
		gbc_lblReward3.gridx = 5;
		gbc_lblReward3.gridy = 4;
		frame.getContentPane().add(lblReward3, gbc_lblReward3);
		
		JLabel lblRobot2 = new JLabel("Robot 2:");
		lblRobot2.setFont(new Font("Dialog", Font.BOLD, 20));
		GridBagConstraints gbc_lblRobot2 = new GridBagConstraints();
		gbc_lblRobot2.anchor = GridBagConstraints.WEST;
		gbc_lblRobot2.insets = new Insets(0, 0, 5, 5);
		gbc_lblRobot2.gridx = 0;
		gbc_lblRobot2.gridy = 6;
		frame.getContentPane().add(lblRobot2, gbc_lblRobot2);
		
		JLabel lblStatus2 = new JLabel("ACQUIRING STATUS");
		lblStatus2.setFont(new Font("Dialog", Font.BOLD, 15));
		GridBagConstraints gbc_lblStatus2 = new GridBagConstraints();
		gbc_lblStatus2.anchor = GridBagConstraints.WEST;
		gbc_lblStatus2.insets = new Insets(0, 0, 5, 5);
		gbc_lblStatus2.gridx = 2;
		gbc_lblStatus2.gridy = 6;
		frame.getContentPane().add(lblStatus2, gbc_lblStatus2);
		
		JLabel lblRobotLocation2 = new JLabel("Robot Location:");
		GridBagConstraints gbc_lblRobotLocation2 = new GridBagConstraints();
		gbc_lblRobotLocation2.anchor = GridBagConstraints.WEST;
		gbc_lblRobotLocation2.insets = new Insets(0, 0, 5, 5);
		gbc_lblRobotLocation2.gridx = 0;
		gbc_lblRobotLocation2.gridy = 7;
		frame.getContentPane().add(lblRobotLocation2, gbc_lblRobotLocation2);
		
		JLabel lblCoords2 = new JLabel("coords2");
		GridBagConstraints gbc_lblCoords2 = new GridBagConstraints();
		gbc_lblCoords2.anchor = GridBagConstraints.WEST;
		gbc_lblCoords2.insets = new Insets(0, 0, 5, 5);
		gbc_lblCoords2.gridx = 2;
		gbc_lblCoords2.gridy = 7;
		frame.getContentPane().add(lblCoords2, gbc_lblCoords2);
		
		JLabel lblCurrentJob2 = new JLabel("Current Job:");
		GridBagConstraints gbc_lblCurrentJob2 = new GridBagConstraints();
		gbc_lblCurrentJob2.anchor = GridBagConstraints.WEST;
		gbc_lblCurrentJob2.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurrentJob2.gridx = 0;
		gbc_lblCurrentJob2.gridy = 8;
		frame.getContentPane().add(lblCurrentJob2, gbc_lblCurrentJob2);
		
		JLabel lblJobid2 = new JLabel("jobId2");
		GridBagConstraints gbc_lblJobid2 = new GridBagConstraints();
		gbc_lblJobid2.anchor = GridBagConstraints.WEST;
		gbc_lblJobid2.insets = new Insets(0, 0, 5, 5);
		gbc_lblJobid2.gridx = 2;
		gbc_lblJobid2.gridy = 8;
		frame.getContentPane().add(lblJobid2, gbc_lblJobid2);
		
		JLabel lblPotentialReward2 = new JLabel("Potential Reward:");
		GridBagConstraints gbc_lblPotentialReward2 = new GridBagConstraints();
		gbc_lblPotentialReward2.anchor = GridBagConstraints.WEST;
		gbc_lblPotentialReward2.insets = new Insets(0, 0, 0, 5);
		gbc_lblPotentialReward2.gridx = 0;
		gbc_lblPotentialReward2.gridy = 10;
		frame.getContentPane().add(lblPotentialReward2, gbc_lblPotentialReward2);
		
		JLabel lblReward2 = new JLabel("reward2");
		GridBagConstraints gbc_lblReward2 = new GridBagConstraints();
		gbc_lblReward2.anchor = GridBagConstraints.WEST;
		gbc_lblReward2.insets = new Insets(0, 0, 0, 5);
		gbc_lblReward2.gridx = 2;
		gbc_lblReward2.gridy = 10;
		frame.getContentPane().add(lblReward2, gbc_lblReward2);
		
		
		// Polls relevant classes for required information on a set interval
		// and updates the corresponding UI elements
		int delay = 2000; //milliseconds
	      ActionListener taskPerformer = new ActionListener() {
	          public void actionPerformed(ActionEvent evt) {

	        	  setStatus(lblStatus,0);
	              setStatus(lblStatus2, 1);
	              setStatus(lblStatus3,2);
	             
	              setCoords(lblCoords,0);
	              setCoords(lblCoords2,1);
	              setCoords(lblCoords3,2);
	            
	              
	              /*
	              Commented out as it throws exception due to no data being returned
	              setJob(lblJobid, 0);
	              setJob(lblJobid2,1);
	              setJob(lblJobid3, 2);
	              
	              	              
	              setReward(lblReward,0);
	              setReward(lblReward2, 1);
	              setReward(lblReward3, 2);
	              */
	          }
	      };
	      new Timer(delay, taskPerformer).start();
	}
	
	// Moved all UI updating functions to separate methods in an attempt to clean up
	// code inside the gui properties class and event listeners
	public void setStatus(JLabel val, int robotId) {
		String status = manager.getRobot(robotId).getRobotStatus().toString(); 
		int rb = robotId +1;
        if(status.equals("IDLE")) {
        		val.setText(status);
        		val.setForeground(Color.BLUE);
        		log4j.trace("Updated Robot " + rb + " Status: " + status);
        }	else if(status.equals("RUNNING")) {
      	  		val.setText(status);
        		val.setForeground(Color.GREEN);
        		log4j.trace("Updated Robot " + rb + " Status: " + status);
        }else if(status.equals("STUCK")) {
      	  		val.setText(status);
        		val.setForeground(Color.RED);
        		log4j.trace("Updated Robot " + rb + " Status: " + status);
        } else if(status.equals("AT_ITEM") || status.equals("AT_DROPOFF")){
      	  		val.setText(status);
        		val.setForeground(Color.ORANGE);
        		log4j.trace("Updated Robot " + rb + " Status: " + status);
        } else {
        	log4j.error("The obtained robot status does not match the valid status types");
        	throw new IllegalArgumentException("Invalid Status Recieved");
        }
        
		
		
	}
	
	public void setCoords(JLabel val, int robotId) {
        String location = "("+ manager.getRobot(robotId).getXLoc() + "," + manager.getRobot(0).getYLoc() + ")";
        val.setText(location);
        int rb = robotId +1;
        log4j.trace("Updated Robot " + rb +  " Position: " + location);
	}
	
	public void setJob(JLabel val, int robotId) {
        String jobID = manager.getRobot(robotId).getCurrentJob().getJobId();
        if(manager.getRobot(robotId).getCurrentJob().getJobId() == null) {
        	jobID = "No Current Job";
        } else { 
        	jobID = manager.getRobot(robotId).getCurrentJob().getJobId();
        }
        	
        val.setText(jobID);
        int rb = robotId +1;
        
        log4j.trace("Updated Robot " + rb + "'s Current Job");
	}
	
	public void setDrop(JLabel val, int robotId) {
		String location = "("+ manager.getRobot(robotId).getXDropLoc() + "," + manager.getRobot(0).getYDropLoc() + ")";
		val.setText(location);
		int rb = robotId+1;
		log4j.trace("Updated Robot " + rb +  " Drop Location: " + location);
		
	}
	
	public void setReward(JLabel val, int robotId) {
		ArrayList<Item> items = manager.getRobot(robotId).getItemsToPick();
		float reward = 0;
		
		for (Item item : items) {
			reward = reward + item.getReward();
		}
		val.setText(" " + reward);
		int rb = robotId+1;
		log4j.trace("Updated Robot " + rb +  "'s Potential Reward: " + reward);
	}
	
	public void WarehouseMap(JFrame val) {
		
		 cp = val.getContentPane();
		 cp.setLayout(null);
	     ImageIcon space = new ImageIcon("res/grid.png");
	     ImageIcon obs = new ImageIcon("res/obs.png");

				
		x0y7 = new JLabel();
		lblProp(x0y7,space,0, 35);
		
		x0y6 = new JLabel();
		lblProp(x0y6,space,0, 60);
		
		x0y5 = new JLabel();
		lblProp(x0y5,space,0,85);

		x0y4 = new JLabel();
		lblProp(x0y4,space,0,110);
		
		x0y3 = new JLabel();
		lblProp(x0y3,space,0,135);
		
		x0y2 = new JLabel();
		lblProp(x0y2,space,0,160);
		
		x0y1 = new JLabel();
		lblProp(x0y1,space,0,185);
		
		x0y0 = new JLabel();
		lblProp(x0y0,space,0,210);
		
		
		x1y7 = new JLabel();
		lblProp(x1y7,space,25,35);
		
		x1y6 = new JLabel();
		lblProp(x1y6,space,25,60);
		
		x1y5 = new JLabel();
		lblProp(x1y5,obs,25,85);
		
		x1y4 = new JLabel();
		lblProp(x1y4,obs,25,110);
		
		x1y3 = new JLabel();
		lblProp(x1y3,obs,25,135);
		
		x1y2 = new JLabel();
		lblProp(x1y2,obs,25,160);
		
		x1y1 = new JLabel();
		lblProp(x1y1,obs,25,185);
		
		x1y0 = new JLabel();
		lblProp(x1y0,space,25,210);
		
		
		x2y7 = new JLabel();
		lblProp(x2y7,space,50,35);
		
		x2y6 = new JLabel();
		lblProp(x2y6,space,50,60);
		
		x2y5 = new JLabel();
		lblProp(x2y5,space,50,85);
		
		x2y4 = new JLabel();
		lblProp(x2y4,space,50,110);
		
		x2y3 = new JLabel();
		lblProp(x2y3,space,50,135);
		
		x2y2 = new JLabel();
		lblProp(x2y2,space,50,160);
		
		x2y1 = new JLabel();
		lblProp(x2y1,space,50,185);
		
		x2y0 = new JLabel();
		lblProp(x2y0,space,50,210);
		
		
		x3y7 = new JLabel();
		lblProp(x3y7,space,75,35);
		
		x3y6 = new JLabel();
		lblProp(x3y6,space,75,60);
		
		x3y5 = new JLabel();
		lblProp(x3y5,space,75,85);
		
		x3y4 = new JLabel();
		lblProp(x3y4,space,75,110);
		
		x3y3 = new JLabel();
		lblProp(x3y3,space,75,135);
		
		x3y2 = new JLabel();
		lblProp(x3y2,space,75,160);
		
		x3y1 = new JLabel();
		lblProp(x3y1,space,75,185);
		
		x3y0 = new JLabel();
		lblProp(x3y0,space,75,210);
		
		x4y7 = new JLabel();
		lblProp(x4y7,space,100,35);
		
		x4y6 = new JLabel();
		lblProp(x4y6,space,100,60);
		
		x4y5 = new JLabel();
		lblProp(x4y5,obs,100,85);
		
		x4y4 = new JLabel();
		lblProp(x4y4,obs,100,110);
		
		x4y3 = new JLabel();
		lblProp(x4y3,obs,100,135);
		
		x4y2 = new JLabel();
		lblProp(x4y2,obs,100,160);
		
		x4y1 = new JLabel();
		lblProp(x4y1,obs,100,185);
		
		x4y0 = new JLabel();
		lblProp(x4y0,space,100,210);
		
		x5y7 = new JLabel();
		lblProp(x5y7,space,125,35);
		
		x5y6 = new JLabel();
		lblProp(x5y6,space,125,60);
		
		x5y5 = new JLabel();
		lblProp(x5y5,space,125,85);
		
		x5y4 = new JLabel();
		lblProp(x5y4,space,125,110);
		
		x5y3 = new JLabel();
		lblProp(x5y3,space,125,135);
		
		x5y2 = new JLabel();
		lblProp(x5y2,space,125,160);
		
		x5y1 = new JLabel();
		lblProp(x5y1,space,125,185);
		
		x5y0 = new JLabel();
		lblProp(x5y0,space,125,210);
		
		x6y7 = new JLabel();
		lblProp(x6y7,space,150,35);
		
		x6y6 = new JLabel();
		lblProp(x6y6,space,150,60);
		
		x6y5 = new JLabel();
		lblProp(x6y5,space,150,85);
		
		x6y4 = new JLabel();
		lblProp(x6y4,space,150,110);
		
		x6y3 = new JLabel();
		lblProp(x6y3,space,150,135);
		
		x6y2 = new JLabel();
		lblProp(x6y2,space,150,160);
		
		x6y1 = new JLabel();
		lblProp(x6y1,space,150,185);
		
		x6y0 = new JLabel();
		lblProp(x6y0,space,150,210);
		
		x7y7 = new JLabel();
		lblProp(x7y7,space,175,35);
		
		x7y6 = new JLabel();
		lblProp(x7y6,space,175,60);
		
		x7y5 = new JLabel();
		lblProp(x7y5,obs,175,85);
		
		x7y4 = new JLabel();
		lblProp(x7y4,obs,175,110);
		
		x7y3 = new JLabel();
		lblProp(x7y3,obs,175,135);
		
		x7y2 = new JLabel();
		lblProp(x7y2,obs,175,160);
		
		x7y1 = new JLabel();
		lblProp(x7y1,obs,175,185);
		
		x7y0 = new JLabel();
		lblProp(x7y0,space,175,210);
		
		x8y7 = new JLabel();
		lblProp(x8y7,space,200,35);
		
		x8y6 = new JLabel();
		lblProp(x8y6,space,200,60);
		
		x8y5 = new JLabel();
		lblProp(x8y5,space,200,85);
		
		x8y4 = new JLabel();
		lblProp(x8y4,space,200,110);
		
		x8y3 = new JLabel();
		lblProp(x8y3,space,200,135);
		
		x8y2 = new JLabel();
		lblProp(x8y2,space,200,160);
		
		x8y1 = new JLabel();
		lblProp(x8y1,space,200,185);
		
		x8y0 = new JLabel();
		lblProp(x8y0,space,200,210);
		
		x9y7 = new JLabel();
		lblProp(x9y7,space,225,35);
		
		x9y6 = new JLabel();
		lblProp(x9y6,space,225,60);
		
		x9y5 = new JLabel();
		lblProp(x9y5,space,225,85);
		
		x9y4 = new JLabel();
		lblProp(x9y4,space,225,110);
		
		x9y3 = new JLabel();
		lblProp(x9y3,space,225,135);
		
		x9y2 = new JLabel();
		lblProp(x9y2,space,225,160);
		
		x9y1 = new JLabel();
		lblProp(x9y1,space,225,185);
		
		x9y0 = new JLabel();
		lblProp(x9y0,space,225,210);
		
		x10y7 = new JLabel();
		lblProp(x10y7,space,250,35);
		
		x10y6 = new JLabel();
		lblProp(x10y6,space,250,60);
		
		x10y5 = new JLabel();
		lblProp(x10y5,obs,250,85);
		
		x10y4 = new JLabel();
		lblProp(x10y4,obs,250,110);
		
		x10y3 = new JLabel();
		lblProp(x10y3,obs,250,135);
		
		x10y2 = new JLabel();
		lblProp(x10y2,obs,250,160);
		
		x10y1 = new JLabel();
		lblProp(x10y1,obs,250,185);
		
		x10y0 = new JLabel();
		lblProp(x10y0,space,250,210);
		
		x11y7 = new JLabel();
		lblProp(x11y7,space,275,35);
		
		x11y6 = new JLabel();
		lblProp(x11y6,space,275,60);
		
		x11y5 = new JLabel();
		lblProp(x11y5,space,275,85);
		
		x11y4 = new JLabel();
		lblProp(x11y4,space,275,110);
		
		x11y3 = new JLabel();
		lblProp(x11y3,space,275,135);
		
		x11y2 = new JLabel();
		lblProp(x11y2,space,275,160);
		
		x11y1 = new JLabel();
		lblProp(x11y1,space,275,185);
		
		x11y0 = new JLabel();
		lblProp(x11y0,space,275,210);
	
		label = new JLabel[][] {
			{x0y0,x1y0,x2y0,x3y0,x4y0,x5y0,x6y0,x7y0,x8y0,x9y0,x10y0,x11y0},
			{x0y1,x1y1,x2y1,x3y1,x4y1,x5y1,x6y1,x7y1,x8y1,x9y1,x10y1,x11y1},
			{x0y2,x1y2,x2y2,x3y2,x4y2,x5y2,x6y2,x7y2,x8y2,x9y2,x10y2,x11y2},
			{x0y3,x1y3,x2y3,x3y3,x4y3,x5y3,x6y3,x7y3,x8y3,x9y3,x10y3,x11y3},
			{x0y4,x1y4,x2y4,x3y4,x4y4,x5y4,x6y4,x7y4,x8y4,x9y4,x10y4,x11y4},
			{x0y5,x1y5,x2y5,x3y5,x4y5,x5y5,x6y5,x7y5,x8y5,x9y5,x10y5,x11y5},
			{x0y6,x1y6,x2y6,x3y6,x4y6,x5y6,x6y6,x7y6,x8y6,x9y6,x10y6,x11y6},
			{x0y7,x1y7,x2y7,x3y7,x4y7,x5y7,x6y7,x7y7,x8y7,x9y7,x10y7,x11y7},
		};
		
	}
	
	public void lblProp(JLabel val, ImageIcon icon,int X, int Y) {
		val.setSize(20,20);
		val.setLocation(X,Y);
		val.setIcon(icon);
		cp.add(val);
	}
	
	public void setRobotPos(int robotId, JLabel val) {
		if(robotId == 1) {
			ImageIcon rb1 = new ImageIcon("res/rb1.png");
			val.setIcon(rb1);
		} else if(robotId == 2) {
			ImageIcon rb2 = new ImageIcon("res/rb2.png");
			val.setIcon(rb2);
		} else if(robotId == 3) {
			ImageIcon rb3 = new ImageIcon("res/rb3.png");
			val.setIcon(rb3);			
		}
		
	}
	
	public void clearPos(JLabel val) {
		ImageIcon grid = new ImageIcon("res/grid.png");
		val.setIcon(grid);
	}
	
	public void setDestination(int robotId, JLabel val) {
		val.setBackground(Color.GREEN);
		val.setOpaque(true);
		//ImageIcon dest = new ImageIcon("res/rb1.png");
		//val.setIcon(dest);
	}
}
