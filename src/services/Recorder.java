package services;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Class used to read configuration files. Default separator for elements in row is ';'.
 * Semicolons are used inSmallStickerFormat.txt to separate requested columns within row. 
 * In config.txt each row contains only one piece of data : printer folder, default printer 
 * 
 * @author Stefan Boskovic
 */
public class Recorder {
	private String config;
	
	public Recorder(String config) {
		this.config = config;
	}
	
	/**
	 * @return list of all rows, where rows are lists of requested columns itself, 
	 * or rows are single element lists which contain data about printerFolder and defaultPrinters
	 * @throws IOException
	 */
	public ArrayList<ArrayList<String>> readConfigFile() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(config)));
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		
		String s = "";
		while ((s = br.readLine()) != null) {
			String[] columns = s.split(";");
			ArrayList<String> line = new ArrayList<String>();
			for (String column : columns) {
				line.add(column);
			}
			result.add(line);
		}
		br.close();
		return result;
	}
	
	public void writeToConfigFile(String[] settings) throws FileNotFoundException {
		PrintWriter pr = new PrintWriter(config);
		for (String s : settings) 
			pr.println(s);
		pr.close();
	}
}
