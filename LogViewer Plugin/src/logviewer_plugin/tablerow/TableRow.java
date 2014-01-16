package logviewer_plugin.tablerow;

public class TableRow {
	String[] data;
	public TableRow(String[]data){
		this.data = data;
	}
	public String[] getData(){
		return data;
	}
	public String toString(){
		String s="";
		for(String d: data){
			s+=(" "+d);
		}
		return s;
	}
}
