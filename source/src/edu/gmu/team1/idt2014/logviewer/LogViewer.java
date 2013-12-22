package edu.gmu.team1.idt2014.logviewer;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import javax.swing.JFileChooser;

public class LogViewer {

	private JFrame frame;
	private JButton loadLog;
	private JButton runFilter;
	private JTextField filterText;
	private JTable table;
	
	private File file;

	private TableRowSorter<ReportTableModel> sorter;
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

	private int increment = 0;

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(10, 10));

		model = new ReportTableModel();

		table = new JTable();
		table.setColumnSelectionAllowed(true);
		sorter = new TableRowSorter<ReportTableModel>(model);
		table.setModel(model);
		table.setRowSorter(sorter);
		frame.getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);

		loadLog = new JButton();
		loadLog.setText("Load Log");
		loadLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
//				model.addData("d " + increment++, "t " + increment * 2,
//						(Math.random() < 0.5 ? "passed" : "failed"), "c", "m",
//						"b/nb", "i", "o");
				
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(chooser.FILES_ONLY);
				int returnVal = chooser.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION){
					file = chooser.getSelectedFile();
					System.out.println("File: " + file.getName());
				}
				else{
					System.out.println("Cancelled");
				}
								
				/*
				 * TODO: Load File Picker Load Report Data Add Data to Model
				 * model.addData(Date, Time, Passed, Class, Method,
				 * Branches/TotalBranches Input, Output, Expected Output)
				 * 
				 * If we can, we should make passed/fail have a greed/red
				 * background.
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
		runFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					RowFilter<ReportTableModel, Object> rf = null;
					String textFilter = filterText.getText();
					List<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>();
					filters.add(RowFilter.regexFilter(textFilter, 0));
					filters.add(RowFilter.regexFilter(textFilter, 1));
					filters.add(RowFilter.regexFilter(textFilter, 2));
					filters.add(RowFilter.regexFilter(textFilter, 3));
					filters.add(RowFilter.regexFilter(textFilter, 4));
					filters.add(RowFilter.regexFilter(textFilter, 5));
					filters.add(RowFilter.regexFilter(textFilter, 6));
					filters.add(RowFilter.regexFilter(textFilter, 7));
					filters.add(RowFilter.regexFilter(textFilter, 8));

					try {
						rf = RowFilter.orFilter(filters);
					} catch (java.util.regex.PatternSyntaxException e) {
						return;
					}
					sorter.setRowFilter(rf);
				} catch (Exception ex) {

				}
			}
		});
		bottomPanel.add(runFilter);
		frame.getRootPane().setDefaultButton(runFilter);
	}
}
