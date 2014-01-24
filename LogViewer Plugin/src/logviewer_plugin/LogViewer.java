package logviewer_plugin;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
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
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

import java.awt.FlowLayout;

import javax.swing.SpringLayout;


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
	public JPanel mainPanel;
	private JButton runFilter;
	private JTextField filterText;

	private File file;

	private JTable table;
	private TableRowSorter<ReportTableModel> sorter;
	private ReportTableModel model;
	private DefaultListModel<String> notesModel;

	private JTree coverageTree;
	private DefaultTreeModel coverageModel;
	private DefaultMutableTreeNode defaultNode;
	private SpringLayout sl_toolBar;
	private JPanel toolBar;

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
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		frame = new JFrame();
		frame.pack();
		frame.setTitle("GMU LogViewer");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		frame.getContentPane().add(mainPanel = new JPanel());
		
		createMidPanelTable();

		notesModel = new DefaultListModel<String>();
		JList<String> notesList = new JList<String>(notesModel);
		notesList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		notesList.setVisibleRowCount(-1);

		defaultNode = new DefaultMutableTreeNode("Coverage");
		coverageModel = new DefaultTreeModel(defaultNode);
		coverageTree = new JTree(coverageModel);
		coverageTree.setEditable(false);
		coverageTree.setShowsRootHandles(false);

		JPanel coveragePanel = new JPanel();
		coveragePanel.setLayout(new BorderLayout(0, 0));
		coveragePanel.add(coverageTree, BorderLayout.CENTER);
		coveragePanel.add(createCoverageExportButton(), BorderLayout.NORTH);

		JTabbedPane commentsAndCoverage = new JTabbedPane(JTabbedPane.TOP);
		commentsAndCoverage.setPreferredSize(new Dimension(400, 500));
		commentsAndCoverage.addTab("Comments", new JScrollPane(notesList));
		commentsAndCoverage.addTab("Code Coverage", coveragePanel);
		mainPanel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(500, 500));
		JSplitPane tableAndRight = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				scrollPane, commentsAndCoverage);
		// tableAndRight.setPreferredSize(new Dimension(900, 500));
		tableAndRight.setResizeWeight(0.75);
		mainPanel.add(tableAndRight, BorderLayout.CENTER);
		frame.pack();

		toolBar = new JPanel();
		mainPanel.add(toolBar, BorderLayout.SOUTH);

		toolBar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		toolBar.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		toolBar.add(createToolBarLoadLog());
		toolBar.add(createToolBarShowFilter());
		toolBar.add(filterText = createToolBarFilterText());
		toolBar.add(runFilter = createToolBarRunFilter());
		toolBar.add(createToolBarResetFilter());
	}

	private void createMidPanelTable() {
		model = new ReportTableModel();
		// create table;
		table = new JTable() {
			private static final long serialVersionUID = -3220594535640547297L;

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

		sorter = new TableRowSorter<ReportTableModel>(model);
		table.setColumnSelectionAllowed(true);
		table.setModel(model);
		table.setRowSorter(sorter);
	}

	private JButton createCoverageExportButton() {
		JButton exportButton = new JButton("Export Coverage");
		exportButton.addActionListener(new ActionListener() {
			@SuppressWarnings("rawtypes")
			public void actionPerformed(ActionEvent arg0) {
				String output = "";
				Enumeration classes = defaultNode.children();
				while (classes.hasMoreElements()) {
					DefaultMutableTreeNode tclass = (DefaultMutableTreeNode) classes
							.nextElement();
					output += tclass.getUserObject().toString()
							+ System.lineSeparator();
					Enumeration methods = tclass.children();
					while (methods.hasMoreElements()) {
						DefaultMutableTreeNode tmethod = (DefaultMutableTreeNode) methods
								.nextElement();
						output += "\t" + tmethod.getUserObject().toString()
								+ System.lineSeparator();
					}
				}
				System.out.println(output);

				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnVal = chooser.showOpenDialog(null);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File savefile = chooser.getSelectedFile();
					try {
						FileWriter fw = new FileWriter(savefile);
						fw.write(output);
						fw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		});
		return exportButton;
	}

	private JButton createToolBarLoadLog() {
		JButton clog = new JButton();
		clog.setText("Load Log");
		clog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnVal = chooser.showOpenDialog(null);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					file = chooser.getSelectedFile();
					addRecords(file);
					performCoverage(model);
				}
			}
		});
		return clog;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private JComboBox<String> createToolBarShowFilter() {
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

	private JTextField createToolBarFilterText() {
		JTextField ifil = new JTextField();
		ifil.setAlignmentX(Component.RIGHT_ALIGNMENT);
		ifil.setPreferredSize(new Dimension(400, 20));
		ifil.setMinimumSize(new Dimension(400, 20));
		ifil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				runFilter.doClick();
			}
		});

		return ifil;
	}

	private JButton createToolBarRunFilter() {
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

	private JButton createToolBarResetFilter() {
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

	private void performCoverage(ReportTableModel im) {
		int records = im.getRowCount();

		defaultNode.removeAllChildren();
		coverageModel.reload();
		HashMap<String, HashMap<String, HashSet<String>>> caseMap = new HashMap<String, HashMap<String, HashSet<String>>>();
		for (int test = 0; test < records; test++) {
			String tclass = (String) im.getValueAt(test, 3);
			String tmethod = (String) im.getValueAt(test, 4);
			String tbranc = (String) im.getValueAt(test, 5);
			if (!caseMap.containsKey(tclass)) {
				caseMap.put(tclass, new HashMap<String, HashSet<String>>());
			}
			if (!caseMap.get(tclass).containsKey(tmethod)) {
				caseMap.get(tclass).put(tmethod, new HashSet<String>());
			}
			caseMap.get(tclass).get(tmethod).add(tbranc);
		}

		for (String cls : caseMap.keySet()) {
			// System.out.println(cls);
			MutableTreeNode clsn = new DefaultMutableTreeNode(cls);
			HashMap<String, HashSet<String>> clsmap = caseMap.get(cls);

			float brmax_base = 0f;
			float compare_base = 0f;
			for (String mtd : clsmap.keySet()) {
				// System.out.println("\t" + mtd);
				boolean first = true;
				HashSet<String> branc = clsmap.get(mtd);
				float brmax = 0f;
				float compare = 0f;
				for (String string : branc) {
					if (first) {
						brmax_base += Float.parseFloat((string.split("/"))[1]);
						first = false;
					}
					brmax = Float.parseFloat((string.split("/"))[1]);
					compare++;
					compare_base++;
					// System.out.println("\t\t" + string);
				}
				MutableTreeNode mtdn = new DefaultMutableTreeNode(
						String.format("[%.4g %%] %s", (compare / brmax * 100),
								mtd));

				clsn.insert(mtdn, clsn.getChildCount());
				// System.out.println("\t\t\t " +compare/brmax);
			}
			clsn.setUserObject(String.format("[%.4g %%] %s", (compare_base
					/ brmax_base * 100), cls));
			coverageModel.insertNodeInto(clsn, defaultNode,
					coverageModel.getChildCount(defaultNode));
		}
		expandAllNodes(coverageTree, 0, coverageTree.getRowCount());
	}

	private void expandAllNodes(JTree tree, int startingIndex, int rowCount) {
		for (int i = startingIndex; i < rowCount; ++i) {
			tree.expandRow(i);
		}

		if (tree.getRowCount() != rowCount) {
			expandAllNodes(tree, rowCount, tree.getRowCount());
		}
	}
}
