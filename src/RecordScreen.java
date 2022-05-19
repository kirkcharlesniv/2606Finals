import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class RecordScreen extends JFrame {
	private static final long serialVersionUID = -7410681797233581340L;
	
	public PersonList databaseRepository;
	public PersonTableModel personTableModel;
	private static JTable table;
	private String sortedBy = "Name";
	private boolean sortedAscending = true;
	private TableRowSorter<TableModel> columnSorter;

	public RecordScreen() {
		super("List of Records");
		databaseRepository = new PersonList();
		personTableModel = new PersonTableModel(databaseRepository);

		// Testing Purposes
//		System.out.println(databaseRepository.getDatabase());
		databaseRepository.addRecord(new Person("Kirk", LocalDate.of(2003, 05, 23)));
		databaseRepository.addRecord(new Person("Mark Quinto", LocalDate.of(1980, 01, 01)));
		databaseRepository.addRecord(new Person("Mark Balgos", LocalDate.of(1980, 01, 01)));
		databaseRepository.addRecord(new Person("Pao", LocalDate.now()));
//		System.out.println(databaseRepository.getDatabase());
//		databaseRepository.removeRecord("Kirk");
//		System.out.println(databaseRepository.getDatabase());

		JPanel panel = new JPanel();
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(panel);

		// Table and ScrollPane (sp)
		table = new JTable(personTableModel);
		table.setBounds(0, 0, 600, 100);
		table.setAutoCreateRowSorter(true);
		JScrollPane sp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(600, 100));
		sp.getViewport().setBackground(Color.WHITE);
		panel.add(sp);

		JPanel sortPanel = new JPanel();

		JLabel sortLabel = new JLabel("Sort by:");
		sortLabel.setBounds(10, 20, 80, 25);
		sortPanel.add(sortLabel);

		String[] sortTitles = new String[] { "Name", "Birthday", "Age" };
		JComboBox<String> sortList = new JComboBox<String>(sortTitles);
		sortList.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					sortedBy = (String) event.getItem();
					sortColumns();
				}
			}
		});
		sortPanel.add(sortList);

		JRadioButton sortTypeA = new JRadioButton("Ascending", true);
		JRadioButton sortTypeB = new JRadioButton("Descending");
		sortTypeA.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sortedAscending = true;
				sortColumns();
			}
		});
		sortTypeB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sortedAscending = false;
				sortColumns();
			}
		});
		ButtonGroup bg = new ButtonGroup();
		bg.add(sortTypeA);
		bg.add(sortTypeB);
		Box verticalBox = Box.createVerticalBox();
		verticalBox.add(sortTypeA);
		verticalBox.add(sortTypeB);
		sortPanel.add(verticalBox);
		panel.add(sortPanel);

		JPanel buttonsPanel = new JPanel();
		JButton addRecordButton = new JButton("Add a Record");
		addRecordButton.setBounds(150 - 40, 100, 80, 25);
		buttonsPanel.add(addRecordButton);
		addRecordButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				AddRecordScreen addRecordScreenFrame = new AddRecordScreen();
				addRecordScreenFrame.addWindowListener(new java.awt.event.WindowAdapter() {
			        @Override
			        public void windowClosed(java.awt.event.WindowEvent windowEvent) {
			            setVisible(true);
			        }
			    });
			}
		});

		JButton removeRecordButton = new JButton("Remove a Record");
		removeRecordButton.setBounds(150 - 40, 100, 80, 25);
		buttonsPanel.add(removeRecordButton);
		removeRecordButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				RemoveRecordScreen removeRecordScreenFrame = new RemoveRecordScreen(personTableModel, databaseRepository);
				removeRecordScreenFrame.addWindowListener(new java.awt.event.WindowAdapter() {
			        @Override
			        public void windowClosed(java.awt.event.WindowEvent windowEvent) {
			            setVisible(true);
			        }
			    });
			}
		});

		JButton exportButton = new JButton("Export to CSV File");
		exportButton.setBounds(150 - 40, 100, 80, 25);
		buttonsPanel.add(exportButton);
		panel.add(buttonsPanel);
		exportButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exportToCSV();
			}
		});

		columnSorter = new TableRowSorter<>(table.getModel());
		table.setRowSorter(columnSorter);

		TableColumnModel m = table.getColumnModel();
		m.getColumn(1).setCellRenderer(new DateRenderer());

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
		setVisible(true);
	}

	private void sortColumns() {
		var sortOrder = sortedAscending == true ? SortOrder.ASCENDING : SortOrder.DESCENDING;
		int sortColIndex = 0;

		switch (sortedBy) {
		case "Age":
			sortColIndex = 2;
			break;
		case "Name":
			sortColIndex = 0;
			break;
		case "Birthday":
			sortColIndex = 1;
			break;
		}

		List<RowSorter.SortKey> ColSortingKeys = new ArrayList<>();

		ColSortingKeys.add(new RowSorter.SortKey(sortColIndex, sortOrder));
		columnSorter.setSortKeys(ColSortingKeys);
		columnSorter.sort();
	}

	public static boolean exportToCSV() {
		try {
			TableModel model = table.getModel();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMdduuuuHHmmss");
			LocalDateTime localDate = LocalDateTime.now();
			String fileName = dtf.format(localDate);
			FileWriter csv = new FileWriter(new File(fileName + ".csv"));

			for (int i = 0; i < model.getColumnCount(); i++) {
				csv.write(model.getColumnName(i) + ",");
			}

			csv.write("\n");

			for (int i = 0; i < model.getRowCount(); i++) {
				for (int j = 0; j < model.getColumnCount(); j++) {
					csv.write(model.getValueAt(i, j).toString() + ",");
				}
				csv.write("\n");
			}

			csv.close();

			JOptionPane.showMessageDialog(null, "Record is exported.", "Success!", JOptionPane.INFORMATION_MESSAGE);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "There was a problem exporting to CSV format.",
					"Error exporting to CSV.", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
}

