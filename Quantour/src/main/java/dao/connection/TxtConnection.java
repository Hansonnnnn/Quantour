package dao.connection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import dao.DateHelper;
import po.StockPO;

public class TxtConnection {
	//public static ArrayList<StockPO> stockPOs = null;
	
	public static Map<Integer,LinkedHashMap<Date, StockPO>> CSI300stockPOs = null;
	public static Map<Integer,LinkedHashMap<Date, StockPO>> SMEstockPOs = null;
	public static Map<Integer,LinkedHashMap<Date, StockPO>> CHINEXTstockPOs = null;
	public static Map<String , Integer> nameToCode = null;
	public TxtConnection() {
		if (CSI300stockPOs==null||SMEstockPOs==null||CHINEXTstockPOs==null) {
//			stockPOs = new ArrayList<>();
			CSI300stockPOs = new LinkedHashMap<>();
			SMEstockPOs = new LinkedHashMap<>();
			CHINEXTstockPOs = new LinkedHashMap<>();
			nameToCode = new LinkedHashMap<>();
			readTxt();
		}
	}
	private void readTxt() {
		readTxt(new File("CHINEXTstockPOs.csv"), CHINEXTstockPOs);
		readTxt(new File("CSI300stockPOs.csv"), CSI300stockPOs);
		readTxt(new File("SMEstockPOs.csv"), SMEstockPOs);
	}
	
	private void readTxt(File file,Map<Integer,LinkedHashMap<Date, StockPO>> stockPOs) {
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		try {
			bufferedReader.readLine();
			String string = bufferedReader.readLine();
			LinkedHashMap<Date, StockPO> aStock = null;
			int code =0;
			while (string != null) {
				String[] strings = string.split("	");
				if (Integer.parseInt(strings[8])!=code) {
					if (aStock!=null) {
						stockPOs.put(code, (aStock));
					}
					
					aStock = new LinkedHashMap<>();					
					code = Integer.parseInt(strings[8]);
					nameToCode.put(strings[9], code);
				}
				
				
				aStock.put(DateHelper.stringToDate(strings[1]),new StockPO(Integer.parseInt(strings[0]), DateHelper.stringToDate(strings[1]),
						Double.parseDouble(strings[2]), Double.parseDouble(strings[3]), Double.parseDouble(strings[4]),
						Double.parseDouble(strings[5]), Long.parseLong(strings[6]), Double.parseDouble(strings[7]),
						Integer.parseInt(strings[8]), strings[9], strings[10],Double.parseDouble(strings[11])));
				string = bufferedReader.readLine();
			}
			stockPOs.put(code, (aStock));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
