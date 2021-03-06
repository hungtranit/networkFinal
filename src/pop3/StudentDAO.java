package pop3;

import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

	List<Student> listStudent;

	public StudentDAO() {
		listStudent = new ArrayList<>();
	}

	public void createStudentList() {
		listStudent.add(new Student("hung", 123, 20, 7.0));
		listStudent.add(new Student("bong", 124, 19, 6.0));
		listStudent.add(new Student("minh", 125, 18, 5.0));
		listStudent.add(new Student("long", 126, 21, 8.0));
		listStudent.add(new Student("nguyen", 127, 22, 4.0));
	}

	public List<Student> findByName(String name) {
		List<Student> resultList = new ArrayList<>();
		for (Student student : listStudent) {
			if (name.equalsIgnoreCase(student.getName()))
				resultList.add(student);
		}
		return resultList;
	}

	public List<Student> findByAge(int age) {
		List<Student> resultList = new ArrayList<>();
		for (Student student : listStudent) {
			if (age == student.getYearOld())
				resultList.add(student);
		}
		return resultList;
	}

	public List<Student> findByScore(double score) {
		List<Student> resultList = new ArrayList<>();
		for (Student student : listStudent) {
			if (score == student.getScore())
				resultList.add(student);
		}
		return resultList;
	}

}
