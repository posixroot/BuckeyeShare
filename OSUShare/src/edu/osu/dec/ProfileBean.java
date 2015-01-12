package edu.osu.dec;

import javax.ejb.EJB;

import eclipsetest.db.EJB.Service.Profile;

public class ProfileBean {
	
	private String fname;
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	
	private String lname;
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	
	private String email;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	private LoginBean logBean;
	public LoginBean getLogBean() {
		return logBean;
	}
	public void setLogBean(LoginBean logBean) {
		this.logBean = logBean;
	}
	
	@EJB
	private Profile profile;
	
	public String update() {
		if(!fname.isEmpty() || !lname.isEmpty() || !email.isEmpty()) {
			String ret = profile.update(logBean.getUname(), fname, lname, email);
			
			return ret; // values can be success/fail
		}
		return "fail";
	}

}
