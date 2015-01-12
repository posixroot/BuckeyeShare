package eclipsetest.db.EJB.Service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import eclipsetest.db.EJB.Entity.UserEntity;

@Stateless
public class Registration implements Serializable {
	
	@PersistenceContext(unitName = "eclipsetest")
	EntityManager em;
	
	public String register(String uname, String password, String code) {
		
		UserEntity ue = new UserEntity();
		
		String ret = "fail";
		
		if(isNew(uname)) {
			if(code.equalsIgnoreCase("buckeye")) {
				ret = registerPrivUser(uname, password, ue);
			} else {
				ret = registerUser(uname, password, ue);
			}
		}
		
		return ret;
	}
	
	public boolean isNew(String uname) {
		
		List<UserEntity> userList ;
		
		try {	
			Query query = em.createNativeQuery("SELECT * from USER where USERNAME like '" + uname + "'" 
					, UserEntity.class);
					
			userList = query.getResultList();
					
			if(userList.isEmpty())
			{
				return true;
			}
		} catch(Exception e)
			{
				return false;
			}
			
			return false;
	}

	public String registerUser(String uname, String password, UserEntity ue) {
		
		ue.setUname(uname);
		ue.setPassword(password);
		ue.setPrivaccess(false);
		ue.setFname("");
		ue.setLname("");
		ue.setEmail("");
		ue.setTs(new Timestamp(Calendar.getInstance().getTime().getTime()));
		
		try{
			em.persist(ue);
			em.flush();
		} catch(Exception e) {
			return "fail";
		}
		return "success";
	}
	
	public String registerPrivUser(String uname, String password, UserEntity ue) {
		ue.setUname(uname);
		ue.setPassword(password);
		ue.setPrivaccess(true);
		ue.setFname("");
		ue.setLname("");
		ue.setEmail("");
		ue.setTs(new Timestamp(Calendar.getInstance().getTime().getTime()));
		
		try{
			em.persist(ue);
			em.flush();
		} catch(Exception e) {
			return "fail";
		}
		return "success";
	}

	//used by the AJAX call to check if uname already exists in DB
	public String checkValid(String uname) {
		
		Query q = em.createNativeQuery("SELECT * from USER where USERNAME = '"+uname+"' ", UserEntity.class);
		List<UserEntity> uel = q.getResultList();
		if(uel.isEmpty()) {
			return "Valid UserName. Proceed with Registration";
		}
		return "UserName already exists...";
		
	}
	
	
}
