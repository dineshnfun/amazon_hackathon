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
		Map<String, Map<String, String>> map = listFilesForFolder(folder);		
	}
	
	public static Map<String, Map<String, String>> listFilesForFolder(final File folder) throws Exception {
		Map<String, Map<String, String>> map = new HashMap<String, Map<String,String>>();
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
		map = readFileAndPopulate(fileNames);
		return map;
	}
	
	public static Map<String, Map<String, String>> readFileAndPopulate(final List<String> fileNames) throws Exception {
		Map<String, String> map = null;
		Map<String, Map<String, String>> finalMap = null;
		finalMap = new HashMap<String, Map<String, String>>();
		for (String fileName : fileNames) {
			@SuppressWarnings("resource")
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
								if (entrySet.getKey().equals(tem[0])) {
									String val = tem[1].toString();
									mapValue.put(val.trim(), tem[2]);
								}
							}							
						} else {
							map.put(tem[1], tem[2]);
						}							
					}					
				}
				if (!map.isEmpty())
				finalMap.put(tem[0], map);
			}
		}	
		return finalMap;
	}	
}
