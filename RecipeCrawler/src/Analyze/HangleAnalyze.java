package Analyze;

import java.util.List;
import org.snu.ids.kkma.ma.CharSetType;
import org.snu.ids.kkma.ma.Eojeol;
import org.snu.ids.kkma.ma.MExpression;
import org.snu.ids.kkma.ma.Morpheme;
import org.snu.ids.kkma.ma.MorphemeAnalyzer;
import org.snu.ids.kkma.ma.Sentence;
import Model.IngredientUnit;

public class HangleAnalyze {

	private static class HangleAnalyzeLazy {
		public static final HangleAnalyze LAZY_INSTANCE = new HangleAnalyze();
	}

	public static HangleAnalyze getInstance() {
		return HangleAnalyzeLazy.LAZY_INSTANCE;
	}

	private MorphemeAnalyzer ma;

	public void initilze() {
		ma = new MorphemeAnalyzer();
		ma.createLogger(null);
	}

	public void analyze(String content) throws Exception {

		List<MExpression> ret = ma.analyze(content);

		ret = ma.postProcess(ret);
		ret = ma.leaveJustBest(ret);

		List<Sentence> stl = ma.divideToSentences(ret);
		IngredientUnit unit = new IngredientUnit();
		String unitStr = "";
		String symbol = "";
		double min = 0;
		double max = 0;
		int num = 0;
		//System.out.println("==============================");
		System.out.println(content);
		for (int i = 0; i < stl.size(); i++) {
			Sentence st = stl.get(i);

			for (int j = 0; j < st.size(); j++) {
				for (int z = 0; z < st.get(j).size(); z++) {
					Morpheme morph = st.get(j).get(z);
					/*System.out.println("--------------------");
					System.out.println(morph.getString());
					System.out.println(morph.getTag());
					System.out.println(morph.getComposed());
					System.out.println(morph.getCharSetName());
					System.out.println(morph.getCharSet());
					System.out.println(morph.getSmplStr());
					System.out.println(morph.getSmplStr2());

					System.out.println("--------------------");*/

					if (morph.getCharSet() == CharSetType.NUMBER) {
						num++;
						if (num > 1)
							max = Double.parseDouble(morph.getString());
						else
							min = Double.parseDouble(morph.getString());
					} else if (morph.getCharSet() == CharSetType.SYMBOL) {
						symbol = morph.getString();
					} else {
						unitStr += morph.getString() + " ";
					}



				}
				/*
				 * String str = eo.getSmplStr2(); String[] splitStr = str.split("\\+"); for (int k =
				 * 0; k < splitStr.length; k++) { String[] slashSplit = splitStr[k].split("\\/");
				 * String charStr = slashSplit[0]; String tagStr = slashSplit[1];
				 * 
				 * // System.out.println(charStr + ", " + tagStr); if (min == 0) { if
				 * (tagStr.equals("NR")) min = Double.parseDouble(charStr); else if
				 * (tagStr.equals("NNG")) // 단위 의존 명사 ex) 개 if (charStr.equals("반개")) min = 0.5;
				 * 
				 * 
				 * }
				 * 
				 * if (charStr.equals("^")) // ^ = / { double operandOne =
				 * Double.parseDouble(splitStr[k - 1].split("\\/")[0]); double operandTwo =
				 * Double.parseDouble(splitStr[k + 1].split("\\/")[0]); min = operandOne /
				 * operandTwo; } else if (charStr.equals("~")) { System.out.println(splitStr[k -
				 * 1]); System.out.println(splitStr[k + 1]); min = Double.parseDouble(splitStr[k -
				 * 1].split("\\/")[0]); max = Double.parseDouble(splitStr[k + 1].split("\\/")[0]); }
				 * 
				 * if (!(tagStr.equals("NR") || tagStr.equals("SW") || tagStr.equals("SO"))) unitStr
				 * += charStr + " ";
				 * 
				 * 
				 * }
				 */

				/*
				 * System.out.println( eo.getExp() + "\t" + eo.getSmplStr2() + "\t" +
				 * eo.getStartIndex() + "\t" + eo.getFirstMorp() + "\t" + eo.getLastMorp());
				 */
			}


		}

		unit.setMin(min);
		unit.setMax(max);
		unit.setUnitStr(unitStr);
		unit.setSymbol(symbol);
		System.out.println(unit.toString());
		System.out.println("==============================");
	}

}
