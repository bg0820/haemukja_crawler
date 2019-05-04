package Crawler;

import java.io.IOException;
import java.util.ArrayList;
import Model.Recipe;

public class CollectionThread extends Thread {

	@Override
	public void run() {
		HaemukjaCrawler hc = new HaemukjaCrawler();
		try {
			int totalCnt = hc.getRecipeRecordCnt();
			int lastPageCnt = (int) Math.ceil(totalCnt
					/ 12.0);

			for (int i = 1; i <= lastPageCnt; i++) {
				ArrayList<String> recipeList = hc.getRecipeList(i);
				for (int j = 0; j < recipeList.size(); j++) {
					Recipe recipe = hc.getRecipeInfo(recipeList.get(j));

					recipe.anaylze();
					Thread.sleep(50);
				}

				Thread.sleep(100);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
