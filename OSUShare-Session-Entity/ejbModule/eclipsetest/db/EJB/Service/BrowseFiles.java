package eclipsetest.db.EJB.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import eclipsetest.db.EJB.Entity.CatalogEntity;
import eclipsetest.db.EJB.Entity.FileEntity;
import eclipsetest.db.EJB.Entity.UserEntity;

@Stateless
public class BrowseFiles implements Serializable {
	
	@PersistenceContext(unitName = "eclipsetest")
	EntityManager em;
	
	public List<List<String>> catalogFiles(String catid) {
		
		List<List<String>> retList = new ArrayList<List<String>>();
		
		long cid = Long.parseLong(catid);
		
		Query query = em.createNativeQuery("SELECT * from FILE where CATALOG_ID = "+cid+" ",FileEntity.class);
		
		List<FileEntity> fel = query.getResultList();
		
		if(!fel.isEmpty()) {
			int n = fel.size();
			
			for(int i=0; i<n; i++) {
				FileEntity fe = new FileEntity();
				fe = fel.get(i);
				
				List<String> fl = new ArrayList<String>();
				
				fl.add(String.valueOf(fe.getId()));
				fl.add(fe.getName());
				fl.add(String.valueOf(fe.getOwner_id()));
				fl.add(fe.getDescription());
				fl.add(String.valueOf(fe.getTimestamp()));
				fl.add(String.valueOf(fe.isVisible()));
				fl.add(fe.getUri());
				fl.add(String.valueOf(fe.getCatalog_id()));
				fl.add(String.valueOf(fe.getDownload_count()));
				
				Query q1 = em.createNativeQuery("SELECT * from USER where ID="+fe.getOwner_id()+" ", UserEntity.class);
				List<UserEntity> uel = new ArrayList<UserEntity>();
				uel = q1.getResultList();
				if(!uel.isEmpty()) {
					UserEntity ue = new UserEntity();
					ue = uel.get(0);
					fl.add(ue.getUname());
				}
				
				Query q2 = em.createNativeQuery("SELECT * from CATALOG where ID="+fe.getCatalog_id()+" ", CatalogEntity.class);	
				List<CatalogEntity> cel = new ArrayList<CatalogEntity>();
				cel = q2.getResultList();
				if(!cel.isEmpty()) {
					CatalogEntity ce = new CatalogEntity();
					ce = cel.get(0);
					fl.add(ce.getName());
				}
				
				retList.add(fl);
			}
		}
		
		return retList;	
	}

	
	
}
