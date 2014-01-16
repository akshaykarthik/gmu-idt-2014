package logviewer_plugin;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import logviewer_plugin.factory.ComparatorFactory;
import logviewer_plugin.tablerow.TableRow;
import logviewer_plugin.tablerow.TableRowHolder;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.part.ViewPart;

public class ViewPart1 extends ViewPart {

	Button openFile;
	Button refresh;
	Table table;
	File file;
	TableRowHolder trh;

	public ViewPart1() {
	}

	@Override
	public void createPartControl(Composite parent) {
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		parent.setLayout(gridLayout);
		trh = new TableRowHolder();

		openFile = new Button(parent, SWT.PUSH);
		openFile.setSize(10, 10);
		openFile.setText("Choose File");
		openFile.addSelectionListener(new FileChooser());

		GridData gd = new GridData();
		gd.grabExcessVerticalSpace = true;
		gd.verticalAlignment = GridData.FILL;
		gd.horizontalAlignment = GridData.FILL;
		table = new Table(parent, SWT.SINGLE);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(gd);

		TableColumn column1 = new TableColumn(table, SWT.LEFT);
		TableColumn column2 = new TableColumn(table, SWT.LEFT);
		TableColumn column3 = new TableColumn(table, SWT.LEFT);
		TableColumn column4 = new TableColumn(table, SWT.LEFT);
		TableColumn column5 = new TableColumn(table, SWT.LEFT);
		TableColumn column6 = new TableColumn(table, SWT.LEFT);
		TableColumn column7 = new TableColumn(table, SWT.LEFT);
		TableColumn column8 = new TableColumn(table, SWT.LEFT);
		TableColumn column9 = new TableColumn(table, SWT.LEFT);

		column1.setWidth(100);
		column1.setText("Date");
		column2.setWidth(100);
		column2.setText("Time");
		column3.setWidth(100);
		column3.setText("Passed");

		column4.setWidth(100);
		column4.setText("Class");


		Listener sortListener4 = new Listener() { 
			public void handleEvent(Event e) { 
				TableColumn column = (TableColumn) e.widget;
				Collections.sort(trh.getRowsList(), ComparatorFactory.makeComparator(3));
				table.setSortColumn(column); 
				updateTable(); 
			}};

			column4.addListener(SWT.Selection, sortListener4);

			column5.setWidth(100);
			column5.setText("Method");
			column6.setWidth(100);
			column6.setText("Branches");
			column7.setWidth(100);
			column7.setText("Input");
			column8.setWidth(100);
			column8.setText("Output");
			column9.setWidth(100);
			column9.setText("Notes");

	}

	@Override
	public void setFocus() {
	}

	public class FileChooser implements SelectionListener {

		@Override
		public void widgetSelected(SelectionEvent e) {
			FileDialog dlg = new FileDialog(openFile.getShell(), SWT.OPEN);
			dlg.setText("Open");
			String path = dlg.open();
			if (path == null)
				return;
			file = new File(path);
			addRecords(file);
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
		}

	}

	private void addRecords(File file) {
		table.clearAll();
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
									+ "(?:\\[[iI]:(?<input>[\\w.\\ \\\\/]*)\\])?\n"
									+ "(?:\\[[oO]:(?<output>[\\w.\\ \\\\/]*)\\])?\n"
									+ "(?:\\[[nN]:(?<notes>[\\w\\s.\\\\/]*)\\])?",
									Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE
									| Pattern.COMMENTS);

			scan = new Scanner(file);
			String[] data;
			System.out.println("Getting data from file");
			while ((line = scan.nextLine()) != null && !line.isEmpty()) {
				data = new String[9];
				Matcher regexMatcher = regex.matcher(line);
				boolean matched = regexMatcher.find();
				data[0] = regexMatcher.group("date");
				data[1] = regexMatcher.group("time");
				data[2] = regexMatcher.group("pass");
				data[3] = regexMatcher.group("class");
				data[4] = regexMatcher.group("method");
				data[5] = regexMatcher.group("branch");
				data[6] = regexMatcher.group("input");
				data[7] = regexMatcher.group("output");
				data[8] = regexMatcher.group("notes");

				if (!matched|| (data[0] + data[1] + data[2] + data[3] + data[4]+ data[5] + data[6] 
						+ data[7] + data[8]).isEmpty())
					continue;
				addData(data);
			}

		} catch (FileNotFoundException ex) {
			System.out.println("File not Found");
		} catch (NoSuchElementException ex) {
		} catch (NullPointerException ex) {
		} finally {
			if (!(scan == null)) {
				scan.close();
			}
		}
		updateTable();
	}

	private void addData(String[] data) {
		TableRow tr = new TableRow(data);
		trh.add(tr);
	}

	private void updateTable() {
		table.removeAll();
		for (TableRow row : trh.getRowsList()) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(row.getData());
		}
	}
}