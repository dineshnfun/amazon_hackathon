package com.sparkles.hackathon;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import au.com.bytecode.opencsv.CSVReader;

/**
 * @author Dinesh Kumar
 *
 */

public class BookInfo {

	public static void main(String[] args) throws Exception {
		final File folder = new File ("C:/Users/Indhu/Desktop/CSV_FILES");
		listFilesForFolder(folder);
	}
	
	public static void listFilesForFolder(final File folder) throws Exception {
		List<String> fileNames = new ArrayList<String>();
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				if (fileEntry.getName().contains(".csv")) {
					fileNames.add(fileEntry.getName());
				}
			}
		}
		readFileAndPopulate(fileNames);
	}
	
	public static void readFileAndPopulate(final List<String> fileNames) throws Exception {
		StringBuilder sbr = new StringBuilder();
		Map<String, String> map = null;
		Map<String, Map<String, String>> finalMap = null;
		finalMap = new HashMap<String, Map<String, String>>();
		for (String fileName : fileNames) {
			CSVReader reader = new CSVReader(new FileReader("C:\\Users\\Indhu\\Desktop\\CSV_FILES\\"+fileName));
			List<String[]> temp = reader.readAll();
			boolean flag = false;
			map = new HashMap<String, String>();
			for (String[] tem : temp) {	
				if (finalMap.containsKey(tem[0]) && !map.isEmpty()) {
					map.put(tem[1], tem[2]);
					flag = true;
				} else {
					if (flag) {
						map = new HashMap<String, String>();
						map.put(tem[1], tem[2]);
					} else {
						if (finalMap.containsKey(tem[0])) {
							for (Map.Entry<String, Map<String, String>> entrySet : finalMap.entrySet()) {
								Map<String, String> mapValue = entrySet.getValue();	
								if (entrySet.getKey().equals(tem[0]))
								mapValue.put(tem[1], tem[2]);
							}							
							System.out.println("Notify Changed Values ::: " +tem[1] + " ::: " + tem[2] +" for Key :: " +tem[0]);
						} else {
							map.put(tem[1], tem[2]);
						}							
					}					
				}
				if (!map.isEmpty())
				finalMap.put(tem[0], map);
			}
		}
		for (Map.Entry<String, Map<String, String>> entry : finalMap.entrySet()) {
			System.out.println("Final Map is ::: " +entry);
		} 
	} 
}
