package eclipsetest.db.EJB.Service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import eclipsetest.db.EJB.Entity.UserEntity;

@Stateless
public class Login implements Serializable {

	@PersistenceContext(unitName = "eclipsetest")
	EntityManager em;
	
	public String validate(String uname, String password, boolean selection) {
		String ret = "fail";
		if(selection)
			ret = validatePrivUser(uname, password);
		else
			ret = validateUser(uname, password);
		
		return ret;
	}	
	
	public String validateUser(String uname, String password) {
		
		List<UserEntity> userList;
		
		try
		{
			Query query = em.createNativeQuery
			("select * from USER where USERNAME like '" + uname
			+ "' AND PRIVILEGEACCESS like FALSE and PASSWORD like '" + password + "'" 
			, UserEntity.class);
			
			userList = query.getResultList();
			
			if(!userList.isEmpty())
			{
				return "normal";
			}
		} catch(Exception e)
		{
			return "fail";
		}
		
		return "fail";	
	}
	
	public String validatePrivUser(String uname, String password) {
		
		List<UserEntity> userList;
		
		try
		{
			Query query = em.createNativeQuery
			("select * from USER where USERNAME like '" + uname
			+ "' AND PRIVILEGEACCESS like TRUE and PASSWORD like '" + password + "'" 
			, UserEntity.class);
			
			userList = query.getResultList();
			
			if(!userList.isEmpty())
			{
				return "privilege";
			}
		} catch(Exception e)
		{
			return "fail";
		}
		
		return "fail";	
	}

}
