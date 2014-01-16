package logviewer_plugin.tablerow;

import java.util.ArrayList;

public class TableRowHolder {
private ArrayList<TableRow> rows;

public TableRowHolder(){
	System.out.println("Holder Created");
	rows = new ArrayList<TableRow>();
}

public void add(TableRow a){
	System.out.println("Adding To the list "+a);
	boolean b = rows.add(a);
	System.out.println(b);
	
}

public ArrayList<TableRow> getRowsList(){
	return (ArrayList<TableRow>) rows;
}

public void print(){
	for(TableRow row:rows){
		System.out.println(row);
	}
}
}
