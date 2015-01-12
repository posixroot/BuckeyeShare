package edu.osu.dec;

import javax.ejb.EJB;

import eclipsetest.db.EJB.Service.Registration;

public class RegisterBean {
	
	private String uname;
	private String password;
	private String valid;
	private String code;
	
	@EJB
	private Registration reg;
	
	public String getUname() {
		return uname;
	}
	public void setUname(final String uname) {
		
		if(!uname.equalsIgnoreCase("!") && !uname.equalsIgnoreCase("@") && !uname.equalsIgnoreCase("#")
				&& !uname.equalsIgnoreCase("$") && !uname.equalsIgnoreCase("%") && !uname.equalsIgnoreCase("^")
				&& !uname.equalsIgnoreCase("&") && !uname.equalsIgnoreCase("*") && !uname.equalsIgnoreCase("(")
				&& !uname.equalsIgnoreCase(")") && !uname.equalsIgnoreCase("_") && !uname.equalsIgnoreCase("+")
				&& !uname.equalsIgnoreCase("")) {
			setValid(reg.checkValid(uname));
		} else {
			setValid("Not a Valid User Name");
		}
		
		this.uname = uname;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(final String password) {
		this.password = password;
	}
	
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(final String code) {
		this.code = code;
	}
	
	public String register() {
		
		if(!uname.isEmpty() && !password.isEmpty()) {
			String ret = reg.register(uname, password, code);
			//clear();
			return ret; //values can be fail/success
		}
		//clear();
		return "fail";
	}
	
	public String clearLogin() {
		setUname("");
		setPassword("");
		setCode("");
		return "login";
	}
	
	public String clearRegister() {
		setUname("");
		setPassword("");
		setCode("");
		return "register";
	}
	

}
