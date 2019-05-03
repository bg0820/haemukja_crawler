package Crawler;

import org.jsoup.Jsoup;
import java.io.IOException;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;

public class HaemukjaCrawler {
	private static final String RECIPE_LIST_URL = "https://haemukja.com/recipes?utf8=%E2%9C%93";


	public void getRecordCnt() {

	}

	private Response getResponse() throws IOException {
		return Jsoup.connect(RECIPE_LIST_URL).method(Method.GET).header("Cache-Control", "no-cache")
				.header("Connection", "keep-alive").header("Host", "haemukja.com")
				.header("Referer", "https://haemukja.com/")
				.userAgent(
						"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36")
				.execute();
	}
}
