package com.linxiangpeng.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.swing.Box.Filler;

import com.linxiangpeng.tool.PrintOut;
import com.linxiangpeng.tool.PrintOut.PipelineListener;

/**
 * @author linxiangpeng
 * 文件工具类，封装了文件操作的一些基础操作
 */
public class FileUtils {
	
	private static final String TAG = "FileUtils";
	private static String separator = null;

	/**
	 * 获取系统的文件间隔符
	 * @return 系统文件间隔符
	 */
	public static String getSeparator() {
		if (separator == null || separator.length() <= 0){
			separator = System.getProperties().getProperty("file.separator");
		}
		return separator;
	}
	
	/**
	 * 创建文件
	 * @param file
	 * @param listener
	 * @return true 为成功，false 为失败。
	 */
	public static boolean createFile(File file,PipelineListener listener){
		if (file == null) {
			PrintOut.print(TAG,"创建文件不能为空!",listener);
			return false;
		}
		// 如果父目录不存在，则创建父目录
		if (!file.getParentFile().exists()){
			createDir(file.getParentFile(),listener);
		}
		try {
			file.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 创建文件夹
	 * @param file
	 * @param listener
	 * @return true 为成功，false 为失败。
	 */
	public static boolean createDir (File file,PipelineListener listener){
		if (file == null){
			PrintOut.print(TAG,"创建文件夹不能为空!",listener);
			return false;
		}
		// 获取不存在的父目录列表
		LinkedList<File> parentDirs = new LinkedList<File>();
		while (file != null && !file.getParentFile().exists()){
			parentDirs.add(file);
			file = file.getParentFile();
		}
		while (parentDirs.size() > 0){
			file = parentDirs.removeLast();
			file.mkdir();
		}
		if (!file.exists()){
			return file.mkdir();
		}
		return true;
	}

	/**
	 * 删除目录或则文件
	 * @param file
	 * @return true 为成功，false 为失败。
	 */
	public static boolean deleteDirOrFile (File file){
		if (file == null || !file.exists()){
			return true;
		}
		if (file.isDirectory()){
			try {
				for (File f : file.listFiles()){
					deleteDirOrFile(f);
				}
				file.delete();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}else{
			return file.delete();
		}
		return true;
	}
	
	/**
	 * 读取文件内容
	 * @param file
	 * @param charsetName
	 * @param listener
	 * @return 文件的内容
	 */
	public static String readFile (File file,String charsetName,PipelineListener listener){
		StringBuilder sb = new StringBuilder();
		if (file == null || !file.exists() || file.isDirectory()){
			PrintOut.print(TAG, "读取文件异常，请检查参数设置！", listener);
			return null;
		}
		if (charsetName == null){
			charsetName = "UTF-8";
		}
		try {
			FileInputStream fInputStream = new FileInputStream(file);
			InputStreamReader inputStreamReader = new InputStreamReader(fInputStream,charsetName);
			BufferedReader bReader = new BufferedReader(inputStreamReader);
			String line  = null;
			while ((line = bReader.readLine()) != null) {
				sb.append(line+"\n");
			}
			bReader.close();
			inputStreamReader.close();
			fInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return sb.toString();
	}

	/**
	 * 写入文件内容
	 * @param file
	 * @param string
	 * @param charsetName
	 * @param listener
	 * @return true 为成功，false 为失败。
	 */
	public static boolean writeFile (File file,String string,String charsetName,PipelineListener listener){
		if (file == null || file.isDirectory()){
			PrintOut.print(TAG,"写入文件异常，请检查参数!",listener);
			return false;
		}
		if (charsetName == null){
			charsetName = "UTF-8";
		}
		try {
			FileOutputStream outputStream = new FileOutputStream(file);
			OutputStreamWriter writer = new OutputStreamWriter(outputStream, charsetName);
			writer.write(string);
			writer.flush();
			writer.close();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 复制文件
	 * @param sourceFile
	 * @param targetFile
	 * @param listener
	 * @return true 为成功，false 为失败。
	 */
	public static boolean copyFile (File sourceFile,File targetFile,PipelineListener listener){
		if (sourceFile == null || !sourceFile.exists() || sourceFile.isDirectory() 
				|| targetFile == null || !targetFile.exists() || targetFile.isDirectory()){
			PrintOut.print(TAG,"复制文件异常，请检查参数!",listener);
			return false;
		}
		try {
			FileInputStream inputStream = new FileInputStream(sourceFile);
			FileOutputStream outputStream = new FileOutputStream(targetFile);
			byte [] b = new byte [2048];
			int len = -1;
			while ((len =inputStream.read(b)) != -1){
				outputStream.write(b,0,len);
			}
			outputStream.flush();
			outputStream.close();
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 移动文件
	 * @param sourceFile
	 * @param targetFile
	 * @param listener
	 * @return true 为成功，false 为失败。
	 */
	public static boolean moveFile (File sourceFile,File targetFile,PipelineListener listener){
		if (sourceFile == null || !sourceFile.exists() || sourceFile.isDirectory() 
				|| targetFile == null || !targetFile.exists() || targetFile.isDirectory()){
			PrintOut.print(TAG,"移动文件异常，请检查参数",listener);
			return false;
		}
		try {
			copyFile(sourceFile, targetFile,listener);
			deleteDirOrFile(sourceFile);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
//	public static void main (String args[]){
//		PrintOut.print("start");
//		String fileName = "/Users/linxiangpeng/Documents/Me/test.html";
//		PrintOut.print("-->"+createFile(new File(fileName)));
//	}
}
