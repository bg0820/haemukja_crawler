package Main;

import java.io.IOException;
import java.util.ArrayList;
import Crawler.HaemukjaCrawler;

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
				System.out.println(recipeList.size());
				Thread.sleep(500);
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
