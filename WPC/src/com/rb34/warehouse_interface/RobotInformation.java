package com.rb34.warehouse_interface;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.rb34.general.RobotManager;
import com.rb34.general.interfaces.IRobot.Status;
import com.rb34.job_input.Item;

public class RobotInformation {

	private JFrame frmDetailedRobotBreakdown;
	private JScrollPane scrollPane;
	private JPanel panel;
	private String lblName;
	private JButton btnCancelJob;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox;
	final static Logger log4j = Logger.getLogger(RobotInformation.class);
	private JButton btnRefresh;
	private JCheckBox chckbxAutoRefresh;
	static int robotNum;

	/**
	 * Launch the application.
	 */

	@SuppressWarnings("static-access")
	public RobotInformation(int robotNum) {
		lblName = "";
		this.robotNum = robotNum;
	}

	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RobotInformation window = new RobotInformation();
					window.frmDetailedRobotBreakdown.setVisible(true);
					log4j.info("Created Detailed Information Window");
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initialize() {
		frmDetailedRobotBreakdown = new JFrame();
		ImageIcon img = new ImageIcon("res/icon.png");
		frmDetailedRobotBreakdown.setIconImage(img.getImage());
		frmDetailedRobotBreakdown.setTitle("Detailed Job Breakdown");
		frmDetailedRobotBreakdown.setBounds(150, 100, 550, 300);
		frmDetailedRobotBreakdown.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmDetailedRobotBreakdown.pack();
		frmDetailedRobotBreakdown.setBounds(150, 100, 600, 250);
		frmDetailedRobotBreakdown.setLocation(400, 100);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		frmDetailedRobotBreakdown.getContentPane().setLayout(gridBagLayout);

		chckbxAutoRefresh = new JCheckBox("Auto Refresh");
		GridBagConstraints gbc_chckbxAutoRefresh = new GridBagConstraints();
		gbc_chckbxAutoRefresh.insets = new Insets(5, 5, 5, 5);
		gbc_chckbxAutoRefresh.gridx = 2;
		gbc_chckbxAutoRefresh.gridy = 1;
		frmDetailedRobotBreakdown.getContentPane().add(chckbxAutoRefresh, gbc_chckbxAutoRefresh);

		JLabel lblRobotSummary = new JLabel("Select Robot:");
		lblRobotSummary.setFont(new Font("Dialog", Font.BOLD, 20));
		GridBagConstraints gbc_lblRobotSummary = new GridBagConstraints();
		gbc_lblRobotSummary.insets = new Insets(5, 5, 5, 5);
		gbc_lblRobotSummary.gridx = 4;
		gbc_lblRobotSummary.gridy = 1;
		frmDetailedRobotBreakdown.getContentPane().add(lblRobotSummary, gbc_lblRobotSummary);

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "", "Robot 1", "Robot 2", "Robot 3" }));
		comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				comboBox = (JComboBox) e.getSource();
				String type = (String) comboBox.getSelectedItem();
				if (type.equals("Robot 1")) {
					setInformation(0);
				} else if (type.equals("Robot 2")) {
					if (robotNum == 2)
						setInformation(1);
					else {
						panel.removeAll();
						JLabel lbl = new JLabel("Robot Not Found");
						panel.add(lbl);
						frmDetailedRobotBreakdown.validate();
						frmDetailedRobotBreakdown.repaint();
					}
				} else if (type.equals("Robot 3")) {
					if (robotNum == 3)
						setInformation(2);
					else {
						panel.removeAll();
						JLabel lbl = new JLabel("Robot Not Found");
						panel.add(lbl);
						frmDetailedRobotBreakdown.validate();
						frmDetailedRobotBreakdown.repaint();
					}
				} else if (type.equals("")) {
					panel.removeAll();
					frmDetailedRobotBreakdown.validate();
					frmDetailedRobotBreakdown.repaint();
				}

			}
		});
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.anchor = GridBagConstraints.WEST;
		gbc_comboBox.insets = new Insets(5, 5, 5, 5);
		gbc_comboBox.gridx = 5;
		gbc_comboBox.gridy = 1;
		frmDetailedRobotBreakdown.getContentPane().add(comboBox, gbc_comboBox);

		btnCancelJob = new JButton("Cancel Job");
		GridBagConstraints gbc_btnCancelJob = new GridBagConstraints();
		gbc_btnCancelJob.insets = new Insets(5, 0, 5, 5);
		gbc_btnCancelJob.gridx = 6;
		gbc_btnCancelJob.gridy = 1;
		frmDetailedRobotBreakdown.getContentPane().add(btnCancelJob, gbc_btnCancelJob);

		btnCancelJob.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String type = (String) comboBox.getSelectedItem();
				if (type.equals("Robot 1")) {
					RobotManager.getRobot(0).setRobotStatus(Status.IDLE);
					log4j.info("Cancelled robot 1's current job");
				} else if (type.equals("Robot 2")) {
					if (robotNum == 2) {
						RobotManager.getRobot(1).setRobotStatus(Status.IDLE);
						log4j.info("Cancelled robot 2's current job");
					} else {
						panel.removeAll();
						JLabel lbl = new JLabel("Robot Not Found");
						panel.add(lbl);
						frmDetailedRobotBreakdown.validate();
						frmDetailedRobotBreakdown.repaint();
					}
				} else if (type.equals("Robot 3")) {
					if (robotNum == 3) {
						RobotManager.getRobot(2).setRobotStatus(Status.IDLE);
						log4j.info("Cancelled robot 3's current job");
					} else {
						panel.removeAll();
						JLabel lbl = new JLabel("Robot Not Found");
						panel.add(lbl);
						frmDetailedRobotBreakdown.validate();
						frmDetailedRobotBreakdown.repaint();
					}
				} else if (type.equals("")) {
					JLabel err = new JLabel("No Robot Selected");
					panel.add(err);
					panel.revalidate();
					panel.repaint();
				}

			}
		});

		btnRefresh = new JButton("Refresh");
		GridBagConstraints gbc_btnRefresh = new GridBagConstraints();
		gbc_btnRefresh.insets = new Insets(5, 5, 5, 5);
		gbc_btnRefresh.gridx = 7;
		gbc_btnRefresh.gridy = 1;
		frmDetailedRobotBreakdown.getContentPane().add(btnRefresh, gbc_btnRefresh);

		btnRefresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String type = (String) comboBox.getSelectedItem();
				if (type.equals("Robot 1")) {
					setInformation(0);
				} else if (type.equals("Robot 2")) {
					if (robotNum == 2)
						setInformation(1);
					else {
						panel.removeAll();
						JLabel lbl = new JLabel("Robot Not Found");
						panel.add(lbl);
						frmDetailedRobotBreakdown.validate();
						frmDetailedRobotBreakdown.repaint();
					}
				} else if (type.equals("Robot 3")) {
					if (robotNum == 3)
						setInformation(2);
					else {
						panel.removeAll();
						JLabel lbl = new JLabel("Robot Not Found");
						panel.add(lbl);
						frmDetailedRobotBreakdown.validate();
						frmDetailedRobotBreakdown.repaint();
					}
				} else if (type.equals("")) {
					panel.removeAll();
					frmDetailedRobotBreakdown.validate();
					frmDetailedRobotBreakdown.repaint();
				}

			}
		});

		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 9;
		gbc_scrollPane.insets = new Insets(5, 5, 0, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 4;
		frmDetailedRobotBreakdown.getContentPane().add(scrollPane, gbc_scrollPane);

		panel = new JPanel();
		scrollPane.setColumnHeaderView(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		chckbxAutoRefresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (chckbxAutoRefresh.isSelected()) {
					autoUpdate(2000);
				}

			}
		});

	}

	public void setInformation(int robotId) {
		ArrayList<Item> items = RobotManager.getRobot(robotId).getItemsToPick();
		String itemName = "";
		float itemReward = 0;
		float itemWeight = 0;
		int itemX = 0;
		int itemY = 0;

		panel.removeAll();

		String jobId = RobotManager.getRobot(robotId).getCurrentJob().getJobId();
		JLabel itemHead = new JLabel("<html><u>Current Job ID:</u> " + jobId + "</html>");
		itemHead.setFont(new Font("Dialog", Font.BOLD, 15));

		JLabel jobContain = new JLabel("<html><u>This Job Contains the Following Items:</u></html>");
		jobContain.setFont(new Font("Dialog", Font.BOLD, 15));

		panel.add(itemHead);
		panel.add(jobContain);

		for (Item item : items) {
			itemReward = item.getReward();
			itemName = item.getItemID();
			itemWeight = item.getWeight();
			itemX = item.getX();
			itemY = item.getY();
			lblName = "   * Item ID: " + itemName + "  " + " Item Reward: " + itemReward + "  " + " Item Weight: "
					+ itemWeight + "  " + " Item Location: " + "(" + itemX + "," + itemY + ")";
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

		JLabel rewardTot = new JLabel(
				"<html><u>Total Reward:</u> " + reward + " <u>Total Weight:</u> " + weight + "</html>");
		rewardTot.setFont(new Font("Dialog", Font.BOLD, 15));
		panel.add(rewardTot);

		JLabel destHead = new JLabel("<html><u>Planned Destinations:</u> </html>");
		destHead.setFont(new Font("Dialog", Font.BOLD, 15));
		panel.add(destHead);

		ArrayList<String> dests = RobotManager.getRobot(robotId).getDestinations();
		String destList = dests.toString();

		JLabel dest = new JLabel(destList);
		panel.add(dest);

		boolean wasCancelled = RobotManager.getRobot(robotId).getCurrentJob().getCancelled();

		if (wasCancelled) {
			String cncl = "WARNING: This job was previously cancelled";
			JLabel cancel = new JLabel(cncl);
			panel.add(cancel);

		}

		frmDetailedRobotBreakdown.validate();
		frmDetailedRobotBreakdown.repaint();

	}

	public void autoUpdate(int interval) {

		int delay = interval;

		ActionListener refreshData = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String type = (String) comboBox.getSelectedItem();
				if (type.equals("Robot 1")) {
					setInformation(0);
				} else if (type.equals("Robot 2")) {
					if (robotNum == 2)
						setInformation(1);
					else {
						panel.removeAll();
						JLabel lbl = new JLabel("Robot Not Found");
						panel.add(lbl);
						frmDetailedRobotBreakdown.validate();
						frmDetailedRobotBreakdown.repaint();
					}
				} else if (type.equals("Robot 3")) {
					if (robotNum == 3)
						setInformation(2);
					else {
						panel.removeAll();
						JLabel lbl = new JLabel("Robot Not Found");
						panel.add(lbl);
						frmDetailedRobotBreakdown.validate();
						frmDetailedRobotBreakdown.repaint();
					}
				} else if (type.equals("")) {
					panel.removeAll();
					frmDetailedRobotBreakdown.validate();
					frmDetailedRobotBreakdown.repaint();
				}

			}
		};
		new Timer(delay, refreshData).start();

		log4j.info("Auto refresh enabled with interval " + delay);

	}

}
