package com.linxiangpeng.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.linxiangpeng.tool.PrintOut.PipelineListener;

public class GrepString {
	
	private static final String TAG = "GrepString";
	
	/**
	 * 普通的字符串查找
	 * @param source
	 * @param target
	 * @param listener
	 * @return
	 */
	public static String grep(String source,String target,PipelineListener listener){
		if (source == null || source.length() <= 0){
			PrintOut.print(TAG,"参数错误",listener);
			return null;
		}
		if (source.contains(target)){
			return source;
		}else{
			return null;
		}
	}
	
	/**
	 * 通过正则表达式查找
	 * @param source
	 * @param pattern
	 * @param listener
	 * @return
	 */
	public static String grep (String source,Pattern pattern,PipelineListener listener){
		if (pattern == null || source == null || source.length() <= 0){
			PrintOut.print(TAG,"参数错误",listener);
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
