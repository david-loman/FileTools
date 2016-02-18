package com.linxiangpeng.tool;

public class PrintOut {
	
	public interface PipelineListener{
		public void output(String tag,String msg);
	}
	
	@Deprecated
	public static void  print(String msg) {
		System.out.println(msg);
	}
	
	public static void print(String tag,String msg,PipelineListener listener){
		if (listener == null){
			System.out.println(tag+":"+msg);
		}else {
			listener.output(tag, msg);
		}
	}
}
