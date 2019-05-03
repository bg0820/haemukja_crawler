package Crawler;

import java.io.IOException;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HaemukjaCrawler {
	private static final String RECIPE_LIST_URL = "https://haemukja.com/recipes?utf8=%E2%9C%93";


	public int getRecipeRecordCnt() throws IOException {

		Response resp = getResponse(RECIPE_LIST_URL);

		if (resp.statusCode() == 200) {
			Document doc = resp.parse();

			String cnt = doc.selectFirst("div[class='tit_area']").selectFirst("strong").text();

			return Integer.parseInt(cnt.replaceAll(",", ""));
		}

		return -1;
	}

	private Response getResponse(String url) throws IOException {
		return Jsoup.connect(url).method(Method.GET).header("Cache-Control", "no-cache")
				.header("Connection", "keep-alive").header("Host", "haemukja.com")
				.header("Referer", "https://haemukja.com/")
				.userAgent(
						"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36")
				.execute();
	}
}
