package Crawler;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import Model.Ingredient;
import Model.Nutrition;
import Model.Recipe;

public class HaemukjaCrawler {
	private static final String ROOT_URL = "https://haemukja.com";
	private static final String RECIPE_LIST_URL = ROOT_URL + "/recipes?utf8=%E2%9C%93";


	public Recipe getRecipeInfo(String hrefURL) throws IOException {
		Response resp = getResponse(ROOT_URL + hrefURL);

		if (resp.statusCode() == 200) {
			Document doc = resp.parse();
			Recipe recipe = new Recipe();

			Element aside = doc.selectFirst("div[class='aside']");
			Element infoBasic = aside.selectFirst("dl[class='info_basic']");
			Element btmList = aside.selectFirst("div[class='btm']");

			System.out.println(hrefURL);
			String servingStr = btmList.selectFirst("div[class='dropdown']").text().split("인")[0];
			if (servingStr.contains("~")) {
				String[] servingSplit = servingStr.split("\\~");
				recipe.setMinServing(Integer.parseInt(servingSplit[0]));
				recipe.setMaxServing(Integer.parseInt(servingSplit[1]));
			} else if (servingStr.contains("-")) {
				String[] servingSplit = servingStr.split("\\-");
				// 2-3큰술인 기준 로 표시된 곳이 있음... 어케 하면 좋을까
				try {
					recipe.setMinServing(Integer.parseInt(servingSplit[0]));
					recipe.setMaxServing(Integer.parseInt(servingSplit[1]));
				} catch (NumberFormatException e) {
					recipe.setMinServing(1);
					recipe.setMaxServing(1);
				}
			} else {
				if (!servingStr.equals(""))
					recipe.setMinServing(Integer.parseInt(servingStr));
			}



			Elements infoBasicChild = infoBasic.children();
			Elements btmLis = btmList.select("li");

			// 0 = cookTime, 1 = 스크랩, 2 = 칼로리

			recipe.setMainTitle(aside.selectFirst("h1").selectFirst("strong").text());
			recipe.setSubTitle(aside.selectFirst("h1").text());

			if (infoBasicChild.get(0) != null) {
				if (infoBasicChild.get(0).className().equals("time"))
					recipe.setCookTime(infoBasicChild.get(1).text());
			}
			if (infoBasicChild.size() > 4) {
				if (infoBasicChild.get(4) != null) {
					if (infoBasicChild.get(4).className().equals("cal"))
						recipe.setKcal(infoBasicChild.get(5).text());
				}
			}


			// 재료 목록 가져오기
			for (int i = 0; i < btmLis.size(); i++) {
				Element liItem = btmLis.get(i);
				Ingredient ingredient = new Ingredient();
				ingredient.setName(liItem.selectFirst("span").text());

				if (liItem.selectFirst("em") != null)
					ingredient.setUnitStr(liItem.selectFirst("em").text());

				recipe.addIngredientList(ingredient);
			}

			// 영양 정보가 있을때
			if (doc.selectFirst("div[class='nutrition']") != null) {
				Elements nutritionLiList =
						doc.selectFirst("div[class='nutrition']").selectFirst("ul").children();

				// 영양정보 추가
				for (int i = 0; i < nutritionLiList.size(); i++) {
					Element nutriLi = nutritionLiList.get(i);
					Nutrition nutrition = new Nutrition();
					nutrition.setName(nutriLi.attr("data-info"));
					String[] dataText = nutriLi.attr("data-text").split("<span>");
					String value = dataText[0];
					String valueUnit = dataText[1].split("</span>")[0];
					nutrition.setValue(Double.parseDouble(dataText[0]));
					nutrition.setValueUnit(valueUnit);
					recipe.addNutritionList(nutrition);
				}
			}

			recipe.setMethodHTML(doc.selectFirst("ol[class='lst_step']").html());

			return recipe;
		}

		return null;
	}

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
