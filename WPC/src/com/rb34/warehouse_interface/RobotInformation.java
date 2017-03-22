package com.rb34.warehouse_interface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import javax.swing.JScrollPane;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import com.rb34.general.RobotManager;
import com.rb34.jobInput.Item;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.BoxLayout;

public class RobotInformation {

	private JFrame frmDetailedRobotBreakdown;
	private JLabel lblDisplayinfo;
	static RobotManager manager;
	private JScrollPane scrollPane;
	private JPanel panel;
	private String lblName;
	/**
	 * Launch the application.
	 */
	
	public RobotInformation(RobotManager manager) {
		this.manager = manager;
		lblName = "";
	}  
	
	public static void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RobotInformation window = new RobotInformation();
					window.frmDetailedRobotBreakdown.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RobotInformation() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDetailedRobotBreakdown = new JFrame();
		ImageIcon img = new ImageIcon("res/icon.png");
		frmDetailedRobotBreakdown.setIconImage(img.getImage());
		frmDetailedRobotBreakdown.setTitle("Detailed Job Breakdown");
		frmDetailedRobotBreakdown.setBounds(150, 100, 550, 300);
		frmDetailedRobotBreakdown.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		frmDetailedRobotBreakdown.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblRobotSummary = new JLabel("Select Robot:");
		lblRobotSummary.setFont(new Font("Dialog", Font.BOLD, 20));
		GridBagConstraints gbc_lblRobotSummary = new GridBagConstraints();
		gbc_lblRobotSummary.insets = new Insets(0, 0, 5, 5);
		gbc_lblRobotSummary.gridx = 1;
		gbc_lblRobotSummary.gridy = 1;
		frmDetailedRobotBreakdown.getContentPane().add(lblRobotSummary, gbc_lblRobotSummary);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"", "Robot 1", "Robot 2", "Robot 3"}));
		comboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				String type = (String)cb.getSelectedItem();
				if(type.equals("Robot 1")) {
					setInformation(0);
				} else if(type.equals("Robot 2")) {
					setInformation(1);
				} else if(type.equals("Robot 3")) {
					setInformation(2);
				} else if(type.equals("")) {
					panel.removeAll();
		            frmDetailedRobotBreakdown.validate();
		            frmDetailedRobotBreakdown.repaint();
				}
				
			}
		});
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.anchor = GridBagConstraints.WEST;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 1;
		frmDetailedRobotBreakdown.getContentPane().add(comboBox, gbc_comboBox);
		
		lblDisplayinfo = new JLabel("displayInfo");
		GridBagConstraints gbc_lblDisplayinfo = new GridBagConstraints();
		gbc_lblDisplayinfo.gridwidth = 2;
		gbc_lblDisplayinfo.insets = new Insets(0, 0, 5, 5);
		gbc_lblDisplayinfo.gridx = 1;
		gbc_lblDisplayinfo.gridy = 3;
		frmDetailedRobotBreakdown.getContentPane().add(lblDisplayinfo, gbc_lblDisplayinfo);
		lblDisplayinfo.setVisible(false);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.insets = new Insets(5, 5, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 4;
		frmDetailedRobotBreakdown.getContentPane().add(scrollPane, gbc_scrollPane);
		
		panel = new JPanel();
		scrollPane.setColumnHeaderView(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


	}
	
	public void setInformation(int robotId) {
		ArrayList<Item> items = manager.getRobot(robotId).getItemsToPick();
		String itemName = "";
		float itemReward = 0;
		float itemWeight = 0;
		int itemX = 0;
		int itemY = 0;

		lblDisplayinfo.setText("Robot " + (robotId + 1) + " Info:");
		lblDisplayinfo.setVisible(true);
		panel.removeAll(); 
		
		String jobId = manager.getRobot(robotId).getCurrentJob().getJobId();
		JLabel itemHead = new JLabel("Current Job ID: " + jobId);
		itemHead.setFont(new Font("Dialog", Font.BOLD, 15));
		
		JLabel jobContain = new JLabel("This Job Contains the Following Items: ");
		jobContain.setFont(new Font("Dialog", Font.BOLD, 15));
		
		panel.add(itemHead);
		panel.add(jobContain);
		
		for (Item item : items) {
			itemReward = item.getReward();
			itemName = item.getItemID();
			itemWeight = item.getWeight();
			itemX = item.getX();
			itemY = item.getY();
			lblName = "Item ID: " + itemName + "  " +  " Item Reward: " + itemReward + "  " + " Item Weight: " + itemWeight + "  " + " Item Location: " + "(" + itemX + "," + itemY + ")";
			JLabel val1 = new JLabel(lblName);
			panel.add(val1);

		}
	
		float reward = 0;
		
		for (Item item : items) {
			reward = reward + item.getReward();
		}
		
		float weight = 0;
		
		for (Item item : items) {
			weight = weight + item.getWeight();
		}
		
		JLabel rewardTot = new JLabel("Total Job Reward: " + reward + "   Total Job Weight: " + weight);
		rewardTot.setFont(new Font("Dialog", Font.BOLD, 15));
		panel.add(rewardTot);
		
		JLabel destHead = new JLabel("Planned Destinations: ");
		destHead.setFont(new Font("Dialog", Font.BOLD, 15));
		panel.add(destHead);
		
		ArrayList<String> dests = manager.getRobot(robotId).getDestinations();
		String destList = dests.toString();
		
		JLabel dest = new JLabel(destList);
		panel.add(dest);
		
        frmDetailedRobotBreakdown.validate();
        frmDetailedRobotBreakdown.repaint();
		
		
	}

}
