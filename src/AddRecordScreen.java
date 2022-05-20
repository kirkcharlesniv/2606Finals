import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AddRecordScreen extends JFrame {
	private static final long serialVersionUID = -682845852218904085L;
	private PersonTableModel personTableModel;
	private PersonList personList;

	public AddRecordScreen(PersonTableModel model, PersonList list) {
		super("Add Records");
		this.personList = list;
		this.personTableModel = model;

		// Generate Months, Dates, and Years
		List<Integer> listOfMonthsInt = IntStream.range(0, 12).boxed().collect(Collectors.toList());

		List<String> listOfMonthsMapped = listOfMonthsInt.stream().map(item -> {
			return new DateFormatSymbols().getMonths()[item];
		}).collect(Collectors.toList());

		List<String> datesStrList = new ArrayList<String>();
		for (int i = 1; i <= 31; i++) {
			datesStrList.add(Integer.toString(i));
		}
		String[] dates = datesStrList.toArray(new String[datesStrList.size()]);

		List<String> yearStrList = new ArrayList<String>();
		for (int i = 1900; i <= Year.now().getValue(); i++) {
			yearStrList.add(Integer.toString(i));
		}
		String[] years = yearStrList.toArray(new String[yearStrList.size()]);

		String[] strArrayMonths = new String[listOfMonthsMapped.size()];
		listOfMonthsMapped.toArray(strArrayMonths);
		// End Generation

		JPanel panel = new JPanel();
		panel.setLayout(null);
		setSize(600, 250);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		add(panel);

		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setBounds(100, 35, 370, 25);
		panel.add(nameLabel);

		JTextField nameTextField = new JTextField(20);
		nameTextField.setBounds(100 + 80, 35, 300, 25);
		panel.add(nameTextField);

		JLabel birthdayLabel = new JLabel("Birthday:");
		birthdayLabel.setBounds(100, 75, 80, 25);
		panel.add(birthdayLabel);

		JComboBox<String> monthComboBox = new JComboBox<String>(strArrayMonths);
		monthComboBox.setBounds(100 + 80, 75, 100, 25);
		panel.add(monthComboBox);

		JLabel monthLabel = new JLabel("mm");
		monthLabel.setBounds(100 + 80 + 35, 100, 100, 25);
		panel.add(monthLabel);

		JComboBox<String> dateComboBox = new JComboBox<String>(dates);
		dateComboBox.setBounds(100 + 80 + 100, 75, 100, 25);
		panel.add(dateComboBox);

		JLabel dateLabel = new JLabel("dd");
		dateLabel.setBounds(100 + 80 + 35 + 100, 100, 100, 25);
		panel.add(dateLabel);

		JComboBox<String> yearComboBox = new JComboBox<String>(years);
		yearComboBox.setBounds(100 + 80 + 100 + 100, 75, 100, 25);
		panel.add(yearComboBox);

		monthComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBoxChanged(monthComboBox.getSelectedItem().toString(), dateComboBox.getSelectedItem().toString(),
						yearComboBox.getSelectedItem().toString());
			}
		});
		dateComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBoxChanged(monthComboBox.getSelectedItem().toString(), dateComboBox.getSelectedItem().toString(),
						yearComboBox.getSelectedItem().toString());
			}
		});
		yearComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBoxChanged(monthComboBox.getSelectedItem().toString(), dateComboBox.getSelectedItem().toString(),
						yearComboBox.getSelectedItem().toString());
			}
		});

		JLabel yearLabel = new JLabel("yyyy");
		yearLabel.setBounds(100 + 80 + 30 + 100 + 100, 100, 100, 25);
		panel.add(yearLabel);

		JButton addRecordBackButton = new JButton("Save and Go Back");
		addRecordBackButton.setBounds(80, 150, 200, 25);
		addRecordBackButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					addRecord(monthComboBox.getSelectedItem().toString(), dateComboBox.getSelectedItem().toString(),
							yearComboBox.getSelectedItem().toString(), nameTextField.getText(), true);
					nameTextField.setText("");
					monthComboBox.setSelectedIndex(0);
					dateComboBox.setSelectedIndex(0);
					yearComboBox.setSelectedIndex(0);
				} catch (ParseException e1) {
					// Do nothing
				}
			}
		});
		panel.add(addRecordBackButton);

		JButton addRecordButton = new JButton("Save and Add Another");
		addRecordButton.setBounds(200 + 80, 150, 150, 25);
		addRecordButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					addRecord(monthComboBox.getSelectedItem().toString(), dateComboBox.getSelectedItem().toString(),
							yearComboBox.getSelectedItem().toString(), nameTextField.getText(), false);
					nameTextField.setText("");
					monthComboBox.setSelectedIndex(0);
					dateComboBox.setSelectedIndex(0);
					yearComboBox.setSelectedIndex(0);
				} catch (ParseException e1) {
					// Do nothing
				}
			}
		});
		panel.add(addRecordButton);

		JButton backButton = new JButton("Back");
		backButton.setBounds(200 + 80 + 150, 150, 100, 25);
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

	private void addRecord(String month, String date, String year, String name, boolean shouldGoBack)
			throws ParseException {
		boolean isValid = checkIfInputIsValid(month, date, year);
		if (!isValid)
			return;

		if (name.isBlank()) {
			JOptionPane.showMessageDialog(null, "Name is required.", "Please fill up all the forms!", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		Date birthDate = buildLocalDateFromString(buildStringFromInputs(month, date, year));
		LocalDate birthDateParsed = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		personList.addRecord(new Person(name, birthDateParsed));
		personTableModel.fireTableDataChanged();
		
		if(shouldGoBack) {
			super.dispose();
		}
	}

	private static boolean checkIfInputIsValid(String month, String date, String year) {
		try {
			buildLocalDateFromString(buildStringFromInputs(month, date, year));
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "An invalid date or future date is entered.", "Invalid Date",
					JOptionPane.ERROR_MESSAGE);

			return false;
		}
	}

	private static void comboBoxChanged(String month, String date, String year) {
		checkIfInputIsValid(month, date, year);
	}

	private static String buildStringFromInputs(String month, String date, String year) {
		return month + " " + date + ", " + year;
	}

	private static Date buildLocalDateFromString(String input) throws ParseException {
		DateFormat sdf = new SimpleDateFormat("MMMM d, yyyy");
		sdf.setLenient(false);
		Date res = sdf.parse(input);
		if (res.after(new Date())) {
			throw new ParseException("Date is in the future.", 0);
		}
		return res;
	}
}
