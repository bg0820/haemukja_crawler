package Analyze;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
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

		System.out.println(content);

		if (content.equals(""))
			return;

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

		ArrayDeque<Morpheme> pickUpQueue = new ArrayDeque<Morpheme>();

		Sentence st = stl.get(0);

		for (int i = 0; i < st.size(); i++) {
			Eojeol eoj = st.get(i);
			// System.out.println(eoj.getSmplStr2());


			for (int j = 0; j < eoj.size(); j++) {

				Morpheme mp = eoj.get(j);

				if (mp.getTag().equals("ETD")) {
					// ETD가 나오는경우 앞의 값이 VCP 일경우 앞에 제거하고 합쳐진것 넣기
					Morpheme prevMor = eoj.get(j - 1);
					if (prevMor.getTag().equals("VCP")
							|| prevMor.getTag().equals("VA") || prevMor.getTag().equals("VV") ) {
						Morpheme mpp = new Morpheme(eoj.getExp(), null, "C");
						pickUpQueue.pollLast();
						pickUpQueue.add(mpp);
					} else
						pickUpQueue.add(mp);
				} else {
					pickUpQueue.add(mp);
				}


			}
		}

		while (true) {
			Morpheme charMorp = pickUpQueue.poll();
			if (charMorp == null)
				break;

			if (charMorp.getCharSet() == CharSetType.NUMBER) {
				if (num == 0)
					min = Double.parseDouble(charMorp.getString());
				else if (num == 1)
					max = Double.parseDouble(charMorp.getString());

				num++;

			} else if (charMorp.getCharSet() == CharSetType.SYMBOL)
				symbol = charMorp.getString();
			else {
				unitStr += charMorp.getString();
			}

		}

		//
		// System.out.println("EOJ GETEXP : " + eoj.getExp());
		// Morpheme first = eoj.getFirstMorp();
		// Morpheme last = eoj.getLastMorp();
		//
		// System.out.println("FIRST MORP : " + first);
		// System.out.println("LAST MORP : " + last);
		// System.out.println("EOJ SIZE : " + eoj.size());
		//
		// if (eoj.size() == 1) {
		// System.out.println(eoj.get(0));
		// if (eoj.get(0).getCharSet() == CharSetType.NUMBER)
		// min = Double.parseDouble(eoj.get(0).getString());
		// else
		// unitStr += eoj.getExp() + " ";
		// } else if (eoj.size() == 2) {
		// if (first.getCharSet() == CharSetType.NUMBER
		// && last.getCharSet() != CharSetType.NUMBER) {
		// min = Double.parseDouble(first.getString());
		// unitStr = last.getString();
		// } else
		// unitStr = eoj.getExp();
		//
		// } else if (eoj.size() == 3) { // 2~3, 2-3
		// if (first.getCharSet() == CharSetType.NUMBER
		// && last.getCharSet() == CharSetType.NUMBER) {
		// min = Double.parseDouble(first.getString());
		// max = Double.parseDouble(last.getString());
		// Morpheme mid = eoj.get(1);
		// symbol = mid.getString();
		// }
		// } else
		// unitStr += eoj.getExp();

		// for (int z = 0; z < st.get(j).size(); z++) {
		// Morpheme morph = eoj.get(z);
		//
		// if (morph.getCharSet() == CharSetType.NUMBER) {
		// num++;
		// if (num > 1)
		// max = Double.parseDouble(morph.getString());
		// else
		// min = Double.parseDouble(morph.getString());
		// } else if (morph.getCharSet() == CharSetType.SYMBOL) {
		// symbol = morph.getString();
		// } else {
		// if(morph.getTag().equals("ETD"))
		// unitStr += eoj.getExp() + " ";
		// else if(!morph.getTag().equals("VA"))
		// unitStr += morph.getString() + " ";
		//
		// //System.out.println(morph.getString());
		// //System.out.println(morph.getTag());
		// }
		// }

		// }



		unit.setMin(min);
		unit.setMax(max);
		unit.setUnitStr(unitStr);
		unit.setSymbol(symbol);
		System.out.println(unit.toString());

	}

}
