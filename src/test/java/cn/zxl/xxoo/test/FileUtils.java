package cn.zxl.xxoo.test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 辅助测试的文件工具类
 * @author 左潇龙
 * @version 1.0.0
 * @since 1.0.0
 */
public class FileUtils {
	
	private FileUtils(){}

	public static String read(String fileName) throws IOException{
		File file = new File(fileName);
		FileInputStream fileInputStream = new FileInputStream(file);
		byte[] bytes = new byte[(int) file.length()];
		fileInputStream.read(bytes);
		return new String(bytes);
	}
	
	public static void write(String fileName,String xml) throws IOException{
		OutputStream outputStream = new FileOutputStream(new File(fileName));
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(xml.getBytes());
		int len = -1;
		byte[] bytes = new byte[1024];
		while ((len = byteArrayInputStream.read(bytes)) != -1) {
			outputStream.write(bytes, 0, len);
		}
		outputStream.flush();
		outputStream.close();
		byteArrayInputStream.close();
	}
}
