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

import eclipsetest.db.EJB.Entity.FeedEntity;
import eclipsetest.db.EJB.Entity.FileEntity;
import eclipsetest.db.EJB.Entity.UserEntity;

@Stateless
public class Feed implements Serializable {
	
	@PersistenceContext(unitName = "eclipsetest")
	EntityManager em;
	
	public List<List<String>> getFeed(String uname) {
		
		List<List<String>> retList = new ArrayList<List<String>>();
		
		Query q = em.createNativeQuery("SELECT * from USER where USERNAME like '"+uname+"' ", UserEntity.class);
		List<UserEntity> uel = q.getResultList(); //might have to allocate space for List<UserEntity>
		
		if(!uel.isEmpty()) {
			UserEntity ue = new UserEntity();
			ue = uel.get(0);
			
			Query query = em.createNativeQuery("SELECT * from LIVEFEED where OWNER_ID like "+ue.getId()+" ", FeedEntity.class);
			List<FeedEntity> fel = query.getResultList();//might have to allocate space for List<UserEntity>
			
			if(!fel.isEmpty()) {
				
				int n = fel.size();
			
				for(int i=0; i<n; i++) {
					FeedEntity fe = new FeedEntity();
					fe = fel.get(i);
					
					List<String> fl = new ArrayList<String>();
					fl.add(String.valueOf(fe.getFileID()));
					fl.add(String.valueOf(fe.getUserID()));
					fl.add(String.valueOf(fe.getOwnerID()));
					fl.add(String.valueOf(fe.getTimestamp()));
					
					Query q1 = em.createNativeQuery("SELECT * from FILE where ID like "+fe.getFileID()+" ", FileEntity.class);
					List<FileEntity> l1 = q1.getResultList();
					if(!l1.isEmpty()) {
						FileEntity f1 = new FileEntity();
						f1 = l1.get(0);
						fl.add(f1.getName());
					}
					
					Query q2 = em.createNativeQuery("SELECT * from USER where ID like "+fe.getUserID()+" ", UserEntity.class);
					List<UserEntity> l2 = q2.getResultList();
					if(!l2.isEmpty()) {
						UserEntity u1 = new UserEntity();
						u1 = l2.get(0);
						fl.add(u1.getUname());
					}
					
					//fl.add(ue.getUname());  //Do we need Owner_id's name as well?
					
					retList.add(fl);
				}
			}
			
		}
		
		return retList;
	}
	
	

}
