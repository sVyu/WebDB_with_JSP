package jwprj.news;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NewsService {

	public List<News> getNewsList() {
		List<News> newslist = new ArrayList<News>();
		newslist.add(new News("1001", "신규 확진 97명", "2020-10-12", null, null));
		newslist.add(new News("1002", "인천시, 인천공항서 버는 것보다 더 쓴다", "2020-10-12", null, null));
		return newslist;
	}

	public Optional<News> getNews(String aid) {
		switch (aid) {
		case "1001": return Optional.of(new News(
				"1001",
				"신규 확진 97명, 거리두기 1단계 첫날 100명 육박",
				"2020-10-12",
				"https://img.sbs.co.kr/newimg/news/20201009/201478580_1280.jpg",
				"'사회적 거리두기' 1단계 전환 첫날인 오늘(12일) 국내 코로나19 신규 확진자는 100명에 육박했습니다."));
		case "1002": return Optional.of(new News(
				"1002",
				"인천시, 인천공항서 버는 것보다 더 쓴다",
				"2020-10-12",
				"http://www.kyeongin.com/mnt/file/202010/20201011010001721_1.jpg",
				"도로보수·소음피해 지원 등 사업 2018년 290억 등 매년 '세수 적자'"));
		default:
			return Optional.empty();
		}
	}
}
