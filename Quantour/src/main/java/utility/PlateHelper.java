package utility;

import model.StockPlate;

public class PlateHelper {

	public static StockPlate codeToPlate(int code) {
		String ID=String.format("%06d",code);
		if (ID.startsWith("000")||ID.startsWith("001")) {
			return StockPlate.CSI300;
		}else if (ID.startsWith("002")||ID.equals("399005")) {
			return StockPlate.SME;
		}else if (ID.startsWith("300")||ID.equals("399006")){
			return StockPlate.CHINEXT;
		}
		return null;
	}
}
