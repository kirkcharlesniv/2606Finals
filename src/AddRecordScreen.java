import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class AddRecordScreen extends JFrame {
	private static final long serialVersionUID = 8329989768106851013L;

	public AddRecordScreen() {
		super("Add Records");
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		setSize(500, 250);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		add(panel);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
		setVisible(true);
	}
}
