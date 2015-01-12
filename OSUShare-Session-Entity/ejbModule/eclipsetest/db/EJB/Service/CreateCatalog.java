package eclipsetest.db.EJB.Service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import eclipsetest.db.EJB.Entity.CatalogEntity;
import eclipsetest.db.EJB.Entity.UserEntity;

@Stateless
public class CreateCatalog implements Serializable {
	
	@PersistenceContext(unitName="eclipsetest")
	EntityManager em;
	
	public boolean create(String name, String description, String uname) {
		
		Query q = em.createNativeQuery("Select * from USER where USERNAME like '"+uname+"' ", UserEntity.class);
		long uid = 10000;
		List<UserEntity> uel = new ArrayList<UserEntity>();
		uel = q.getResultList();
		if(!uel.isEmpty()) {
			UserEntity ue = new UserEntity();
			ue = uel.get(0);
			uid = ue.getId();
		}
		
		if(isNew(name, uid)) {
			CatalogEntity ce = new CatalogEntity();
			ce.setName(name);
			ce.setOwner_id(uid);
			ce.setDescription(description);
			ce.setSize(0);
			ce.setTimestamp(new Timestamp(Calendar.getInstance().getTime().getTime()));
			ce.setVisible(true);
			//ce.setUri(uname+"/"+name);
			
			try{
				em.persist(ce);
				em.flush();
			} catch(Exception e) {
				return false;
			}
			return true;
		}
		
		return false;
	}
	
	public boolean isNew(String name, long uid) {
		
		List<UserEntity> userList ;
		
		try {	
			Query query = em.createNativeQuery("select * from CATALOG where NAME like '" + name + "' and OWNER_ID like "+uid+" " 
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
}
