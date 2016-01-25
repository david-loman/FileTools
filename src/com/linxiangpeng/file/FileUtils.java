package com.linxiangpeng.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import com.linxiangpeng.tool.PrintOut;

public class FileUtils {
	
	private static String separator = null;

	public static String getSeparator() {
		if (separator == null || separator.length() <= 0){
			separator = System.getProperties().getProperty("file.separator");
		}
		return separator;
	}

	public static boolean createFile(File file) {
		if (file == null || (file.exists() && file.isDirectory())) {
			String msg = null;
			if (file == null){
				msg = "创建文件不能为空";
			}else if (file.exists() && file.isDirectory()){
				msg = "文件已存在";
			}
			PrintOut.print(msg);
			return false;
		} else {
			try {
				file.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	public static boolean deleteFile(File file) {
		if (file == null || !file.exists()) {
			return true;
		} else {
			try {
				file.delete();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	public static boolean createDir (File file){
		if (file == null ||(file.exists() && file.isFile())){
			String msg = null;
			if (file == null){
				msg = "创建文件夹不能为空";
			}else if (file.exists() && file.isFile()){
				msg = "文件已存在";
			}
			PrintOut.print(msg);
			return false;
		}
		try {
			LinkedList<File> parentFiles = new LinkedList<File>();
			while (file != null && !file.getParentFile().exists()){
				parentFiles.add(file);
				file = file.getParentFile();
			}
			while (parentFiles.size() > 0){
				file = parentFiles.removeLast();
				file.mkdirs();
			}
			if (!file.exists()){
				file.mkdirs();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean deleteDir (File file){
		if (file == null || !file.exists()){
			return true;
		}
		if (file.isDirectory()){
			try {
				for (File f : file.listFiles()){
					deleteDir(f);
				}
				file.delete();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}else{
			return deleteFile(file);
		}
		return true;
	}

	public static String readFile (File file){
		StringBuilder stringBuilder = new StringBuilder();
		if (file == null || !file.exists() || file.isDirectory()){
			PrintOut.print("读取文件异常，请检查参数设置！");
			return null;
		}
		try {
			FileReader reader = new FileReader(file);
			BufferedReader bReader = new BufferedReader(reader);
			String line = null;
			while ((line = bReader.readLine()) != null) {
				stringBuilder.append(line);
			}
			reader.close();
			bReader.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return stringBuilder.toString();
	}

	public static boolean writeFile (File file,String string){
		if (file == null || file.isDirectory()){
			PrintOut.print("写入文件异常，请检查参数");
			return false;
		}
		try {
			FileOutputStream outputStream = new FileOutputStream(file);
			OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
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

	public static boolean copyFile (File sourceFile,File targetFile){
		if (sourceFile == null || !sourceFile.exists() || sourceFile.isDirectory() 
				|| targetFile == null || !targetFile.exists() || targetFile.isDirectory()){
			PrintOut.print("复制文件异常，请检查参数");
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

	public static boolean moveFile (File sourceFile,File targetFile){
		if (sourceFile == null || !sourceFile.exists() || sourceFile.isDirectory() 
				|| targetFile == null || !targetFile.exists() || targetFile.isDirectory()){
			PrintOut.print("移动文件异常，请检查参数");
			return false;
		}
		try {
			copyFile(sourceFile, targetFile);
			deleteFile(sourceFile);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
