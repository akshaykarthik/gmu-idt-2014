package edu.gmu.team1.idt2014.logviewer;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
				// Random Record Generator
				// model.addData("d " + increment++, "t " + increment * 2,
				// (Math.random() < 0.5 ? "passed" : "failed"), "c", "m",
				// "b/nb", "i", "o");

				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnVal = chooser.showOpenDialog(null);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					file = chooser.getSelectedFile();
				} else {

				}

				addRecords(file);

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

	/*
	 * TODO: 1. Add expressions for class, method, branches, input, and output.
	 * 2. Add green/red background for pass/fail. 3. Add Upper/lowercase
	 * filtering fix on logviewer. 4. Add clear button for the log 5. Clean up.
	 */
	private void addRecords(File file) {

		final String IGNORE_LINE_PATTERN = "\\~";
		final String DATE_PATTERN = "\\d{1,2}\\/\\d{1,2}\\/\\d{4}";
		final String TIME_PATTERN = "\\d{2}:\\d{2}";
		final String PASS_PATTERN = "(passed|Passed)|(failed|Failed)|(pass|Pass)|(fail|Fail)";

		// "(?:\\[[cC]\\:)([a-zA-Z0-9_\\.]*)(?:\\])"
		// (?:\\[[cC]\\:) - matches [c:, ignores group ( (?: )
		// ([a-zA-Z0-9_\\.]*) - matches repeated a-zA-Z0-9_. group
		// (?:\\]) - matches ], ignores group ( (?: )
		final String CLASS_PATTERN = "(?:\\[[cC]\\:)([a-zA-Z0-9_\\.]*)(?:\\])";
		final String METHOD_PATTERN = "(?:\\[[mM]\\:)([a-zA-Z0-9_\\.]*)(?:\\])";
		final String BRANCH_PATTERN = "(?:\\[[bB]\\:)([a-zA-Z0-9_\\.]*)(?:\\])";
		final String INPUT_PATTERN = "(?:\\[[iI]\\:)([a-zA-Z0-9_\\.]*)(?:\\])";
		final String OUTPUT_PATTERN = "(?:\\[[oO]\\:)([a-zA-Z0-9_\\.]*)(?:\\])";

		String date, time, pass, cls, method, branches, input, output, line;
		date = time = pass = cls = method = branches = input = output = line = "";
		boolean flag = false;

		Scanner scan = null;

		try {
			scan = new Scanner(file);

			while ((line = scan.nextLine()) != null) {
				// System.out.println(line);

				Pattern p = Pattern.compile(IGNORE_LINE_PATTERN);
				Matcher m = p.matcher(line);
				while (m.find()) {
					flag = true;
					break;
				}
				if (flag) {
					flag = false;
					continue;
				}

				p = Pattern.compile(DATE_PATTERN);
				m = p.matcher(line);
				while (m.find()) {
					date = m.group(0);
					// System.out.println("Date: " + date);
				}

				p = Pattern.compile(TIME_PATTERN);
				m = p.matcher(line);
				while (m.find()) {
					time = m.group(0);
					// System.out.println("Time: " + time);
				}

				p = Pattern.compile(PASS_PATTERN);
				m = p.matcher(line);
				while (m.find()) {
					pass = m.group(0);
					// System.out.println("Status: " + pass + "\n");
				}

				p = Pattern.compile(CLASS_PATTERN);
				m = p.matcher(line);
				while (m.find()) {
					cls = m.group(1);
				}

				p = Pattern.compile(METHOD_PATTERN);
				m = p.matcher(line);
				while (m.find()) {
					method = m.group(1);
				}

				p = Pattern.compile(BRANCH_PATTERN);
				m = p.matcher(line);
				while (m.find()) {
					branches = m.group(1);
				}

				p = Pattern.compile(INPUT_PATTERN);
				m = p.matcher(line);
				while (m.find()) {
					input = m.group(1);
				}

				p = Pattern.compile(OUTPUT_PATTERN);
				m = p.matcher(line);
				while (m.find()) {
					output = m.group(1);
				}

				if ((date + time + pass + cls + method + branches + input + output)
						.equals(""))
					continue;
				model.addData(date, time, pass, cls, method, branches, input,
						output);
				date = time = pass = cls = method = branches = input = output = line = "";
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not Found");
		} catch (NoSuchElementException e2) {

		} catch (NullPointerException e3) {
			return;
		} finally {
			if (!(scan == null)){
				scan.close();
			}
		}
	}
}
