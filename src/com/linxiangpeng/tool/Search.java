package com.linxiangpeng.tool;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.linxiangpeng.file.FileUtils;
import com.linxiangpeng.tool.PrintOut.PipelineListener;

public class Search {
	
	private static final String TAG = "Search";
	
	/**
	 * 二分查找，输入要查找的字符串列表，和目标字符，输出的值不为-1,即为字符串存在。（输入的字符串列表必须是有序且为升序的）
	 * @param strings
	 * @param target
	 * @param listener
	 * @return －1 未找到目标字符串，其它为找到。
	 */
	public static int binarySearch(List<String> strings,String target,PipelineListener listener){
		int minNum = 0;
		int maxNum = strings.size()-1;
		int midNum = (minNum+maxNum)/2;
		if (strings == null || target == null || strings.size() <= 0 || target.length() <= 0){
			PrintOut.print(TAG,"检索失败，请检查参数配置",listener);
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
	
	/**
	 * 广度优先搜索，由于查找文件
	 * @param files
	 * @param path
	 * @param listener
	 * @return null 为未能找到，否则会输出相应结果
	 */
	public static List<File> breadthSearch (LinkedList<File> files,String path,PipelineListener listener){
		List<File> results = new ArrayList<File>();
		if (files == null && files.size() <= 0){
			PrintOut.print(TAG,"检索失败，请检查参数配置",listener);
			return null;
		}
		while (files.size() > 0){
			File f = files.getFirst();
			Pattern pattern = Pattern.compile(path);
			String result = GrepString.grep(f.getAbsolutePath(), pattern,listener);
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
