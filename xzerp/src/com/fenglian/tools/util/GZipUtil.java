package com.fenglian.tools.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.fenglian.tools.logger.FileLogger;
import com.sanlink.util.StmFunc;

public class GZipUtil {
	
	/**
	 * 读取gzip文件，保存成zip文件。
	 * @param b
	 * @return
	 * @throws IOException
	 */
	public static File getGzip(byte[] b,String filetype) throws IOException
	{
		InputStream in = new ByteArrayInputStream(b);
		InputStream  inf = StmFunc.getUnGZIPStm(in);
		String tempPath = Config.getFileConfig("TEMP_FILE_PATH");
		File file = new File(tempPath + ID.getId() + filetype);
		FileOutputStream out = new FileOutputStream(file);
		int count;
		byte data[] = new byte[1024];
		while ((count = inf.read(data)) != -1) {
			out.write(data, 0, count);
		}
		out.close();
		inf.close();
		FileLogger.debug("保存Gzip："+file.getPath());
		return file;
	}
}
