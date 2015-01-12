package edu.osu.dec;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import eclipsetest.db.EJB.Service.BrowseCatalogs;
import eclipsetest.db.EJB.Service.BrowseFiles;

public class BrowseFilesBean {
	
	@EJB
	BrowseFiles bf;
	
//	@EJB
//	BrowseCatalogs bc;
	
	private BrowseCatalogsBean bcb;
	public BrowseCatalogsBean getBcb() {
		return bcb;
	}
	public void setBcb(BrowseCatalogsBean bcb) {
		this.bcb = bcb;
	}
	
	private List<List<String>> fileList = new ArrayList<List<String>>();
	public List<List<String>> getFileList() {
		return fileList;
	}
	public void setFileList(List<List<String>> fileList) {
		this.fileList = fileList;
	}
	
	public String catalogFiles() {
		
		fileList = bf.catalogFiles(bcb.getAid());
		
		if(!fileList.isEmpty()) {
			return "true";
		}
		
		return "false";
	}
	
	public String backNav() {
		String val = bcb.getFilesBackNav();
		if(val.equalsIgnoreCase("1")) {
			bcb.setFilesBackNav("2");
			return "mycatalogs";
		}
		return "allcatalogs";
	}


	
}
