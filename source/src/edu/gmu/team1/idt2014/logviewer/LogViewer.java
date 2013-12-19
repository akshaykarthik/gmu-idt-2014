package edu.gmu.team1.idt2014.logviewer;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class LogViewer {

	private JFrame frame;
	private JButton loadLog;
	private JButton runFilter;
	private JTextField filterText;
	private JTable table;

	private ReportTableModel model;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogViewer window = new LogViewer();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LogViewer() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(10, 10));

		model = new ReportTableModel();

		table = new JTable(model);
		table.setColumnSelectionAllowed(true);
		frame.getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);

		loadLog = new JButton();
		loadLog.setText("Load Log");
		loadLog.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				model.addData("d", "t", "p", "c", "m", "i", "o", "eo");
				/* TODO:
				 * Load File Picker
				 * Load Report Data
				 * Add Data to Model
				 * model.addData(Date, Time, Passed, Class, Method, Input, Output, Expected Output)
				 * 
				 * If we can, we should make passed/fail have a greed/red background.
				 */
				
				
			}
		});
		frame.getContentPane().add(loadLog, BorderLayout.PAGE_START);

		JPanel bottomPanel = new JPanel();
		frame.getContentPane().add(bottomPanel, BorderLayout.PAGE_END);
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));

		filterText = new JTextField();
		filterText.setColumns(40);
		bottomPanel.add(filterText);

		runFilter = new JButton();
		runFilter.setText("Filter");
		runFilter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				/* TODO:
				 * Take data from filterText and filter JTree with it.
				 * Take a look at http://docs.oracle.com/javase/tutorial/uiswing/components/table.html#sorting
				 */
				
				
			}
		});
		bottomPanel.add(runFilter);
		

	}

}
