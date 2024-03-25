package shop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
	private String fileName = "shop.txt";
	private File file = new File(fileName);
	
	private FileWriter fw;
	private FileReader fr;
	private BufferedReader br;
	
	public void saveData(String data) {
		try {
			fw = new FileWriter(file);
			fw.write(data);
			fw.close();
			
			System.out.println("save complete");
		}catch(IOException e) {
			e.printStackTrace();
			System.err.println("save failed");
		}
	}
	
	public String loadData() {
		String info = "";
		if(file.exists()) {
			try {
				fr = new FileReader(file);
				br = new BufferedReader(fr);
				
				boolean isCheck = false;
				
				while(br.ready()) {
					info += br.readLine();
					info += "\n";
					
					isCheck = true;
				}
				
				if(isCheck)
					info = info.substring(0,info.length()-1);
				
				br.close();
				fr.close();
				
				System.out.println("load Data");
			}catch(IOException e) {
				e.printStackTrace();
				System.err.println("load failed");
			}
		}
		else
			return null;
		
		return info;
	}
	
	
}
