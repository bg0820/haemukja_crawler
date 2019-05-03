package Model;

import java.util.ArrayList;

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

	public void addIngredientList(Ingredient ing)
	{
		ingredientList.add(ing);
	}
	
	public Ingredient getIngredientList(int idx)
	{
		return ingredientList.get(idx);
	}


}
