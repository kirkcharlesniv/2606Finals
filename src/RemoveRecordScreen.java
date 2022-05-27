import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RemoveRecordScreen extends JFrame {
	private static JLabel nameLabel;
	private static JTextField nameTextField;
	private PersonTableModel personTableModel;
	private PersonList personList;
	
	public RemoveRecordScreen(PersonTableModel model, PersonList list) {
		super("Remove Record");
		this.personList = list;
		this.personTableModel = model;
	
		JPanel panel = new JPanel();
		panel.setLayout(null);
		setSize(500, 250);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		add(panel);
		
		nameLabel = new JLabel("Name:");
		nameLabel.setBounds(125, 50, 80, 25);
		panel.add(nameLabel);
		
		nameTextField = new JTextField(20);
		nameTextField.setBounds(125 + 80, 50, 165, 25);
		panel.add(nameTextField);
		
		JButton removeRecordButton = new JButton("Remove and Go Back");
		removeRecordButton.setBounds(20, 150, 200, 25);
		removeRecordButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeRecord(nameTextField.getText(), true);
			}
		});
		panel.add(removeRecordButton);
		
		JButton removeAnotherButton = new JButton("Remove Another");
		removeAnotherButton.setBounds(200 + 20, 150, 150, 25);
		removeAnotherButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeRecord(nameTextField.getText(), false);
			}
		});
		panel.add(removeAnotherButton);
		
		JButton backButton = new JButton("Back");
		backButton.setBounds(200 + 20 + 150, 150, 100, 25);
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel.add(backButton);
	
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
		setVisible(true);
	}
	
	private void removeRecord(String value, boolean shouldGoBack) {
		try {
			personList.removeRecord(value);
			nameTextField.setText("");
			nameTextField.requestFocus();
			nameTextField.setCaretPosition(0);
			personTableModel.fireTableDataChanged();
			
			if(shouldGoBack) {
				super.dispose();
			}
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Name not found", "Warning Screen", JOptionPane.WARNING_MESSAGE);
		}
	}
}
