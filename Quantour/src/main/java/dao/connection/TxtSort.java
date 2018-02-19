package dao.connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

import po.StockPO;

public class TxtSort {
	ArrayList<Integer> test = new ArrayList<>();
	public TxtSort() {
		sortTxt();
		//search();
	}
	private void sortTxt() {
		File file = new File("股票历史数据2.0ALL.csv");
		File newFile1 = new File("CSI300stockPOs.csv");
		File newFile2 = new File("SMEstockPOs.csv");
		File newFile3 = new File("CHINEXTstockPOs.csv");
		FileReader fileReader = null;
		FileWriter fileWriter1 = null;
		FileWriter fileWriter2 = null;
		FileWriter fileWriter3 = null;
		try {
			fileReader = new FileReader(file);
			fileWriter1 = new FileWriter(newFile1);
			fileWriter2 = new FileWriter(newFile2);
			fileWriter3 = new FileWriter(newFile3);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		BufferedWriter bufferedWriter1 = new BufferedWriter(fileWriter1);
		BufferedWriter bufferedWriter2 = new BufferedWriter(fileWriter2);
		BufferedWriter bufferedWriter3 = new BufferedWriter(fileWriter3);
		try {
			String title = bufferedReader.readLine();
			bufferedWriter1.write(title+"	logRate");
			bufferedWriter2.write(title+"	logRate");
			bufferedWriter3.write(title+"	logRate");
			bufferedWriter1.newLine();
			bufferedWriter2.newLine();
			bufferedWriter3.newLine();
			String string = bufferedReader.readLine();
			LinkedHashMap<Date, StockPO> aStock = null;
			int code =0;

			BufferedWriter bufferedWriter = bufferedWriter1;
			while (string != null) {
				String[] strings = string.split("	");
				if (code != Integer.parseInt(strings[8])) {
					code = Integer.parseInt(strings[8]);
					String str=String.format("%06d",code);
					if (str.startsWith("000")||str.startsWith("001")) {
						bufferedWriter = bufferedWriter1;
					}else if (str.startsWith("002")) {
						bufferedWriter = bufferedWriter2;
						test.add(code);
					}else {
						bufferedWriter = bufferedWriter3;
					}
				}
				
				bufferedWriter.write(string);
				bufferedWriter.newLine();
				string = bufferedReader.readLine();
				bufferedWriter.flush();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new TxtSort();
	}
	
	private void search() {
		for(int i = 2001;i<=2566;i++){
			if (test.contains(i)) {
				continue;
			}
			System.out.println(i);
		}
	}
}
