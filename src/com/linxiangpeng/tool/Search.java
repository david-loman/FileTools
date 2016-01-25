package com.linxiangpeng.tool;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.linxiangpeng.file.FileUtils;

public class Search {
	
	public static int binarySearch(List<String> strings,String target){
		int minNum = 0;
		int maxNum = strings.size()-1;
		int midNum = (minNum+maxNum)/2;
		if (strings == null || target == null || strings.size() <= 0 || target.length() <= 0){
			PrintOut.print("检索失败，请检查参数配置");
			return -1;
		}
		Collections.sort(strings);
		String midString = strings.get(midNum);
		while (midNum != maxNum){
			int compare = midString.compareTo(target);
			if (compare > 0){
				maxNum = midNum;
			}else{
				if (compare == 0){
					return midNum;
				}
				minNum = midNum;
			}
			midNum = (minNum + maxNum)/2;
		}
		return -1;
	}
	
	public static List<File> breadthSearch (LinkedList<File> files,String path){
		List<File> results = new ArrayList<File>();
		if (files == null && files.size() <= 0){
			PrintOut.print("检索失败，请检查参数配置");
			return null;
		}
		while (files.size() > 0){
			File f = files.getFirst();
			Pattern pattern = Pattern.compile(path);
			String result = GrepString.grep(f.getAbsolutePath(), pattern);
			if (result != null && result.length() > 0){
				results.add(f);
			}
			if (f.isDirectory()){
				for (File tmpFile : f.listFiles()){
					files.addLast(tmpFile);
				}
			}
			files.removeFirst();
		}
		return results;
	}

}
