import java.util.ArrayList;
import java.util.NoSuchElementException;

public class PersonList {
	private ArrayList<Person> database;
	
	public PersonList() {
		database = new ArrayList<Person>();
	}
	
	public ArrayList<Person> getDatabase() {
		return database;
	}
	
	public void addRecord(Person person) {
		database.add(person);
	}
	
	public Person findRecordByName(String name) {
		for (Person person : database) {
	        if (person.getName().toLowerCase().equals(name.toLowerCase())) {
	            return person;
	        }
	    }
		
	    return null;
	}
	
	public void removeRecord(String name) throws NoSuchElementException {
		Person record = findRecordByName(name);
		
		if(record == null) throw new NoSuchElementException();
		
		if(!database.remove(record)) {
			 throw new NoSuchElementException();
		}
	}
}
