package logviewer_plugin;

public class ReportLine {
	final String[] columnNames = { "Date", "Time", "Passed", "Class", "Method",
			"Branches", "Input", "Output", "Notes"};
	public String Date;
	public String Time;
	public Boolean Passed;
	public String Class;
	public String Method;
	public String branch;
	public String Input;
	public String Output;
	public String Notes;
	
	
	
	public ReportLine(String date, String time, Boolean passed, String class1,
			String method, String branch, String input,
			String output, String notes) {
		Date = date;
		Time = time;
		Passed = passed;
		Class = class1;
		Method = method;
		this.branch = branch;
		Input = input;
		Output = output;
		Notes = notes;
	}
	
	public ReportLine() {
		this("", "", false, "", "", "", "", "", "");
	}

	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}
	public Boolean getPassed() {
		return Passed;
	}
	public void setPassed(Boolean passed) {
		Passed = passed;
	}
	public String getMethodClass() {
		return Class;
	}
	public void setMethodClass(String class1) {
		Class = class1;
	}
	public String getMethod() {
		return Method;
	}
	public void setMethod(String method) {
		Method = method;
	}

	public String getBranches() {
		return branch;
	}
	public void setBranches(String branches) {
		this.branch = branches;
	}
	public String getInput() {
		return Input;
	}
	public void setInput(String input) {
		Input = input;
	}
	public String getOutput() {
		return Output;
	}
	public void setOutput(String output) {
		Output = output;
	}
	public String getNotes() {
		return Notes;
	}
	public void setNotes(String notes) {
		Notes = notes;
	}
	
}
