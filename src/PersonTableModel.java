import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class PersonTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -4869513310623463266L;
	
	private String[] columnNames = {"Name","Birthday","Age"};
	private ArrayList<Person> database;
	
	public PersonTableModel(PersonList list) {
		this.database = list.getDatabase();
	}
	

	@Override
	public int getRowCount() {
		if(database == null) return 0;
		return database.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int row, int col) {
		 Object temp = null;
	      if (col == 0) {
	         temp = database.get(row).getName();
	      } else if (col == 1) {
	         temp = database.get(row).getBirthDay();
	      } else if (col == 2) {
	         temp = Integer.toString(database.get(row).getAge());
	      }
	      return temp;
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}
}
