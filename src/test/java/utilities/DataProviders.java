package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {

	@DataProvider(name = "UserData")
	public static String[][] getUserData() throws IOException{
		String path = ".\\testdata\\user_data.xlsx";
		
		ExcelUtil eu = new ExcelUtil(path);
		
		int rows = eu.getRowCount("Sheet1");
		int cols = eu.getCellCount("Sheet1");
		
		String[][] UserData = new String[rows][cols];
		
		for(int i = 1; i <= rows; i++) {
			for(int j = 0; j < cols; j++) {
				UserData[i-1][j] = eu.getCellData("Sheet1", i, j);
			}
		}
		
		return UserData;
	}
	
	@DataProvider(name = "Username")
	public static String[] getUsername() throws IOException {
		String path = System.getProperty("user.dir") + "//testdata//user_data.xlsx";
		ExcelUtil eu = new ExcelUtil(path);
		
		int rows = eu.getRowCount("Sheet1");
		String[] Username = new String[rows];
		for(int i = 1; i <= rows; i++) {
			Username[i-1] = eu.getCellData("Sheet1", i, 1);
		}
		
		return Username;
	}
	
	public static void main(String args[]) throws IOException {
		String[][] User = getUserData();
		for(String[] s : User) {
			for(String s1 : s) {
				System.out.print(s1 + "\t");
			}
			System.out.println("");
		}
		
		String[] Username = getUsername();
		for(String s : Username) {
				System.out.print(s + "\t");
		}
	}
}
