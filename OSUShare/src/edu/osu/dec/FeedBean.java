package edu.osu.dec;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import eclipsetest.db.EJB.Service.Feed;

public class FeedBean {
	
	@EJB
	Feed f;
	
	private List<List<String>> list = new ArrayList<List<String>>();
	public List<List<String>> getList() {
		return list;
	}
	public void setList(List<List<String>> list) {
		this.list = list;
	}
	
	private LoginBean logBean;
	public LoginBean getLogBean() {
		return logBean;
	}
	public void setLogBean(LoginBean logBean) {
		this.logBean = logBean;
	}
	
	public String getFeed() {
		
		list = f.getFeed(logBean.getUname());
		if(!list.isEmpty()) {
			return "feed";
		}
		
		return "nofeed";
	}

	

}
