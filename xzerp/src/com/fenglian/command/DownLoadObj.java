package com.fenglian.command;

/**
 * 下载文件DownLoadCommandFactory 所需要的入参数
 * deletefile 是否删除被下载的文件
 * filename 需要下载文件的名称
 */
public class DownLoadObj {
	private boolean deletefile;
	private String filename;
	public boolean isDeletefile() {
		return deletefile;
	}
	public void setDeletefile(boolean deletefile) {
		this.deletefile = deletefile;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public DownLoadObj(boolean deletefile, String filename) {
		super();
		this.deletefile = deletefile;
		this.filename = filename;
	}
	
	
}
