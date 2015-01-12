package edu.osu.dec;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import eclipsetest.db.EJB.Service.BrowseCatalogs;

public class BrowseCatalogsBean {
	
	@EJB
	BrowseCatalogs bc ;
	
	private LoginBean logBean;
	public LoginBean getLogBean() {
		return logBean;
	}
	public void setLogBean(LoginBean logBean) {
		this.logBean = logBean;
	}
	
	private List<List<String>> allList = new ArrayList<List<String>>(); //space allocation probably not required but just in case
	public List<List<String>> getAllList() {
		return allList;
	}
	public void setAllList(List<List<String>> allList) {
		this.allList = allList;
	}
	
	private List<List<String>> myList = new ArrayList<List<String>>(); //space allocation probably not required but just in case
	public List<List<String>> getMyList() {
		return myList;
	}
	public void setMyList(List<List<String>> myList) {
		this.myList = myList;
	}
	
	private String aid;
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	
	private String filesBackNav;
	public String getFilesBackNav() {
		return filesBackNav;
	}
	public void setFilesBackNav(String filesBackNav) {
		this.filesBackNav = filesBackNav;
	}
	
	public String allCatalogs() {
		
		allList = bc.allCatalogs();
		
		if(!allList.isEmpty()) {
			return "allcatalogs";
		}
		
		return "false";
	}
	
	public String myCatalogs() {
		
		myList = bc.myCatalogs(logBean.getUname());
//		if(!myList.isEmpty()) {
//			return "mycatalogs";
//		}
		
		return "mycatalogs";
	}

	
}
