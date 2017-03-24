package com.rb34.warehouse_interface;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.rb34.general.RobotManager;
import com.rb34.job_input.Item;

public class MainWindow {

	private JFrame frame;
	static RobotManager manager;
	static int robotNum;
	final static Logger log4j = Logger.getLogger(MainWindow.class);

	/**
	 * Launch the application.
	 */
	
	public MainWindow(RobotManager rm, int robotNum) {
		this.manager = rm;
		this.robotNum = robotNum;
	} 
	
	public void launch() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
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
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {;
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Warehouse Interface");
		frame.getContentPane().setFont(new Font("Dialog", Font.PLAIN, 20));
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setBounds(100, 100, 350, 600);
		
		JPanel rb1Pnl = new JPanel();
		rb1Pnl.setBounds(23, 88, 290, 164);
		frame.getContentPane().add(rb1Pnl);
		GridBagLayout gbl_rb1Pnl = new GridBagLayout();
		gbl_rb1Pnl.columnWidths = new int[]{0, 0, 0, 0};
		gbl_rb1Pnl.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_rb1Pnl.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_rb1Pnl.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		rb1Pnl.setLayout(gbl_rb1Pnl);
		
		JLabel lblRobot = new JLabel("Robot 1: ");
		lblRobot.setFont(new Font("Dialog", Font.BOLD, 20));
		GridBagConstraints gbc_lblRobot = new GridBagConstraints();
		gbc_lblRobot.anchor = GridBagConstraints.WEST;
		gbc_lblRobot.insets = new Insets(0, 0, 5, 5);
		gbc_lblRobot.gridx = 1;
		gbc_lblRobot.gridy = 0;
		rb1Pnl.add(lblRobot, gbc_lblRobot);
		
		JLabel lblStatus = new JLabel("STATUS");
		lblStatus.setFont(new Font("Dialog", Font.BOLD, 20));
		GridBagConstraints gbc_lblStatus = new GridBagConstraints();
		gbc_lblStatus.anchor = GridBagConstraints.WEST;
		gbc_lblStatus.insets = new Insets(0, 0, 5, 0);
		gbc_lblStatus.gridx = 2;
		gbc_lblStatus.gridy = 0;
		rb1Pnl.add(lblStatus, gbc_lblStatus);
		
		JLabel lblRobotLocation = new JLabel("Robot Location: ");
		GridBagConstraints gbc_lblRobotLocation = new GridBagConstraints();
		gbc_lblRobotLocation.anchor = GridBagConstraints.WEST;
		gbc_lblRobotLocation.insets = new Insets(0, 0, 5, 5);
		gbc_lblRobotLocation.gridx = 1;
		gbc_lblRobotLocation.gridy = 1;
		rb1Pnl.add(lblRobotLocation, gbc_lblRobotLocation);
		
		JLabel lblCoords = new JLabel("coords");
		GridBagConstraints gbc_lblCoords = new GridBagConstraints();
		gbc_lblCoords.anchor = GridBagConstraints.WEST;
		gbc_lblCoords.insets = new Insets(0, 0, 5, 0);
		gbc_lblCoords.gridx = 2;
		gbc_lblCoords.gridy = 1;
		rb1Pnl.add(lblCoords, gbc_lblCoords);
		
		JLabel lblCurrentJob = new JLabel("Current Job: ");
		GridBagConstraints gbc_lblCurrentJob = new GridBagConstraints();
		gbc_lblCurrentJob.anchor = GridBagConstraints.WEST;
		gbc_lblCurrentJob.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurrentJob.gridx = 1;
		gbc_lblCurrentJob.gridy = 2;
		rb1Pnl.add(lblCurrentJob, gbc_lblCurrentJob);
		
		JLabel lblJobid = new JLabel("jobID");
		GridBagConstraints gbc_lblJobid = new GridBagConstraints();
		gbc_lblJobid.insets = new Insets(0, 0, 5, 0);
		gbc_lblJobid.anchor = GridBagConstraints.WEST;
		gbc_lblJobid.gridx = 2;
		gbc_lblJobid.gridy = 2;
		rb1Pnl.add(lblJobid, gbc_lblJobid);
		
		JLabel lblPotentialReward = new JLabel("Potential  Reward: ");
		GridBagConstraints gbc_lblPotentialReward = new GridBagConstraints();
		gbc_lblPotentialReward.anchor = GridBagConstraints.WEST;
		gbc_lblPotentialReward.insets = new Insets(0, 0, 0, 5);
		gbc_lblPotentialReward.gridx = 1;
		gbc_lblPotentialReward.gridy = 3;
		rb1Pnl.add(lblPotentialReward, gbc_lblPotentialReward);
		
		JLabel lblReward = new JLabel("reward");
		GridBagConstraints gbc_lblReward = new GridBagConstraints();
		gbc_lblReward.anchor = GridBagConstraints.WEST;
		gbc_lblReward.gridx = 2;
		gbc_lblReward.gridy = 3;
		rb1Pnl.add(lblReward, gbc_lblReward);
		
		JPanel rb2Pnl = new JPanel();
		rb2Pnl.setBounds(23, 264, 290, 164);
		frame.getContentPane().add(rb2Pnl);
		GridBagLayout gbl_rb2Pnl = new GridBagLayout();
		gbl_rb2Pnl.columnWidths = new int[]{0, 0, 0, 0};
		gbl_rb2Pnl.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_rb2Pnl.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_rb2Pnl.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		rb2Pnl.setLayout(gbl_rb2Pnl);
		
		JLabel lblRobotHeading2 = new JLabel("Robot 2: ");
		lblRobotHeading2.setFont(new Font("Dialog", Font.BOLD, 20));
		GridBagConstraints gbc_lblRobotHeading2 = new GridBagConstraints();
		gbc_lblRobotHeading2.anchor = GridBagConstraints.WEST;
		gbc_lblRobotHeading2.insets = new Insets(0, 0, 5, 5);
		gbc_lblRobotHeading2.gridx = 1;
		gbc_lblRobotHeading2.gridy = 0;
		rb2Pnl.add(lblRobotHeading2, gbc_lblRobotHeading2);
		
		JLabel lblStatus2 = new JLabel("STATUS");
		lblStatus2.setFont(new Font("Dialog", Font.BOLD, 20));
		GridBagConstraints gbc_lblStatus2 = new GridBagConstraints();
		gbc_lblStatus2.anchor = GridBagConstraints.WEST;
		gbc_lblStatus2.insets = new Insets(0, 0, 5, 0);
		gbc_lblStatus2.gridx = 2;
		gbc_lblStatus2.gridy = 0;
		rb2Pnl.add(lblStatus2, gbc_lblStatus2);
		
		JLabel lblLocation2 = new JLabel("Robot Location: ");
		GridBagConstraints gbc_lblLocation2 = new GridBagConstraints();
		gbc_lblLocation2.anchor = GridBagConstraints.WEST;
		gbc_lblLocation2.insets = new Insets(0, 0, 5, 5);
		gbc_lblLocation2.gridx = 1;
		gbc_lblLocation2.gridy = 1;
		rb2Pnl.add(lblLocation2, gbc_lblLocation2);
		
		JLabel coords2 = new JLabel("coords");
		GridBagConstraints gbc_coords2 = new GridBagConstraints();
		gbc_coords2.anchor = GridBagConstraints.WEST;
		gbc_coords2.insets = new Insets(0, 0, 5, 0);
		gbc_coords2.gridx = 2;
		gbc_coords2.gridy = 1;
		rb2Pnl.add(coords2, gbc_coords2);
		
		JLabel lblCurrentJob2 = new JLabel("Current Job: ");
		GridBagConstraints gbc_lblCurrentJob2 = new GridBagConstraints();
		gbc_lblCurrentJob2.anchor = GridBagConstraints.WEST;
		gbc_lblCurrentJob2.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurrentJob2.gridx = 1;
		gbc_lblCurrentJob2.gridy = 2;
		rb2Pnl.add(lblCurrentJob2, gbc_lblCurrentJob2);
		
		JLabel jobID2 = new JLabel("jobID");
		GridBagConstraints gbc_jobID2 = new GridBagConstraints();
		gbc_jobID2.anchor = GridBagConstraints.WEST;
		gbc_jobID2.insets = new Insets(0, 0, 5, 0);
		gbc_jobID2.gridx = 2;
		gbc_jobID2.gridy = 2;
		rb2Pnl.add(jobID2, gbc_jobID2);
		
		JLabel lblPotentialReward2 = new JLabel("Potential  Reward: ");
		GridBagConstraints gbc_lblPotentialReward2 = new GridBagConstraints();
		gbc_lblPotentialReward2.anchor = GridBagConstraints.WEST;
		gbc_lblPotentialReward2.insets = new Insets(0, 0, 0, 5);
		gbc_lblPotentialReward2.gridx = 1;
		gbc_lblPotentialReward2.gridy = 3;
		rb2Pnl.add(lblPotentialReward2, gbc_lblPotentialReward2);
		
		JLabel reward2 = new JLabel("reward");
		GridBagConstraints gbc_reward2 = new GridBagConstraints();
		gbc_reward2.anchor = GridBagConstraints.WEST;
		gbc_reward2.gridx = 2;
		gbc_reward2.gridy = 3;
		rb2Pnl.add(reward2, gbc_reward2);
		
		JPanel rb3Pnl = new JPanel();
		rb3Pnl.setBounds(23, 446, 290, 164);
		frame.getContentPane().add(rb3Pnl);
		GridBagLayout gbl_rb3Pnl = new GridBagLayout();
		gbl_rb3Pnl.columnWidths = new int[]{0, 0, 0, 0};
		gbl_rb3Pnl.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_rb3Pnl.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_rb3Pnl.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		rb3Pnl.setLayout(gbl_rb3Pnl);
		
		JLabel lblRobotHeading3 = new JLabel("Robot 3: ");
		lblRobotHeading3.setFont(new Font("Dialog", Font.BOLD, 20));
		GridBagConstraints gbc_lblRobotHeading3 = new GridBagConstraints();
		gbc_lblRobotHeading3.anchor = GridBagConstraints.WEST;
		gbc_lblRobotHeading3.insets = new Insets(0, 0, 5, 5);
		gbc_lblRobotHeading3.gridx = 1;
		gbc_lblRobotHeading3.gridy = 0;
		rb3Pnl.add(lblRobotHeading3, gbc_lblRobotHeading3);
		
		JLabel lblStatus3 = new JLabel("STATUS");
		lblStatus3.setFont(new Font("Dialog", Font.BOLD, 20));
		GridBagConstraints gbc_lblStatus3 = new GridBagConstraints();
		gbc_lblStatus3.anchor = GridBagConstraints.WEST;
		gbc_lblStatus3.insets = new Insets(0, 0, 5, 0);
		gbc_lblStatus3.gridx = 2;
		gbc_lblStatus3.gridy = 0;
		rb3Pnl.add(lblStatus3, gbc_lblStatus3);
		
		JLabel lblRobotLocation3 = new JLabel("Robot Location: ");
		GridBagConstraints gbc_lblRobotLocation3 = new GridBagConstraints();
		gbc_lblRobotLocation3.anchor = GridBagConstraints.WEST;
		gbc_lblRobotLocation3.insets = new Insets(0, 0, 5, 5);
		gbc_lblRobotLocation3.gridx = 1;
		gbc_lblRobotLocation3.gridy = 1;
		rb3Pnl.add(lblRobotLocation3, gbc_lblRobotLocation3);
		
		JLabel coords3 = new JLabel("coords");
		GridBagConstraints gbc_coords3 = new GridBagConstraints();
		gbc_coords3.anchor = GridBagConstraints.WEST;
		gbc_coords3.insets = new Insets(0, 0, 5, 0);
		gbc_coords3.gridx = 2;
		gbc_coords3.gridy = 1;
		rb3Pnl.add(coords3, gbc_coords3);
		
		JLabel lblCurrentJob3 = new JLabel("Current Job: ");
		GridBagConstraints gbc_lblCurrentJob3 = new GridBagConstraints();
		gbc_lblCurrentJob3.anchor = GridBagConstraints.WEST;
		gbc_lblCurrentJob3.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurrentJob3.gridx = 1;
		gbc_lblCurrentJob3.gridy = 2;
		rb3Pnl.add(lblCurrentJob3, gbc_lblCurrentJob3);
		
		JLabel jobID3 = new JLabel("jobID");
		GridBagConstraints gbc_jobID3 = new GridBagConstraints();
		gbc_jobID3.anchor = GridBagConstraints.WEST;
		gbc_jobID3.insets = new Insets(0, 0, 5, 0);
		gbc_jobID3.gridx = 2;
		gbc_jobID3.gridy = 2;
		rb3Pnl.add(jobID3, gbc_jobID3);
		
		JLabel lblPotentialReward3 = new JLabel("Potential  Reward: ");
		GridBagConstraints gbc_lblPotentialReward3 = new GridBagConstraints();
		gbc_lblPotentialReward3.anchor = GridBagConstraints.WEST;
		gbc_lblPotentialReward3.insets = new Insets(0, 0, 0, 5);
		gbc_lblPotentialReward3.gridx = 1;
		gbc_lblPotentialReward3.gridy = 3;
		rb3Pnl.add(lblPotentialReward3, gbc_lblPotentialReward3);
		
		JLabel reward3 = new JLabel("reward");
		GridBagConstraints gbc_reward3 = new GridBagConstraints();
		gbc_reward3.anchor = GridBagConstraints.WEST;
		gbc_reward3.gridx = 2;
		gbc_reward3.gridy = 3;
		rb3Pnl.add(reward3, gbc_reward3);
		
		JButton btnWarehouseMap = new JButton("Warehouse Map");
		btnWarehouseMap.setBounds(46, 23, 222, 25);
		frame.getContentPane().add(btnWarehouseMap);
		
		JButton btnJobInformation = new JButton("Job Information");
		btnJobInformation.setBounds(46, 51, 222, 25);
		frame.getContentPane().add(btnJobInformation);
		
		int delay = 2000; //milliseconds
	      ActionListener uiUpdate = new ActionListener() {
	          public void actionPerformed(ActionEvent evt) {

	        	  setStatus(lblStatus,0);
	              
	        	  setCoords(lblCoords,0);
	        	  
	        	  setJob(lblJobid, 0);
	        	  
	        	  setReward(lblReward,0);
	        	  
	        	  if(robotNum == 2) {
	        		  setStatus(lblStatus,0);
	        		  setStatus(lblStatus2, 1);
	        		  
		              setCoords(lblCoords,0);
		              setCoords(coords2,1);
		              
		              setJob(lblJobid, 0);
		              setJob(jobID2,1);
		              
		              setReward(lblReward,0);
		              setReward(reward2, 1);
		              
	        	  } else if (robotNum == 3) {
	        		  setStatus(lblStatus,0);
	        		  setStatus(lblStatus2, 1);
	        		  setStatus(lblStatus3,2);
	        		  
		              setCoords(lblCoords,0);
		              setCoords(coords2,1);
		              setCoords(coords3,2);
		              
		              setJob(lblJobid, 0);
		              setJob(jobID2,1);
		              setJob(jobID3,2);
		              
		              setReward(lblReward,0);
		              setReward(reward2, 1);
		              setReward(reward3, 2);
	        	  }
	             

	          }
	      };
	      new Timer(delay, uiUpdate).start();

	   if(robotNum == 1) {
		   rb2Pnl.removeAll();
		   rb3Pnl.removeAll();
		   frame.setBounds(100, 100, 350, 250);
	   } else if (robotNum == 2) {
		   rb3Pnl.removeAll();
		   frame.setBounds(100, 100, 350, 400);
	   }
	   
	   btnJobInformation.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			RobotInformation info = new RobotInformation(manager,robotNum);
			info.run();
			
		}
	});
	     
		

	}
	
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
        String location = "("+ manager.getRobot(robotId).getXLoc() + "," + manager.getRobot(robotId).getYLoc() + ")" + " Heading: " + manager.getRobot(robotId).getHeading();;
        val.setText(location);
        int rb = robotId +1;
        log4j.trace("Updated Robot " + rb +  " Position: " + location);
	}
	
	public void setJob(JLabel val, int robotId) {
        String jobID = "";
        if(manager.getRobot(robotId).getCurrentJob().getJobId() == null) {
        	jobID = "No Current Job";
        } else { 
        	jobID = manager.getRobot(robotId).getCurrentJob().getJobId();
        }
        	
        val.setText("" + jobID);
        int rb = robotId +1;
        
        log4j.trace("Updated Robot " + rb + "'s Current Job");
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
}
