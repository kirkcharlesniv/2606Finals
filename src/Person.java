import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Person {
	private String name;
	private LocalDate birthDay;
	private int age;
	
	public Person(String name, LocalDate birthDay) {
		this.name = name;
		this.birthDay = birthDay;
		this.age = calculateAge(birthDay, LocalDate.now());
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthDay() {
		return birthDay.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
	}

	public void setBirthDay(LocalDate birthDay) {
		this.birthDay = birthDay;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	public static int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        if ((birthDate != null) && (currentDate != null)) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return 0;
        }
    }
}
