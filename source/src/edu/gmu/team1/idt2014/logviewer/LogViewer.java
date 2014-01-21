package edu.gmu.team1.idt2014.logviewer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
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

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

public class LogViewer {
	// final String IGNORE_LINE_PATTERN = "\\~";
	// final String DATE_PATTERN = "\\d{1,2}\\/\\d{1,2}\\/\\d{4}";
	// final String TIME_PATTERN = "\\d{2}:\\d{2}";
	// final String PASS_PATTERN =
	// "(passed|Passed)|(failed|Failed)|(pass|Pass)|(fail|Fail)";
	//
	// // "(?:\\[[cC]\\:)([a-zA-Z0-9_\\.]*)(?:\\])"
	// // (?:\\[[cC]\\:) - matches [c:, ignores group ( (?: )
	// // ([a-zA-Z0-9_\\.]*) - matches repeated a-zA-Z0-9_. group
	// // (?:\\]) - matches ], ignores group ( (?: )
	// final String CLASS_PATTERN = "(?:\\[[cC]\\:)([a-zA-Z0-9_\\.]*)(?:\\])";
	// final String METHOD_PATTERN = "(?:\\[[mM]\\:)([a-zA-Z0-9_\\.]*)(?:\\])";
	// final String BRANCH_PATTERN = "(?:\\[[bB]\\:)([a-zA-Z0-9_\\.]*)(?:\\])";
	// final String INPUT_PATTERN = "(?:\\[[iI]\\:)([a-zA-Z0-9_\\.]*)(?:\\])";
	// final String OUTPUT_PATTERN = "(?:\\[[oO]\\:)([a-zA-Z0-9_\\.]*)(?:\\])";
	// final String NOTES_PATTERN = "(?:\\[[nN]\\:)([a-zA-Z0-9_\\.]*)(?:\\])";
	//
	private JFrame frame;

	private JButton runFilter;
	private JTextField filterText;
	private JTable table;

	private File file;

	private TableRowSorter<ReportTableModel> sorter;
	private ReportTableModel model;
	private DefaultListModel<String> notesModel;

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
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		initMidPanel();

		JToolBar toolBar = new JToolBar();
		frame.getContentPane().add(toolBar, BorderLayout.SOUTH);

		toolBar.add(createLoadLog());

		toolBar.add(createShowFilter());

		filterText = new JTextField();

		toolBar.add(filterText);
		runFilter = createRunFilter();

