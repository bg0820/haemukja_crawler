package Model;

public class Ingredient {
	private String name;
	private String unitStr;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getUnitStr() {
		return unitStr;
	}

	public void setUnitStr(String unitStr) {
		this.unitStr = unitStr;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return unitStr;//"[name=" + name + ", unitStr=" + unitStr + "]"; 
	}

	

}
