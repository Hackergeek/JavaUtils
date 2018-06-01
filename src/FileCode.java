import info.monitorenter.cpdetector.CharsetPrinter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author skyward
 * 基于cpdetector检测文件编码实现的文本文件编码转换器
 * 可用于eclipse工程统一编码
 * 功能：将指定目录下的所有指定类型文件转为指定字符编码的文件。
 */
public class FileCode {
	public static void main(String[] args) {
		String destEncode = "utf-8";
		List<String> fileList = new ArrayList<String>();
		List<String> acceptTypes = new ArrayList<String>();

		acceptTypes.add("*.txt");
		acceptTypes.add("*.java");
		acceptTypes.add("*.html");
		acceptTypes.add("*.log");
		visitDirBydigui("./src", fileList, acceptTypes);
		System.out.println(fileList.size());
		for (String file : fileList) {
			String oldEncode = guessEncoding(file);
			System.out.println(file + " " + oldEncode);
			write(file, destEncode, read(file, oldEncode));
		}

	}
	
	/**
	 * 递归访问指定目录，并将指定类型的文件添加到filelist中
	 * @param path
	 * @param fileList
	 * @param acceptTypes
	 */
	private static void visitDirBydigui(String path, List<String> fileList, List<String> acceptTypes) {
		File dir = new File(path);
		if(!dir.exists()) {
			System.out.println("你指定的目录不存在");
			return;
		}
		if(!dir.isDirectory()) {
			System.out.println("请指定一个目录路径，而不是一个文件路径");
			return;
		}
		File[] files = dir.listFiles();
		if (files == null)
			return;
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				visitDirBydigui(files[i].getAbsolutePath(), fileList, acceptTypes);
			} else {
				String strFileName = files[i].getAbsolutePath();
				for (String type : acceptTypes) {
					if (strFileName.matches(".+\\" + type.substring(1))) {
						fileList.add(strFileName);
					}
				}
				
			}
		}

	}

	/**
	 * 返回指定文件的字符编码
	 * @param filename
	 * @return
	 */
	public static String guessEncoding(String filename) {
		try {
			CharsetPrinter charsetPrinter = new CharsetPrinter();
			String encode = charsetPrinter.guessEncoding(new File(filename));
			return encode;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 以指定字符编码读取指定文件，并以字符串的形式返回文件内容
	 * @param fileName
	 * @param encoding
	 * @return
	 */
	public static String read(String fileName, String encoding) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), encoding));
			String str = "";
			while ((str = in.readLine()) != null) {
				sb.append(str);
				sb.append("\n");
			}
			in.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * 将字符串以指定字符编码写入指定文件
	 * @param fileName
	 * @param encoding
	 * @param str
	 */
	public static void write(String fileName, String encoding, String str) {
		try {
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), encoding));
			out.write(str);
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
