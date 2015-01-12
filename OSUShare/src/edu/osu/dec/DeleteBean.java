package edu.osu.dec;

import javax.ejb.EJB;

import eclipsetest.db.EJB.Service.DeleteCatalog;
import eclipsetest.db.EJB.Service.DeleteFile;

public class DeleteBean {
	
	private String fid;
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	
	@EJB
	DeleteFile df;
	
	public String deleteFile() {
		
		boolean success = df.delete(fid);
		
		if(success){
			return "deletesuccess";
		}
		
		return "fail";
	}


	private String cid;
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	
	@EJB
	DeleteCatalog dc;
	
	public String deleteCatalog() {
		boolean success = dc.delete(cid);
		
		if(success) {
			return "deletecatsuccess";
		}
		
		return "fail";
	}

	

}
