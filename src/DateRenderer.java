import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.table.DefaultTableCellRenderer;

public class DateRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = -503835610267293582L;
	
	private LocalDate dateValue;
	private String valueToString = "";

	@Override
	public void setValue(Object value) {
	    if ((value != null)) {
	        String stringFormat = value.toString();
            dateValue = LocalDate.parse(stringFormat);
	        valueToString = dateValue.format(DateTimeFormatter.ofPattern("MM/dd/uuuu"));
	        value = valueToString;
	    }
	    super.setValue(value);
	}
}
