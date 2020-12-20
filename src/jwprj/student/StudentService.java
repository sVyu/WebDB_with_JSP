package jwprj.student;
import java.util.Optional;

public class StudentService {
	public Optional<Student> getStudent(int id){
		switch (id) {
		case 1: return Optional.of(new Student(1, "홍길동", "컴퓨터공학과"));
		case 2: return Optional.of(new Student(2, "김사랑", "전자공학과"));
		case 3: return Optional.of(new Student(3, "강동명", "건축학과"));
		default:
			return Optional.empty();
			// 1,2,3 이외의 아이디는 없는 것으로 간주
			// 데이터가 없는 경우 오류 처리를 위해 리턴 타입을 Optional로 한다
		}
	}
}
