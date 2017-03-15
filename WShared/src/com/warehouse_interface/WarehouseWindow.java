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

import lejos.robotics.RangeFinder;
import rp.robotics.MobileRobotWrapper;
import rp.robotics.control.RandomGridWalk;
import rp.robotics.mapping.GridMap;
import rp.robotics.mapping.MapUtils;
import rp.robotics.navigation.GridPose;
import rp.robotics.navigation.Heading;
import rp.robotics.simulation.MapBasedSimulation;
import rp.robotics.simulation.MovableRobot;
import rp.robotics.simulation.SimulatedRobots;
import rp.robotics.visualisation.GridMapVisualisation;
import rp.robotics.visualisation.KillMeNow;
import rp.robotics.visualisation.MapVisualisationComponent;

import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

public class WarehouseWindow {

	private JFrame frame;
	static RobotManager manager;
	Robot rb1, rb2, rb3;
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
	 * Initialize the contents of the frame.
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
		frame.setBounds(100, 100, 700, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblRobot = new JLabel("Robot 1: ");
		lblRobot.setFont(new Font("Dialog", Font.BOLD, 20));
		GridBagConstraints gbc_lblRobot = new GridBagConstraints();
		gbc_lblRobot.anchor = GridBagConstraints.WEST;
		gbc_lblRobot.insets = new Insets(0, 0, 5, 5);
		gbc_lblRobot.gridx = 0;
		gbc_lblRobot.gridy = 1;
		frame.getContentPane().add(lblRobot, gbc_lblRobot);
		
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
		
		JLabel lblRobot2 = new JLabel("Robot 2:");
		lblRobot2.setFont(new Font("Dialog", Font.BOLD, 20));
		GridBagConstraints gbc_lblRobot2 = new GridBagConstraints();
		gbc_lblRobot2.anchor = GridBagConstraints.WEST;
		gbc_lblRobot2.insets = new Insets(0, 0, 5, 5);
		gbc_lblRobot2.gridx = 0;
		gbc_lblRobot2.gridy = 5;
		frame.getContentPane().add(lblRobot2, gbc_lblRobot2);
		
		JLabel lblStatus2 = new JLabel("ACQUIRING STATUS");
		lblStatus2.setFont(new Font("Dialog", Font.BOLD, 15));
		GridBagConstraints gbc_lblStatus2 = new GridBagConstraints();
		gbc_lblStatus2.anchor = GridBagConstraints.WEST;
		gbc_lblStatus2.insets = new Insets(0, 0, 5, 5);
		gbc_lblStatus2.gridx = 2;
		gbc_lblStatus2.gridy = 5;
		frame.getContentPane().add(lblStatus2, gbc_lblStatus2);
		
		JLabel lblRobotLocation2 = new JLabel("Robot Location:");
		GridBagConstraints gbc_lblRobotLocation2 = new GridBagConstraints();
		gbc_lblRobotLocation2.anchor = GridBagConstraints.WEST;
		gbc_lblRobotLocation2.insets = new Insets(0, 0, 5, 5);
		gbc_lblRobotLocation2.gridx = 0;
		gbc_lblRobotLocation2.gridy = 6;
		frame.getContentPane().add(lblRobotLocation2, gbc_lblRobotLocation2);
		
		JLabel lblCoords2 = new JLabel("coords2");
		GridBagConstraints gbc_lblCoords2 = new GridBagConstraints();
		gbc_lblCoords2.anchor = GridBagConstraints.WEST;
		gbc_lblCoords2.insets = new Insets(0, 0, 5, 5);
		gbc_lblCoords2.gridx = 2;
		gbc_lblCoords2.gridy = 6;
		frame.getContentPane().add(lblCoords2, gbc_lblCoords2);
		
		JLabel lblCurrentJob2 = new JLabel("Current Job:");
		GridBagConstraints gbc_lblCurrentJob2 = new GridBagConstraints();
		gbc_lblCurrentJob2.anchor = GridBagConstraints.WEST;
		gbc_lblCurrentJob2.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurrentJob2.gridx = 0;
		gbc_lblCurrentJob2.gridy = 7;
		frame.getContentPane().add(lblCurrentJob2, gbc_lblCurrentJob2);
		
		JLabel lblJobid2 = new JLabel("jobId2");
		GridBagConstraints gbc_lblJobid2 = new GridBagConstraints();
		gbc_lblJobid2.anchor = GridBagConstraints.WEST;
		gbc_lblJobid2.insets = new Insets(0, 0, 5, 5);
		gbc_lblJobid2.gridx = 2;
		gbc_lblJobid2.gridy = 7;
		frame.getContentPane().add(lblJobid2, gbc_lblJobid2);
		
		JButton btnWarehouseMap = new JButton("Warehouse Map");
		GridBagConstraints gbc_btnWarehouseMap = new GridBagConstraints();
		gbc_btnWarehouseMap.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnWarehouseMap.anchor = GridBagConstraints.NORTH;
		gbc_btnWarehouseMap.insets = new Insets(0, 0, 0, 5);
		gbc_btnWarehouseMap.gridx = 2;
		gbc_btnWarehouseMap.gridy = 8;
		frame.getContentPane().add(btnWarehouseMap, gbc_btnWarehouseMap);
		
			
			JButton btnRewardSummary = new JButton("Reward Summary");
			GridBagConstraints gbc_btnRewardSummary = new GridBagConstraints();
			gbc_btnRewardSummary.insets = new Insets(0, 0, 0, 5);
			gbc_btnRewardSummary.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnRewardSummary.gridx = 4;
			gbc_btnRewardSummary.gridy = 8;
			frame.getContentPane().add(btnRewardSummary, gbc_btnRewardSummary);
			
			JButton btnLoadJobList = new JButton("Load Job List");
			GridBagConstraints gbc_btnLoadJobList = new GridBagConstraints();
			gbc_btnLoadJobList.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnLoadJobList.anchor = GridBagConstraints.NORTH;
			gbc_btnLoadJobList.insets = new Insets(0, 0, 0, 5);
			gbc_btnLoadJobList.gridx = 5;
			gbc_btnLoadJobList.gridy = 8;
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
		
		
		//Preparation for Getting Job Reward History
		btnRewardSummary.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		GridMapVisualisation viz = createMapView();
		
		btnWarehouseMap.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				JFrame frame2 = new JFrame("Warehouse Map");
				frame2.getContentPane();
				frame2.getContentPane().add(viz);
				frame.addWindowListener(new KillMeNow());
				frame2.pack();
				frame2.setSize(450,300);
				frame2.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame2.setVisible(true);
				
			}
		});
		
		
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
	              Commented out as it throws exception when no job is found
	              setJob(lblJobid, 0);
	              setJob(lblJobid2,1);
	              setJob(lblJobid3, 2);
	              */
	          }
	      };
	      new Timer(delay, taskPerformer).start();
	}
	
	//Maybe used for map visualisation. Will remove if unnecessary
	
	public  static int getX() {
		return manager.getRobot(0).getXLoc();
	}
	
	public  static int getY() {
		return manager.getRobot(0).getYLoc();
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
	
	
	public GridMapVisualisation createMapView() {
		GridMap gridMap = MapUtils.createRealWarehouse();
		MapBasedSimulation sim = new MapBasedSimulation(gridMap);
		GridPose gridStart = new GridPose(manager.getRobot(0).getXLoc(), manager.getRobot(0).getYLoc(), Heading.PLUS_X);
		MobileRobotWrapper<MovableRobot> wrapper = sim.addRobot(
				SimulatedRobots.makeConfiguration(false, true),gridMap.toPose(gridStart));
		
		RangeFinder ranger = sim.getRanger(wrapper);

		SimulationController controller = new SimulationController(wrapper.getRobot(),
				gridMap, gridStart, ranger);
		
		new Thread(controller).start();
		GridMapVisualisation viz = new GridMapVisualisation(gridMap,
				sim.getMap());
		
		MapVisualisationComponent.populateVisualisation(viz, sim); 
		return viz;
	}

}
