package pop3;

public class Student {

	String name;
	int id;
	int age;
	double score;

	public Student(String name, int id, int age, double score) {
		this.name = name;
		this.id = id;
		this.age = age;
		this.score = score;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getYearOld() {
		return this.age;
	}

	public void setYearOld(int yearOld) {
		this.age = yearOld;
	}

	public double getScore() {
		return this.score;
	}

	public void setScore(double score) {
		this.score = score;
	}

}
