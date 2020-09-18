package com.koreait.matzip;

import java.io.File;

import javax.servlet.http.Part;

public class FileUtils {
	public static void makeFolder(String path) {
		File dir = new File(path);		
		if(!dir.exists()) {
			dir.mkdirs();
		}
	}
	
	public static String getExt(String fileNm) {
		return fileNm.substring(fileNm.lastIndexOf("."));
	}
	
	public static void delFile(String path) {
		File file = new File(path);
		if(file.exists()) {
			if(file.delete()) {
				System.out.println("파일 삭제 성공 >.<");
			} else {
				System.out.println("파일 삭제 실패 흑흑 ㅜㅜ");
			}
		}
	}
	
}