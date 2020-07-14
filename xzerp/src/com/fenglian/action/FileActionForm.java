package com.fenglian.action;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class FileActionForm extends ActionForm {
	FormFile file;

	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}
	
}
