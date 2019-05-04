package Main;

import Analyze.HangleAnalyze;
import Crawler.CollectionThread;

public class Main {
	public static void main(String[] args) {
		// first init
		HangleAnalyze.getInstance().initilze();

		CollectionThread ct = new CollectionThread();
		ct.start();

	}
}
