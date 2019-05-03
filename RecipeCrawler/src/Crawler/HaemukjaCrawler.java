package Crawler;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HaemukjaCrawler {
	private static final String ROOT_URL = "https://haemukja.com";
	private static final String RECIPE_LIST_URL = ROOT_URL + "/recipes?utf8=%E2%9C%93";


	public ArrayList<String> getRecipeList(int page) throws IOException {
		Response resp = getResponse(RECIPE_LIST_URL + "&page=" + page);
		ArrayList<String> resultList = new ArrayList<String>();

		if (resp.statusCode() == 200) {
			Document doc = resp.parse();

			Elements liList = doc.selectFirst("ul[class='lst_recipe']").select("li");
			for (int i = 0; i < liList.size(); i++) {
				resultList.add(liList.get(i).selectFirst("a").attr("href"));
			}
		}

		return resultList;
	}

	// return ceil(value / 12(한 페이지 레코드 개수)) = page 개수
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
