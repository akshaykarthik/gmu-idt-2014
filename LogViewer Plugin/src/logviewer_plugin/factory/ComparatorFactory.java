package logviewer_plugin.factory;

import java.util.Comparator;

import logviewer_plugin.tablerow.TableRow;

public class ComparatorFactory {

	public static Comparator<TableRow> makeComparator(int index){
		final int x = index;
		return new Comparator<TableRow>(){
			@Override
			public int compare(TableRow o1, TableRow o2) { 
				return o1.getData()[x].compareTo(o2.getData()[x]); 
			}
		};
	}
}
