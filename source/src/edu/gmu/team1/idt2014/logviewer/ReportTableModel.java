package edu.gmu.team1.idt2014.logviewer;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class ReportTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	final String[] columnNames = { "Date",// 0
			"Time",// 1
			"Passed",// 2
			"Class",// 3
			"Method",// 4
			"Branches",// 5
			"Input",// 6
			"Output",// 7
			"Notes" };// 8
	private ArrayList<ReportLine> rowData = new ArrayList<ReportLine>();

	public ReportTableModel() {

	}

	public void addData(String date, String time, String passed, String iclass,
			String method, String branches, String input, String output, String notes) {
		//<pass>true|false|t|f|passed|Passed|failed|Failed|pass|Pass|fail|Fail
		boolean ilpassed = passed.equalsIgnoreCase("pass") || passed.equalsIgnoreCase("passed") || passed.equalsIgnoreCase("true") || passed.equalsIgnoreCase("t");
		rowData.add(new ReportLine(date, time, ilpassed, iclass, method,
				branches, input, output, notes ));
		this.fireTableRowsInserted(0, rowData.size() - 1);
	}

	public String getColumnName(int col) {
		return columnNames[col].toString();
	}

	public int getRowCount() {
		return rowData.size();
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public Object getValueAt(int row, int col) {
		switch (col) {
		case 0:	return rowData.get(row).getDate();
		case 1:	return rowData.get(row).getTime();
		case 2:	return rowData.get(row).getPassed();
		case 3:	return rowData.get(row).getMethodClass();
		case 4:	return rowData.get(row).getMethod();
		case 5:	return rowData.get(row).getBranches();
		case 6:	return rowData.get(row).getInput();
		case 7:	return rowData.get(row).getOutput();
		case 8:	return rowData.get(row).getNotes();
		default:
			return null;
		}
	}

	public boolean isCellEditable(int row, int col) {
		return false;
	}

}
