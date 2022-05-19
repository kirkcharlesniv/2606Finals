import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class RecordScreen extends JFrame {
	public PersonList databaseRepository;
	public PersonTableModel personTableModel;
	private JTable table;
	
	public RecordScreen() {
		super("List of Records");
		databaseRepository = new PersonList();
		personTableModel = new PersonTableModel(databaseRepository);
		
		// Testing Purposes
//		System.out.println(databaseRepository.getDatabase());
//		databaseRepository.addRecord(new Person("Kirk", LocalDate.of(2003, 05, 23)));
//		System.out.println(databaseRepository.getDatabase());
//		databaseRepository.removeRecord("Kirk");
//		System.out.println(databaseRepository.getDatabase());
		
		JPanel panel = new JPanel();
		setSize(400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		add(panel);
		panel.setLayout(null);

        table = new JTable(personTableModel);
        table.setBounds(0, 0,400, 100);
        JScrollPane sp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        sp.setPreferredSize(new Dimension(400, 100));
        sp.getViewport().setBackground(Color.WHITE);
        
        add(sp);
        
        JButton addRecordButton = new JButton("Add a Record");
        addRecordButton.setBounds(150 - 40, 100, 80, 25);
//        addRecordButton.addActionListener(new LoginScreen());
		panel.add(addRecordButton);


		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-getSize().width/2, dim.height/2-getSize().height/2);		
		setVisible(true);
	}
}
