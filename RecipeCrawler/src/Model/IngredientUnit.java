package Model;

public class IngredientUnit {
	// 1 ~ 3 개
	// 1~3 개
	// 1/3 개 = 0.33
	// 3개
	private double min; // 3 1
	private double max; // 3
	private String result;
	private String unitStr; // 개
	private String symbol;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;

		/*if (symbol.equals("~")) {
			result = min + " ~ " + max;
		} else */
		if (symbol.equals("/")) {
			result = String.valueOf(min
					/ max);
		}
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
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
		return "[min=" + min + ", max=" + max + ", symbol=" + symbol + ", result=" + result + ", unitStr=" + unitStr + "]";


	}



}
