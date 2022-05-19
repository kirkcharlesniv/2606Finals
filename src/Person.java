import java.time.LocalDate;
import java.time.Period;

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

	public LocalDate getBirthDay() {
		return birthDay;
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

	private static int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        if ((birthDate != null) && (currentDate != null)) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return 0;
        }
    }
}
