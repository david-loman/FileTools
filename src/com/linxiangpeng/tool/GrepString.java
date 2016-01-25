package com.linxiangpeng.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GrepString {
	
	public static String grep(String source,String target){
		if (source == null || source.length() <= 0){
			PrintOut.print("参数错误");
			return null;
		}
		if (source.contains(target)){
			return source;
		}else{
			return null;
		}
	}
	
	public static String grep (String source,Pattern pattern){
		if (pattern == null || source == null || source.length() <= 0){
			PrintOut.print("参数错误");
			return null;
		}
		Matcher matcher = pattern.matcher(source);
		if (matcher.find()){
			return matcher.group();
		}else{
			return null;
		}
	}
}
