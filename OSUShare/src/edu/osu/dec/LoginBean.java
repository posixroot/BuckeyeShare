package edu.osu.dec;

import javax.ejb.EJB;

import eclipsetest.db.EJB.Service.Login;

public class LoginBean {
	
	private String uname;
	private String password;
	private String selection;

	@EJB
	private Login login;
	
	public String getUname() {
		return uname;
	}
	public void setUname(final String uname) {
		this.uname = uname;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(final String password) {
		this.password = password;
	}

	public String getSelection() {
		return selection;
	}
	public void setSelection(final String selection) {
		this.selection = selection;
	}
	
	
	public String validateCredentials() {
		
		String ret = "fail";
		if(!uname.isEmpty() && !password.isEmpty() && !selection.isEmpty()) {
			if(selection.equalsIgnoreCase("yes"))
				ret = login.validate(uname, password, true);
			else
				ret = login.validate(uname, password, false);
		}
		return ret; //values can be normal/privilege/fail
	}
	
	public String clearLogin() {
		setUname("");
		setPassword("");
		setSelection("");
		return "login";
	}
	
	public String clearLogout() {
		setUname("");
		setPassword("");
		setSelection("");
		return "logout";
	}
	
	public String homeNav() {
		if(selection.equalsIgnoreCase("yes"))
			return "privilege";
		else
			return "normal";
	}

}
