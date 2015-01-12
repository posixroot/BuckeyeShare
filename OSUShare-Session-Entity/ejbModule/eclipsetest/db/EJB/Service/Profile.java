package eclipsetest.db.EJB.Service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import eclipsetest.db.EJB.Entity.UserEntity;

@Stateless
public class Profile implements Serializable {
	@PersistenceContext(unitName = "eclipsetest")
	EntityManager em;
	
	public String update(String uname, String fname, String lname, String email) {
		
		//check if there is an entry for that uname
		boolean valid = isUnameValid(uname);
		//if there is, update using query
		if(valid) {
			String fnameStmt = "FIRSTNAME  = '"+fname+"' ";
			String lnameStmt = "LASTNAME = '"+lname+"' ";
			String emailStmt = "EMAIL = '"+email+"' ";
			String setStmt = "";
			if(!fname.isEmpty())
				setStmt += fnameStmt;
			if(!lname.isEmpty()) {
				if(setStmt.isEmpty())
					setStmt += lnameStmt;
				else
					setStmt += ", "+lnameStmt; 
			} 
			if(!email.isEmpty()) {
				if(setStmt.isEmpty())
					setStmt += emailStmt;
				else
					setStmt += ", "+ emailStmt;
			}
				
			Query query = em.createNativeQuery("UPDATE USER set "+setStmt+" where USERNAME like '"+uname+"'");
			
			try {
			query.executeUpdate();
			em.flush();
			} catch (Exception e) {
				return "fail";
			}
			return "success";
		} 
		
		return "fail";
		
		
	}

	private boolean isUnameValid(String uname) {
		
		List<UserEntity> ue;
		
		Query query = em.createNativeQuery("Select * from USER where USERNAME like '"+uname+"'");
		
		ue = query.getResultList();
		
		if(!ue.isEmpty())
			return true;
		 
		return false;
	}

}
