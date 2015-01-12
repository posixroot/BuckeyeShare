package eclipsetest.db.EJB.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import eclipsetest.db.EJB.Entity.CatalogEntity;
import eclipsetest.db.EJB.Entity.UserEntity;

@Stateless
public class BrowseCatalogs implements Serializable {
	
	@PersistenceContext(unitName = "eclipsetest")
	EntityManager em;
	
	public List<List<String>> allCatalogs() {
		
		List<List<String>> retList = new ArrayList<List<String>>();
		
		List<CatalogEntity> catalogList;
		//List<UserEntity> userList;
		
		
		Query query = em.createNativeQuery("SELECT * FROM CATALOG", CatalogEntity.class);
		
		catalogList = query.getResultList();
		
		if(!catalogList.isEmpty()) {
			
			int n = catalogList.size();
			
			for(int i=0; i<n; i++) {
				CatalogEntity ce = new CatalogEntity();
				
				ce = catalogList.get(i);
				
				List<String> cat = new ArrayList<String>();
				
				cat.add(String.valueOf(ce.getId()));	//0
				cat.add(ce.getName());					//1
				cat.add(String.valueOf(ce.getOwner_id()));	//2
				cat.add(ce.getDescription());				//3
				cat.add(String.valueOf(ce.getSize()));		//4
				cat.add(String.valueOf(ce.getTimestamp()));	//5
				cat.add(String.valueOf(ce.isVisible()));	//6
				cat.add("dummy");						//7
				
				Query q = null ;
				q = em.createNativeQuery("SELECT * FROM USER where ID = "+ce.getOwner_id()+" ", UserEntity.class);
				List<UserEntity> tmp = q.getResultList();
				if(!tmp.isEmpty()) {
					UserEntity ue = tmp.get(0);		//8
					cat.add(ue.getUname());
				}
				
				retList.add(cat);
				
			}
		}
		
		return retList;
	}
	
	public List<List<String>> myCatalogs(String uname) {
		
		List<List<String>> retList = new ArrayList<List<String>>();
		
		List<CatalogEntity> catalogList;
		List<UserEntity> userList;
		long uid;
		
		Query q = em.createNativeQuery("SELECT * FROM USER where USERNAME = '"+uname+"'", UserEntity.class);
		userList = q.getResultList();
		
		if(!userList.isEmpty()) {
			UserEntity ue = new UserEntity();
			ue = userList.get(0);
			uid = ue.getId();
			
			Query query = em.createNativeQuery("SELECT * FROM CATALOG where OWNER_ID = "+uid+" ", CatalogEntity.class);
			
			catalogList = query.getResultList();
			
			
			if(!catalogList.isEmpty()) {
				
				int n = catalogList.size();
				
				for(int i=0; i<n; i++) {
					CatalogEntity ce = new CatalogEntity();
					
					ce = catalogList.get(i);
					
					List<String> cat = new ArrayList<String>();
					
					cat.add(String.valueOf(ce.getId()));	//0
					cat.add(ce.getName());					//1
					cat.add(String.valueOf(ce.getOwner_id()));	//2
					cat.add(ce.getDescription());				//3
					cat.add(String.valueOf(ce.getSize()));		//4
					cat.add(String.valueOf(ce.getTimestamp()));	//5
					cat.add(String.valueOf(ce.isVisible()));	//6
					cat.add("dummy");						//7
					cat.add(uname);								//8
					
					retList.add(cat);
					
				}
			}
		}
		return retList;	
	}

	
}
