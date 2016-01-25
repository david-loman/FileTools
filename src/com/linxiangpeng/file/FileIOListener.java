package com.linxiangpeng.file;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

public interface FileIOListener {
	public boolean inputOperate(InputStream inputStream);
	public boolean inputOperate(Reader reader);
	public boolean outputOperate(OutputStream outputStream);
	public boolean outputOperate(Writer writer);
}
