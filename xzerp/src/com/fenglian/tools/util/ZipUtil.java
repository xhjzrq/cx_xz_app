package com.fenglian.tools.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import com.fenglian.tools.logger.FileLogger;
import com.sanlink.util.FileFunc;
import com.sanlink.util.StmFunc;

public class ZipUtil {

	/**
	 * 将文件流中的zip内容读取出来并返回
	 * 
	 * @param b
	 * @return
	 * @throws FileNotFoundException
	 */
	public static File readReportFile(File f, String ext) throws Exception {

		String tempPath = Config.getFileConfig("TEMP_FILE_PATH");
		File file2 = new File(tempPath + ID.getId() + ext);
		FileOutputStream fout2 = new FileOutputStream(file2);

		try {
			ZipFile zf = new ZipFile(f);
			ZipInputStream zin = new ZipInputStream(new FileInputStream(f));
			ZipEntry ze;

			while ((ze = zin.getNextEntry()) != null) {
				if (!ze.isDirectory()) {

					if (ze.getName().indexOf(".html") > -1) {
						FileLogger.debug("file - " + ze.getName() + " : "
								+ ze.getSize() + " bytes");

						InputStream inf = zf.getInputStream(ze);
						int i = 0;
						byte[] bs = new byte[1024];

						while ((i = inf.read(bs)) > 0) {
							fout2.write(bs, 0, i);
						}
						inf.close();
					}
				}
			}
			zin.closeEntry();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			fout2.flush();
			fout2.close();
		}

		return file2;
	}

	static final int BUFFER = 2048;

	public static void unzip(String fileName) {
		try {

			String filePath = "E:\\test\\";
			ZipFile zipFile = new ZipFile(fileName);
			Enumeration emu = zipFile.entries();
			int i = 0;
			while (emu.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) emu.nextElement();
				// 会把目录作为一个file读出一次，所以只建立目录就可以，之下的文件还会被迭代到。
				if (entry.isDirectory()) {
					new File(filePath + entry.getName()).mkdirs();
					System.out.println("创建目录");
					continue;
				}
				BufferedInputStream bis = new BufferedInputStream(
						zipFile.getInputStream(entry));
				File file = new File(filePath + entry.getName());
				// 加入这个的原因是zipfile读取文件是随机读取的，考试,大提示这就造成可能先读取一个文件。
				// 而这个文件所在的目录还没有出现过，所以要建出目录来。
				File parent = file.getParentFile();
				if (parent != null && (!parent.exists())) {
					parent.mkdirs();
				}
				FileOutputStream fos = new FileOutputStream(file);
				BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER);
				int count;
				byte data[] = new byte[BUFFER];
				while ((count = bis.read(data, 0, BUFFER)) != -1) {
					bos.write(data, 0, count);
				}
				bos.flush();
				bos.close();
				bis.close();
				System.out.println("创建文件：" + file.getPath());
			}
			zipFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("完成");

	}

	public static void main(String[] args) throws Exception {
		// ZipFile zf = new ZipFile("D:\\log\\1332752554000.zip");
		// unzip("E:\\test\\test.zip");
		System.out.println("------");
		File file = new File("E:\\test\\test.zip");
		FileInputStream in = new FileInputStream(file);
		// InputStream inf = StmFunc.getGZIPStm(in);
		InputStream inf = StmFunc.getUnGZIPStm(in);

		File file2 = new File("E:\\test2\\");
		FileOutputStream out = new FileOutputStream(file2);

		int count;
		byte data[] = new byte[BUFFER];
		while ((count = inf.read(data, 0, BUFFER)) != -1) {
			out.write(data, 0, count);
		}
		out.close();
		inf.close();

		// FileFunc.unzip("E:\\test\\test.zip", "E:\\test\\");

		System.out.println("结束");
	}
}
