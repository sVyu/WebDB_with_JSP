package jwprj.news;

public class News {
	private String aid;
	private String title;
	private String date;
	private String img;
	private String content;
	
	public News() {}
	public News(String aid, String title, String date, String img, String content) {
		this.aid = aid;
		this.title = title;
		this.date = date;
		this.img = img;
		this.content = content;
	}
	
	// getter, setter 구현 필요
	// 대소문자 구분 주의
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void SetTitle(String title) {
		this.title = title;
	}
	
	public String getDate() {
		return date;
	}
	public void SetDate(String date) {
		this.date = date;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
