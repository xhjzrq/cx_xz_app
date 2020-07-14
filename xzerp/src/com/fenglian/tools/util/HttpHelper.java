package com.fenglian.tools.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHelper {
	public static String callUrl(String urlStr,String para) {
		HttpURLConnection conn = null;
		String resultInfo="NULL";
		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.connect();
			
			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
			//dos.writeUTF("GET");
			dos.write(para.getBytes());
			dos.flush();
			dos.close();
			DataInputStream dis = new DataInputStream(conn.getInputStream());
			resultInfo="";
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			try {
				
				while ((bytesRead = dis.read(buffer, 0, 8192)) != -1) {
					String s = new String(buffer, 0, bytesRead);
					resultInfo+=s;
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//resultInfo = dis.readUTF();
			dis.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				conn.disconnect();
		}
		return resultInfo;
	}
	
	public static void main(String[] args) {
		System.out.println(callUrl(Config.getFileConfig("EHR_URL")+"sysUserController.do", "method=transformJsonData"));
	}
}
