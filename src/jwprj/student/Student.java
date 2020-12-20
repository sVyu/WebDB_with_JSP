package jwprj.student;

// 학생정보 엔티티 클래스(DO, Data Object)
public class Student {
	private int id;
	private String name;
	private String dept;
	
	public Student() {}
	public Student(int id, String name, String dept) {
		this.id = id;
		this.name = name;
		this.dept = dept;
	}
	
	// getter, setter 는 (편의상) 생략 ! >> 구현 ,,
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDept() {
		return dept;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDept(String dept) {
		this.dept = dept;
	}
}
