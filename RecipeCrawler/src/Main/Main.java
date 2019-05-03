package Main;

import java.io.IOException;
import java.util.ArrayList;
import Crawler.HaemukjaCrawler;
import Model.Recipe;

public class Main {
	public static void main(String[] args)
	{
		HaemukjaCrawler hc = new HaemukjaCrawler();
		try {
			int totalCnt = hc.getRecipeRecordCnt();
			int lastPageCnt = (int) Math.ceil(totalCnt / 12.0);
			
			for(int i = 1 ; i <= lastPageCnt; i++)
			{
				ArrayList<String> recipeList = hc.getRecipeList(i);
				for(int j = 0; j< recipeList.size(); j++)
				{
					Recipe recipe = hc.getRecipeInfo(recipeList.get(j));
					System.out.println(recipe.toString());
					Thread.sleep(100);
				}
				
				Thread.sleep(300);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
