package edu.osu.dec;

import javax.ejb.EJB;

import eclipsetest.db.EJB.Service.CreateCatalog;

public class CreateCatalogBean {
	
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	private String description;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@EJB
	CreateCatalog cc;
	
	private LoginBean logBean;
	public LoginBean getLogBean() {
		return logBean;
	}
	public void setLogBean(LoginBean logBean) {
		this.logBean = logBean;
	}
	
	public String create() {
		
		boolean valid = cc.create(name, description, logBean.getUname());
		if(valid) {
			return "true";
		}
		
		return "false";
	}


}