		toolBar.add(runFilter);
		toolBar.add(createResetFilter());

	}

	private void initMidPanel() {
		JPanel midPanel = new JPanel();
		midPanel.setMinimumSize(new Dimension(100, 100));
		{// midPanel
			model = new ReportTableModel();

			table = new JTable() {
				public Component prepareRenderer(TableCellRenderer renderer,
						int row, int column) {
					Component c = super.prepareRenderer(renderer, row, column);
					if (c instanceof JComponent) {

						JComponent jc = (JComponent) c;
						String toolTip = "<html><font face=\"monospace\"> "
								+ getValueAt(row, column).toString()
								+ "</font></html>";

						toolTip = toolTip.replace("{R}{N}", "<br>");
						toolTip = toolTip.replace(";", "<br>");
						toolTip = toolTip.replace(" ", "&nbsp;");
						// toolTip = toolTip.replace("{N}", "\n");
						jc.setToolTipText(toolTip);

					}
					return c;
				}
			};

			table.setColumnSelectionAllowed(true);
			sorter = new TableRowSorter<ReportTableModel>(model);
			midPanel.setLayout(new BorderLayout(0, 0));
			table.setModel(model);
			table.setRowSorter(sorter);
			midPanel.add(new JScrollPane(table));

			JPanel rightPanel = new JPanel();
			rightPanel.setLayout(new BorderLayout(0, 0));
			{
				notesModel = new DefaultListModel<String>();
				JList<String> notesList = new JList<String>(notesModel);
				notesList
						.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
				notesList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
				notesList.setVisibleRowCount(-1);
				rightPanel.add(notesList);
			}

			JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
					midPanel, rightPanel);
			splitPane.setResizeWeight(0.75);
			frame.getContentPane().add(splitPane);
		}
	}

	private JButton createLoadLog() {
		JButton clog = new JButton();
		clog.setText("Load Log");
		clog.addActionListener(new ActionListener() {
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
		return clog;
	}

	private void addRecords(File file) {

		String line = "";
		Scanner scan = null;
		try {
			Pattern regex = Pattern
					.compile(
							"(?:\\[(?<date>\\d{0,2}/\\d{0,2}/\\d{2,4})\\])?\n"
									+ // date (##/##/####)
									"(?:\\[(?<time>\\d{0,2}:\\d{0,2}:\\d{0,2})\\])?\n"
									+ // time (##:##:##)
									"(?:\\[(?<pass>true|false|t|f|passed|Passed|failed|Failed|pass|Pass|fail|Fail)\\])?\n"
									+ "(?:\\[[cC]:(?<class>[\\w.\\ \\\\/]*)\\])?\n"
									+ "(?:\\[[mM]:(?<method>[\\w.\\ \\\\/]*)\\])?\n"
									+ "(?:\\[[bB]:(?<branch>[\\w.\\ \\\\/]*)\\])?\n"
									+ "(?:\\[[iI]:(?<input>.*?)])?"
									+ "(?:\\[[oO]:(?<output>.*?)])?"
									+ "(?:\\[[nN]:(?<notes>.*?)])?",
							Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE
									| Pattern.COMMENTS);

			scan = new Scanner(file);
			while ((line = scan.nextLine()) != null && !line.isEmpty()) {
				Matcher regexMatcher = regex.matcher(line);

				if (line.startsWith("~")) {
					notesModel.addElement(line);
					continue;
				}

				boolean matched = !line.startsWith("~") && regexMatcher.find();
				String date = regexMatcher.group("date");
				String time = regexMatcher.group("time");
				String pass = regexMatcher.group("pass");
				String cls = regexMatcher.group("class");
				String method = regexMatcher.group("method");
				String branch = regexMatcher.group("branch");
				String input = regexMatcher.group("input");
				String output = regexMatcher.group("output");
				String notes = regexMatcher.group("notes");

				if (!matched
						|| (date + time + pass + cls + method + branch + input
								+ output + notes).isEmpty())
					continue;

				model.addData(date, time, pass, cls, method, branch, input,
						output, notes);
			}
		} catch (FileNotFoundException ex) {
			System.out.println("File not Found");
		} catch (NoSuchElementException ex) {
		} catch (NullPointerException ex) {
		} catch (IllegalStateException ex) {
		} catch (IllegalArgumentException ex) {
		} finally {
			if (!(scan == null)) {
				scan.close();
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private JComboBox<String> createShowFilter() {
		JComboBox comboBox = new JComboBox(new String[] { "Show:All",
				"Show:Passed", "Show:Failed" });
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox<?> cb = (JComboBox<?>) e.getSource();
				String showValue = (String) cb.getSelectedItem();
				try {
					RowFilter<ReportTableModel, Object> rf = null;
					String textFilter = (showValue.equals("Show:Passed") ? "true"
							: showValue.equals("Show:Failed") ? "false" : "");
					List<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>();

					filters.add(RowFilter.regexFilter(textFilter, 2));
					try {
						rf = RowFilter.orFilter(filters);
					} catch (java.util.regex.PatternSyntaxException e1) {
						return;
					}
					sorter.setRowFilter(rf);
				} catch (Exception ex) {

				}
			}
		});
		return comboBox;
	}

	private JButton createRunFilter() {
		JButton runFilt = new JButton();

		runFilt.setText("Filter");
		runFilt.addActionListener(new ActionListener() {
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
		frame.getRootPane().setDefaultButton(runFilter);
		return runFilt;
	}

	private JButton createResetFilter() {
		JButton btnResetFilter = new JButton();
		btnResetFilter.setText("Reset Filter");
		btnResetFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				filterText.setText("");
				runFilter.doClick();
			}
		});
		return btnResetFilter;
	}
}
