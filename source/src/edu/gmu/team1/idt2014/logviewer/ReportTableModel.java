package edu.gmu.team1.idt2014.logviewer;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class ReportTableModel extends AbstractTableModel {
	final String[] columnNames = { "Date", "Time", "Passed", "Class", "Method",
			"Input", "Output", "Expected Output" };

	private ArrayList<String[]> rowData = new ArrayList<String[]>();
	
	public ReportTableModel(){
		
	}
	
	public void addData(String date, String time, String passed, String iclass, String method, String input, String output, String expectedOutput){
		rowData.add(new String[]{date, time, passed, iclass, method, input, output, expectedOutput});
		this.fireTableRowsInserted(0, rowData.size());
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
		return rowData.get(row)[col];
	}

	public boolean isCellEditable(int row, int col) {
		return false;
	}

}
