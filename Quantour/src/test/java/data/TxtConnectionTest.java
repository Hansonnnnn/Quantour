package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.Test;

import dao.connection.TxtConnection;

public class TxtConnectionTest {

	@Test
	public void test() {
		new TxtConnection();
		System.out.println(TxtConnection.CHINEXTstockPOs.size());

		System.out.println(TxtConnection.CSI300stockPOs.size());

		System.out.println(TxtConnection.SMEstockPOs.size());
		
		System.out.println(TxtConnection.nameToCode.size());
	}
	
	/*@Test
	public void test2(){
		
		File file = new File("股票历史数据ALL.csv");
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
			ArrayList<Integer> list = new ArrayList<>();
			int code =0;
			while (string != null) {
				String[] strings = string.split("	");
				if (Integer.parseInt(strings[8])!=code) {
					code = Integer.parseInt(strings[8]);
					list.add(code)		;
				}
				
				
				string = bufferedReader.readLine();
			}
			for (Integer integer : list) {
				System.out.println(integer);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
*/
}
