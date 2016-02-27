package com.linxiangpeng.tool;

public class PrintOut {
	/**
	 * 可以根据不同的平台来封装打印结果
	 * @author linxiangpeng
	 *
	 */
	public interface PipelineListener{
		public void output(String tag,String msg);
	}
	
	@Deprecated
	public static void  print(String msg) {
		System.out.println(msg);
	}
	
	/**
	 * 打印日志
	 * @param tag
	 * @param msg
	 * @param listener
	 */
	public static void print(String tag,String msg,PipelineListener listener){
		if (listener == null){
			System.out.println(tag+":"+msg);
		}else {
			listener.output(tag, msg);
		}
	}
}
