package edu.osu.dec;

import javax.ejb.EJB;

import eclipsetest.db.EJB.Service.DownloadFile;
import eclipsetest.db.EJB.Service.Feed;

public class DownloadBean {
	
	private String fid;
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	
	@EJB
	DownloadFile df;
	
	@EJB
	Feed feed;
	
	private LoginBean logBean;
	public LoginBean getLogBean() {
		return logBean;
	}
	public void setLogBean(LoginBean logBean) {
		this.logBean = logBean;
	}
	
	public void download() {
		
		boolean success = df.download(fid, logBean.getUname());
		
		
	}
	

}
