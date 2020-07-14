package com.fenglian.tools.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.stream.FileImageInputStream;

import org.apache.commons.validator.Form;
import org.apache.struts.upload.FormFile;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import com.fenglian.exception.BizException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



public class Msg2Zipbak {
	public static final String CHARSET_UTF_8="UTF-8";
	
	/**
	 * 将数据写成txt
	 * @param dir 目标文件夹
	 * @param data 数据源 list 或者map类型
	 * @return 文件生成路径
	 * @throws Exception 
	 */
	public static String object2Txt(String dir,Object data) throws Exception{
		File outdir=new File(dir+File.separator+ID.getId());
		if(!outdir.exists()){
			outdir.mkdirs();
		}
		
		
		File outfile = new File(outdir.getAbsolutePath()+File.separator+"temp.txt");
		
		BufferedWriter writer=new BufferedWriter(new FileWriter(outfile));
		
		if(data==null){
			throw new BizException("没有数据！");
		}
		Gson gson = new Gson();
		if(data instanceof Map){
			writer.append(gson.toJson(data));
		}else if (data instanceof List) {
			List list =(List)data;
			for (Object object : list) {
				writer.append(gson.toJson(object));
				writer.newLine();
			}
		}else {
			throw new BizException("不支持的数据类型！");
		}
		writer.flush();
		writer.close();
		String filename=outdir.getAbsolutePath();
		writer=null;
		outfile=null;
		return filename;
	}	
	
	
	public static String object2Txt(String dir,String fn,Object data) throws Exception{
		File outdir=new File(dir+File.separator+ID.getId());
		if(!outdir.exists()){
			outdir.mkdirs();
		}
		
		if(CString.isEmpty(fn)){
			fn="temp";
		}
		
		File outfile = new File(outdir.getAbsolutePath()+File.separator+fn+".txt");
		
		BufferedWriter writer=new BufferedWriter(new FileWriter(outfile));
		
		if(data==null){
			throw new BizException("没有数据！");
		}
		Gson gson = new Gson();
		if(data instanceof Map){
			writer.append(gson.toJson(data));
		}else if (data instanceof List) {
			List list =(List)data;
			for (Object object : list) {
				writer.append(gson.toJson(object));
				writer.newLine();
			}
		}else {
			throw new BizException("不支持的数据类型！");
		}
		writer.flush();
		writer.close();
		String filename=outdir.getAbsolutePath();
		writer=null;
		outfile=null;
		return filename;
	}
	
	
	/** 
     * 解压缩 
     * @param destDir 生成的目标目录下   c:/a 
     * @param sourceZip 源zip文件      c:/upload.zip 
     * 结果则是 将upload.zip文件解压缩到c:/a目录下 
     */  
    public static void unZip(String destDir,String sourceZip){  
          
         try {  
            Project prj1 = new Project();  
              
            Expand expand = new Expand();  
              
            expand.setProject(prj1);  
              
            expand.setSrc(new File(sourceZip));  
              
            expand.setOverwrite(true);//是否覆盖  
  
            File f = new File(destDir);  
              
            expand.setDest(f);  
              
            expand.execute();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
  
    /** 
     * 压缩 
     *  
     * @param sourceFile 
     *            压缩的源文件 如: c:/upload 
     * @param targetZip 
     *            生成的目标文件 如：c:/upload.zip 
     */  
    public static void zip(String sourceFile,String targetZip){  
          
          Project prj = new Project();  
            
          Zip zip = new Zip();  
            
          zip.setProject(prj);  
            
          zip.setDestFile(new File(targetZip));//设置生成的目标zip文件File对象  
            
          FileSet fileSet = new FileSet();  
            
          fileSet.setProject(prj);  
            
          fileSet.setDir(new File(sourceFile));//设置将要进行压缩的源文件File对象  
            
          //fileSet.setIncludes("**/*.java"); //包括哪些文件或文件夹,只压缩目录中的所有java文件  
            
          //fileSet.setExcludes("**/*.java"); //排除哪些文件或文件夹,压缩所有的文件，排除java文件  
            
            
          zip.addFileset(fileSet);  
  
          zip.execute();  
  
    }
    
    /**
     * 
     * @param dir 生成文件夹的路径
     * @param data 数据源
     * @return
     * @throws Exception
     */
    public static String object2Zip(String dir,Object data) throws Exception {
    	String filename=object2Txt(dir,data);
//    	System.out.println(filename);
		String id=ID.getId()+"";
		String out=dir+File.separator+id+".zip";
		zip(filename,out);
		deleteBackUp(filename);
		return out;
    }
    
    public static void deleteBackUp(String filename){
    	try {
			File file=new File(filename);
			if(file.isDirectory()){
				File[] list=file.listFiles();
				for (File file2 : list) {
					file2.delete();
				}
			}
			file.delete();
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
	
    /**
     * 根据上传文件保存成zip文件
     * @param ff
     * @return
     * @throws Exception 
     */
    public static String saveToZip(FormFile ff) throws Exception{
    	byte[] buffer=new byte[2048];
    	String temdir = Config.getFileConfig("TEMP_FILE_PATH");
    	
    	File dir=new File(temdir);
    	if(dir.exists()){
    		dir.mkdirs();
    	}
    	String filename=temdir+File.separator+ID.getId()+".zip";
    	File file=new File(filename);
    	OutputStream out=new BufferedOutputStream(new FileOutputStream(file));
    	InputStream inf = ff.getInputStream();		
		byte[] b = new byte[1024];
    	int n = 0;    
    	while((n = inf.read(b))!=-1){
    		out.write(b,0,n);
    	} 
    	out.flush();
    	out.close();
    	inf.close();
    	out=null;
    	inf=null;
    	return filename;
    }
    
    public static String saveFile(FormFile ff) throws Exception{
    	byte[] buffer=new byte[2048];
    	String temdir = Config.getFileConfig("TEMP_FILE_PATH");
    	
    	File dir=new File(temdir);
    	if(dir.exists()){
    		dir.mkdirs();
    	}
    	String ext=getExt(ff.getFileName());
    	if(CString.isEmpty(ext)){
    		ext="tmp";
    	}
    	
    	String filename=temdir+File.separator+ID.getId()+"."+ext;
    	File file=new File(filename);
    	OutputStream out=new BufferedOutputStream(new FileOutputStream(file));
    	InputStream inf = ff.getInputStream();		
		byte[] b = new byte[1024];
    	int n = 0;    
    	while((n = inf.read(b))!=-1){
    		out.write(b,0,n);
    	} 
    	out.flush();
    	out.close();
    	inf.close();
    	out=null;
    	inf=null;
    	return filename;
    }
    
    public static List saveToList(String src) throws Exception{
    	List ret=new ArrayList();
    	BufferedReader reader=new BufferedReader(new FileReader(new File(src)));
    	String line;
    	Gson gson=new Gson();
    	Map data;
    	while ((line=reader.readLine())!=null) {
    		if(!CString.isEmpty((line))){
    			data=gson.fromJson(line, new TypeToken<Map<String, String>>() {  
                }.getType());
    			if(data!=null&&!data.isEmpty()){
    				ret.add(data);
    			}
    		}
		}
    	reader.close();
    	reader=null;
    	return ret;
    }
    
    /**
     * 将上传的zip转换成数据
     * @param ff
     * @return
     * @throws Exception
     */
    public static List convertFormFile2List(FormFile ff) throws Exception{
    	List list = new ArrayList();
    	String zipurl=saveToZip(ff);
    	//System.out.println(zipurl);
    	String zipdir=zipurl.replace(".zip", ""); //获得解压后的文件夹
    	deleteBackUp(zipdir);
    	//System.out.println(zipdir);
    	unZip(zipdir, zipurl);
    	String tempurl=zipdir+File.separator+"temp.txt";
    	list=saveToList(tempurl);
//    	System.out.println("共："+list.size());
    	
    	
    	deleteBackUp(zipurl);//删除源上传文件
    	deleteBackUp(zipdir);//删除解压后文件
    	
    	return list;
    }
    
    public static String getExt(String file) {
    	if(CString.isEmpty(file)){
    		return "";
    	}
    	int lastdot=file.lastIndexOf(".");
    	if(lastdot!=-1){
    		file=file.substring(lastdot+1);
    	}
    	if(CString.isEmpty(file)){
    		return "";
    	}else{
    		return file.toLowerCase();
    	}
    }
    
//	public static final String DIR="F:/msg2zip";
	public static void main(String[] args) {
//		Map data = new HashMap();
//		data.put("aaa", "xxxxx中文");
//		data.put("bbb", "2xxxxx中文");
//		data.put("cccc", "ccccc中文");
//		
//		List list =  new ArrayList();
//		list.add(data);
//		list.add(data);
//		list.add(data);
//		list.add(data);
//		list.add(data);
		System.out.println(getExt("c:/xxx.gof"));
//		try {
////			String filename=object2Txt(DIR,data);
////			System.out.println(filename);
//			object2Zip("F:/msg2zip",list);
//			//deleteBackUp("F:/msg2zip/1415069157859");
//			//file.delete();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		System.out.println("执行结束");
	}
}
