package com.rb34.warehouse_interface;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.rb34.general.Robot;
import com.rb34.general.RobotManager;
import com.rb34.general.interfaces.IRobot.Status;

class PathFrame extends Thread{

	/* Col , Row // X, Y
			 r . . . . . . .  11,7
			 ^ 				  .
			 |				  .
			 0,0 -> c . . . . 11,0
	*/

	private JFrame frame;

	private boolean isRunning;

	private final int COLUMNS;
	private final int ROWS;

	private HashMap<String, JLabel> grid;

	private ImageIcon wallImage;
	private ImageIcon groundImage;
	private ImageIcon dropoffImage;
	private ArrayList<ImageIcon> robotImage;
	private ArrayList<ImageIcon> robotWaitImage;
	private ArrayList<ImageIcon> robotGoalImage;

	PathFrame(){
		frame = new JFrame();
		grid = new HashMap<>();
		robotImage = new ArrayList<>();
		robotGoalImage = new ArrayList<>();
		robotWaitImage = new ArrayList<>();

		isRunning = true;
		COLUMNS = 12;
		ROWS = 8;

		frame.setPreferredSize(new Dimension(600, 400));
		frame.setMinimumSize(new Dimension(408, 256));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setName("Warehouse Interface");
		frame.setLocationRelativeTo(null);
		frame.setLayout(new GridLayout(8, 12));
		frame.setResizable(false);
		frame.setVisible(true);

		frame.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e){

			}

			@Override
			public void keyPressed(KeyEvent e){
				switch(e.getKeyChar()){
					case '+':
						if(frame.getWidth() / COLUMNS != 110){    // Max Size
							frame.setSize(frame.getWidth() + 48, frame.getHeight() + 36);
						}
						break;
					case '-':
						frame.setSize(frame.getWidth() - 48, frame.getHeight() - 36);
						break;
				}

				updateTiles();
			}

			@Override
			public void keyReleased(KeyEvent e){

			}
		});
		frame.addWindowListener(new WindowListener(){
			@Override
			public void windowOpened(WindowEvent e){

			}

			@Override
			public void windowClosing(WindowEvent e){

			}

			@Override
			public void windowClosed(WindowEvent e){
				isRunning = false;
			}

			@Override
			public void windowIconified(WindowEvent e){

			}

			@Override
			public void windowDeiconified(WindowEvent e){

			}

			@Override
			public void windowActivated(WindowEvent e){

			}

			@Override
			public void windowDeactivated(WindowEvent e){

			}
		});

		setElements();
		addElements();
		updateTiles();
	}

	@Override
	public void run(){
		while(isRunning){
			updateTiles();
			try{
				Thread.sleep(1000);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * Initialize all components to be added to the frame
	 */
	private void setElements(){
		for(int r = 0; r < ROWS; r++){
			for(int c = 0; c < COLUMNS; c++){
				grid.put(r + "," + c, new JLabel());
			}
		}

		for(int i = 0; i < 3; i++){
			robotImage.add(
					new ImageIcon(
							new ImageIcon("resources/Robot" + (i + 1) + ".png")
									.getImage()
									.getScaledInstance(frame.getWidth() / COLUMNS, frame.getHeight() / ROWS, Image.SCALE_SMOOTH))
			);

			robotGoalImage.add(
					new ImageIcon(
							new ImageIcon("resources/Robot" + (i + 1) + "Goal.png")
									.getImage()
									.getScaledInstance(frame.getWidth() / COLUMNS, frame.getHeight() / ROWS, Image.SCALE_SMOOTH))
			);

			robotWaitImage.add(
					new ImageIcon(
							new ImageIcon("resources/Robot" + (i + 1) + "Waiting.png")
									.getImage()
									.getScaledInstance(frame.getWidth() / COLUMNS, frame.getHeight() / ROWS, Image.SCALE_SMOOTH))
			);
		}

		updateTiles();
	}

	/**
	 * Adds all the components to the frame
	 */
	private void addElements(){
		String key;
		for(int r = 0; r < ROWS; r++){
			for(int c = 0; c < COLUMNS; c++){
				key = (ROWS - r - 1) + "," + c;
				frame.add(grid.get(key));
			}
		}

		frame.pack();
	}

	/**
	 * Updates the labels to integrate the resized images
	 */
	private void updateTiles(){
		updateImageSizes();

		String key;

		// Ground
		for(int r = 0; r < ROWS; r++){
			for(int c = 0; c < COLUMNS; c++){
				key = r + "," + c;
				grid.get(key).setIcon(groundImage);
			}
		}

		// Walls
		for(int r = 1; r != 6; r++){
			for(int c = 1; c < 11; c += 3){
				key = r + "," + c;
				grid.get(key).setIcon(wallImage);
			}

		}

		// Dropoffs
		for(int i = 0; i < RobotManager.getDropoffList().size(); i++){

		}

		// Robots + Destinations
		for(Robot robot : RobotManager.getRobots()){
			key = robot.getXDes() + "," + robot.getYDes();
			grid.get(key).setIcon(robotGoalImage.get(robot.getRobotId()));

			key = robot.getXLoc() + "," + robot.getYLoc();
			if(robot.getRobotStatus() == Status.AT_ITEM){
				grid.get(key).setIcon(robotWaitImage.get(robot.getRobotId()));
			}else{
				grid.get(key).setIcon(robotImage.get(robot.getRobotId()));
			}
		}
	}

	/**
	 * Updates the size of each image
	 */
	private synchronized void updateImageSizes(){
		wallImage = new ImageIcon(
				new ImageIcon("resources/Wall.png")
						.getImage()
						.getScaledInstance(frame.getWidth() / COLUMNS, frame.getHeight() / ROWS, Image.SCALE_SMOOTH));

		groundImage = new ImageIcon(
				new ImageIcon("resources/Ground.png")
						.getImage()
						.getScaledInstance(frame.getWidth() / COLUMNS, frame.getHeight() / ROWS, Image.SCALE_SMOOTH));

		dropoffImage = new ImageIcon(
				new ImageIcon("resources/Dropoff.png")
						.getImage()
						.getScaledInstance(frame.getWidth() / COLUMNS, frame.getHeight() / ROWS, Image.SCALE_SMOOTH));

		for(int i = 2; i > -1; i--){
			robotImage.remove(i);
			robotImage.add(
					new ImageIcon(
							new ImageIcon("resources/Robot" + i + ".png")
									.getImage()
									.getScaledInstance(frame.getWidth() / COLUMNS, frame.getHeight() / ROWS, Image.SCALE_SMOOTH))
			);

			robotGoalImage.remove(i);
			robotGoalImage.add(
					new ImageIcon(
							new ImageIcon("resources/Robot" + i + "Goal.png")
									.getImage()
									.getScaledInstance(frame.getWidth() / COLUMNS, frame.getHeight() / ROWS, Image.SCALE_SMOOTH))
			);

			robotWaitImage.remove(i);
			robotWaitImage.add(
					new ImageIcon(
							new ImageIcon("resources/Robot" + i + "Waiting.png")
									.getImage()
									.getScaledInstance(frame.getWidth() / COLUMNS, frame.getHeight() / ROWS, Image.SCALE_SMOOTH))
			);
		}
	}
}