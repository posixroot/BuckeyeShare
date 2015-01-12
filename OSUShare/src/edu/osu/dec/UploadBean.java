package edu.osu.dec;

import java.io.IOException;
import java.util.Scanner;

import javax.ejb.EJB;
import javax.servlet.http.Part;

import eclipsetest.db.EJB.Service.UploadFile;

public class UploadBean {
	
	private BrowseCatalogsBean bcb;
	public BrowseCatalogsBean getBcb() {
		return bcb;
	}
	public void setBcb(BrowseCatalogsBean bcb) {
		this.bcb = bcb;
	}
	
	private LoginBean logBean;
	public LoginBean getLogBean() {
		return logBean;
	}
	public void setLogBean(LoginBean logBean) {
		this.logBean = logBean;
	}
	
	private String status;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	private Part file;
	public Part getFile() {
		return file;
	}
	public void setFile(Part file) {
		this.file = file;
	}
	
	private String description;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@EJB
	UploadFile uf;
	
	public String fileUp() {
		

		boolean success = uf.upload(bcb.getAid(), logBean.getUname(), file, description);
		
		if(success) {
			return "success";
		}
//		String con = null;
//		try {
//			con = new Scanner(file.getInputStream())
//			.useDelimiter("\\A").next();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println("The contents of Part File: "+fileContent);
		
		return "fail";
		//return fileContent;
	}
	
	public String homeNav() {
		setDescription("");
		setFile(null);
		
		return "home";
	}


}
