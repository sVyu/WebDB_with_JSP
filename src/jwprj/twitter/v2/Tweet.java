package jwprj.twitter.v2;

// Entity Class, DO(Data Object)
// tweet 클래스의 구조는 사실상 DB 테이블의 구조와 같다(mapping)
public class Tweet {
	// (중요)변수명은 field 명과 같게 만들어야 한다
	// 서로 일치해야 자동으로 처리해주는 부분이 있다 (spring의 경우 등)
	
	// 원시데이터보다 클래스의 사용이 권장
	// 그래서 int 보다 Integer를 사용
	private Integer id;
	
	private String name;
	private String email;
	private String date;
	private String msg;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
