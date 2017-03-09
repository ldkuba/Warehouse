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
import java.awt.Color;

public class WarehouseWindow {

	private JFrame frame;
	static RobotManager manager;
	Robot rb1;
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
		manager.addRobot(rb1);
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblRobot = new JLabel("Robot 1: ");
		lblRobot.setFont(new Font("Dialog", Font.BOLD, 20));
		GridBagConstraints gbc_lblRobot = new GridBagConstraints();
		gbc_lblRobot.insets = new Insets(0, 0, 5, 5);
		gbc_lblRobot.gridx = 0;
		gbc_lblRobot.gridy = 1;
		frame.getContentPane().add(lblRobot, gbc_lblRobot);
		
		JLabel lblStatus = new JLabel("ACQUIRING STATUS");
		lblStatus.setFont(new Font("Dialog", Font.BOLD, 15));
		GridBagConstraints gbc_lblStatus = new GridBagConstraints();
		gbc_lblStatus.insets = new Insets(0, 0, 5, 0);
		gbc_lblStatus.gridx = 2;
		gbc_lblStatus.gridy = 1;
		frame.getContentPane().add(lblStatus, gbc_lblStatus);
		
		JLabel lblRobotLocation = new JLabel("Robot Location: ");
		GridBagConstraints gbc_lblRobotLocation = new GridBagConstraints();
		gbc_lblRobotLocation.insets = new Insets(0, 0, 5, 5);
		gbc_lblRobotLocation.gridx = 0;
		gbc_lblRobotLocation.gridy = 2;
		frame.getContentPane().add(lblRobotLocation, gbc_lblRobotLocation);
		
		JLabel lblCoords = new JLabel("coords");
		GridBagConstraints gbc_lblCoords = new GridBagConstraints();
		gbc_lblCoords.insets = new Insets(0, 0, 5, 0);
		gbc_lblCoords.gridx = 2;
		gbc_lblCoords.gridy = 2;
		frame.getContentPane().add(lblCoords, gbc_lblCoords);
		
		JLabel lblCurrentJob = new JLabel("Current Job: ");
		GridBagConstraints gbc_lblCurrentJob = new GridBagConstraints();
		gbc_lblCurrentJob.insets = new Insets(0, 0, 0, 5);
		gbc_lblCurrentJob.gridx = 0;
		gbc_lblCurrentJob.gridy = 3;
		frame.getContentPane().add(lblCurrentJob, gbc_lblCurrentJob);
		
		JLabel lblJobid = new JLabel("jobID");
		GridBagConstraints gbc_lblJobid = new GridBagConstraints();
		gbc_lblJobid.gridx = 2;
		gbc_lblJobid.gridy = 3;
		frame.getContentPane().add(lblJobid, gbc_lblJobid);
		
		
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
		
		
		
		
		
		JFrame frame2 = new JFrame("Warehouse Map");
		frame2.add(viz);
		frame.addWindowListener(new KillMeNow());
		frame2.pack();
		frame2.setSize(450,300);
		frame2.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.setVisible(true);
		
		 int delay = 2000; //milliseconds
	      ActionListener taskPerformer = new ActionListener() {
	          public void actionPerformed(ActionEvent evt) {
	              String status = manager.getRobot(0).getRobotStatus().toString(); 
	              
	              if(status.equals("IDLE")) {
	              		lblStatus.setText(status);
	              		lblStatus.setForeground(Color.BLUE);
	              		log4j.trace("Updated Robot Status: " + status);
	              }	else if(status.equals("RUNNING")) {
	            	  	lblStatus.setText(status);
	              		lblStatus.setForeground(Color.GREEN);
	              		log4j.trace("Updated Robot Status: " + status);
	              }else if(status.equals("STUCK")) {
	            	  	lblStatus.setText(status);
	              		lblStatus.setForeground(Color.RED);
	              		log4j.trace("Updated Robot Status: " + status);
	              } else {
	            	  	lblStatus.setText(status);
	              		lblStatus.setForeground(Color.ORANGE);
	              		log4j.trace("Updated Robot Status: " + status);
	              }
	              
	              String location = "("+ manager.getRobot(0).getXLoc() + "," + manager.getRobot(0).getYLoc() + ")";
	              lblCoords.setText(location);
	              log4j.trace("Updated Robot Position: " + location);

	              /*
	              Commented out for now as it crashes when no ID is returned
	              
	              String jobID = manager.getRobot(0).getCurrentJob().getJobId();
	              if(manager.getRobot(0).getCurrentJob().getJobId() == null)
	            	  jobID = "No Current Job";
	              else jobID = manager.getRobot(0).getCurrentJob().getJobId();
	              lblJobid.setText(jobID); */
	              
	          }
	      };
	      new Timer(delay, taskPerformer).start();
	}
	
	
	public  static int getX() {
		return manager.getRobot(0).getXLoc();
	}
	
	public  static int getY() {
		return manager.getRobot(0).getYLoc();
	}

}
