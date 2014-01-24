package logviewer_plugin;
import java.awt.Frame;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;


public class ViewPart1 extends ViewPart {

	public ViewPart1() {
	}

	@Override
	public void createPartControl(Composite parent) {
		Composite c = new Composite(parent, SWT.EMBEDDED);
		Frame window = SWT_AWT.new_Frame(c);
		LogViewer lv = new LogViewer();
		window.add(lv.mainPanel);
	}
	
	@Override
	public void setFocus() {}
}