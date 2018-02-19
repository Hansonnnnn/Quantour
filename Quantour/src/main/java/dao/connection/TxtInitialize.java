package dao.connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import dao.DateHelper;
import jdk.jfr.events.FileWriteEvent;
import po.StockPO;

public class TxtInitialize {
	public static Map<Integer,LinkedHashMap<Date, StockPO>> stockPOs = null;
	public static Map<String , Integer> nameToCode = null;
	public TxtInitialize() {
		if (stockPOs==null) {
//			stockPOs = new ArrayList<>();
			stockPOs = new LinkedHashMap<>();
			nameToCode = new LinkedHashMap<>();
			alterTxt();
		}
	}
	
	private void alterTxt() {
		File file = new File("股票历史数据ALL.csv");
		File newFile = new File("股票历史数据2.0ALL.csv");
		FileReader fileReader = null;
		FileWriter fileWriter = null;
		try {
			fileReader = new FileReader(file);
			fileWriter = new FileWriter(newFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		try {
			bufferedWriter.write(bufferedReader.readLine()+"	logRate");
			bufferedWriter.newLine();
			String string = bufferedReader.readLine();
			LinkedHashMap<Date, StockPO> aStock = null;
			int code =0;
			double logRate = 0;
			double yesterday = 0;
			double today = 0;
			String string2;
			while (string != null) {
				String[] strings = string.split("	");
				code = Integer.parseInt(strings[8]);
				today = Double.parseDouble(strings[7]);
				string2 = string;
				string = bufferedReader.readLine();
				if (string==null) {
					continue;
				}
				strings = string.split("	");
				while(Integer.parseInt(strings[6])==0){
					string = bufferedReader.readLine();
					if (string==null) {
						continue;
					}
					strings = string.split("	");
				}
				if (code!=Integer.parseInt(strings[8])) {
					bufferedWriter.write(string2+"	0.0");
					bufferedWriter.newLine();
					continue;
				}
				yesterday = Double.parseDouble(strings[7]);
				bufferedWriter.write(string2+"	"+Math.log(today/yesterday));
				System.out.println(string2+"	"+Math.log(today/yesterday));
				bufferedWriter.newLine();
			}
			
			bufferedWriter.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new TxtInitialize();
	}
}
