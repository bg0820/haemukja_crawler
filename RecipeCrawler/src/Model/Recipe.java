package Model;

import java.util.ArrayList;
import Analyze.HangleAnalyze;

public class Recipe {
	private String mainTitle;
	private String subTitle;

	private String cookTime;
	private String kcal;
	private ArrayList<Ingredient> ingredientList = new ArrayList<Ingredient>();

	public String getMainTitle() {
		return mainTitle;
	}

	public void setMainTitle(String mainTitle) {
		this.mainTitle = mainTitle;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getCookTime() {
		return cookTime;
	}

	public void setCookTime(String cookTime) {
		this.cookTime = cookTime;
	}

	public String getKcal() {
		return kcal;
	}

	public void setKcal(String kcal) {
		this.kcal = kcal;
	}

	public void addIngredientList(Ingredient ing) {
		ingredientList.add(ing);
	}

	public Ingredient getIngredientList(int idx) {
		return ingredientList.get(idx);
	}

	public void anaylze() throws Exception {
		System.out.println("========================");
		for (int i = 0; i < ingredientList.size(); i++) {
			//9HangleAnalyze.getInstance().getKeyWord(ingredientList.get(i).getUnitStr());
			HangleAnalyze.getInstance().analyze(ingredientList.get(i).getUnitStr());
		}
		System.out.println("========================");
	}
	
	public void printIngredient()
	{
		for (int i = 0; i < ingredientList.size(); i++) {
			System.out.println(ingredientList.get(i).getUnitStr());
		}
	}

	@Override
	public String toString() {

		StringBuffer sb = new StringBuffer();

		sb.append("======================\r\n");
		sb.append("메인 이름 : " + mainTitle + "\r\n");
		sb.append("서브 이름 : " + subTitle + "\r\n");
		sb.append("조리 시간 : " + cookTime + "\r\n");
		sb.append("칼로리 : " + kcal + "\r\n");
		sb.append("---------재료---------\r\n");
		for (int i = 0; i < ingredientList.size(); i++) {
			sb.append(ingredientList.get(i).toString() + "\r\n");
		}

		sb.append("======================\r\n");

		return sb.toString();
	}


}
