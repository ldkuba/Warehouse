package com.rb34.warehouse_interface;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import javax.swing.JFrame;

import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;

import com.rb34.general.Robot;
import com.rb34.general.RobotManager;
import com.rb34.localisation.Map;

import rp.robotics.MobileRobotWrapper;
import rp.robotics.mapping.GridMap;
import rp.robotics.navigation.GridPose;
import rp.robotics.navigation.Heading;
import rp.robotics.simulation.MapBasedSimulation;
import rp.robotics.simulation.MovableRobot;
import rp.robotics.simulation.SimulatedRobots;
import rp.robotics.testing.TestMaps;
import rp.robotics.visualisation.GridMapVisualisation;
import rp.robotics.visualisation.KillMeNow;
import rp.robotics.visualisation.MapVisualisationComponent;

import org.eclipse.swt.widgets.Button;

public class MainWindow {

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		
		RobotManager manager = new RobotManager();
		Robot robot1 = new Robot();
		manager.addRobot(robot1);
		Display display = Display.getDefault();
		Shell shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("Warehouse Interface");
		shell.setLayout(new GridLayout(2, false));
		
		Label lblRobot = new Label(shell, SWT.NONE);
		lblRobot.setFont(SWTResourceManager.getFont("Segoe UI", 20, SWT.BOLD));
		lblRobot.setText("Robot 1: ");
		
		Label lblStatus = new Label(shell, SWT.NONE);
		lblStatus.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		lblStatus.setFont(SWTResourceManager.getFont("Segoe UI", 20, SWT.NORMAL));
		lblStatus.setText("NULL");
		

		
		Label lblCurrentJob = new Label(shell, SWT.NONE);
		lblCurrentJob.setFont(SWTResourceManager.getFont("Segoe UI", 15, SWT.NORMAL));
		lblCurrentJob.setText("Current Job: ");
		
		Label lblJobid = new Label(shell, SWT.NONE);
		lblJobid.setFont(SWTResourceManager.getFont("Segoe UI", 15, SWT.NORMAL));
		lblJobid.setText("null");
		
		Label lblCurrentPosition = new Label(shell, SWT.NONE);
		lblCurrentPosition.setFont(SWTResourceManager.getFont("Segoe UI", 15, SWT.NORMAL));
		lblCurrentPosition.setText("Current Position: ");
		
		Label lblCoords = new Label(shell, SWT.NONE);
		lblCoords.setFont(SWTResourceManager.getFont("Segoe UI", 15, SWT.NORMAL));
		lblCoords.setText("(null,null)");
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Button btnGetInfo = new Button(shell, SWT.NONE);
		btnGetInfo.setFont(SWTResourceManager.getFont("Segoe UI", 20, SWT.NORMAL));
		btnGetInfo.setText("GET INFO");
		btnGetInfo.addListener(SWT.Selection, new Listener() {
		    public void handleEvent(Event e) {
		      switch (e.type) {
		      case SWT.Selection:
		        lblStatus.setText(manager.getRobot(0).getRobotStatus().toString());
		        //lblJobid.setText(manager.getRobot(0).getCurrentJob().toString());
		        lblCoords.setText("(" + manager.getRobot(0).getXLoc() + "," + manager.getRobot(0).getYLoc() + ")");;
		        break;
		      }
		    }
		  });
		new Label(shell, SWT.NONE);
		
		MapBasedSimulation sim = new MapBasedSimulation(TestMaps.EMPTY_8_x_6);
		GridMap gridMap = Map.createGridMap();
		
		
		GridMapVisualisation viz = new GridMapVisualisation(gridMap,
				sim.getMap());
		GridPose gridStart = new GridPose(0,0, Heading.PLUS_X);
		MobileRobotWrapper<MovableRobot> wrapper = sim.addRobot(
				SimulatedRobots.makeConfiguration(false, true),
				gridMap.toPose(gridStart));
		MapVisualisationComponent.populateVisualisation(viz, sim);
		final JFrame frame = new JFrame("Simulation Viewer");
		
		frame.add(viz);
		frame.addWindowListener(new KillMeNow());
		
		frame.pack();
		frame.setSize(1000,900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);


		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	
	}

}
