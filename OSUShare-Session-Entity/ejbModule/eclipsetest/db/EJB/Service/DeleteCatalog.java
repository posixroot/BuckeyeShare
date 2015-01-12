package eclipsetest.db.EJB.Service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import eclipsetest.db.EJB.Entity.FileEntity;

@Stateless
public class DeleteCatalog implements Serializable {
	
	@PersistenceContext(unitName = "eclipsetest")
	EntityManager em;
	
	@EJB
	DeleteFile df;
	
	public boolean delete(String cid) {
		
		//Get the List of files within the catalog
		Query q = em.createNativeQuery("SELECT * from FILE where CATALOG_ID="+cid+" ", FileEntity.class);
		List<FileEntity> fel =  q.getResultList();
		
		if(!fel.isEmpty()) {
			int n = fel.size();
			
			for(int i=0; i<n; i++) {
				FileEntity fe = new FileEntity();
				fe = fel.get(i);
				
				//delete the LIVEFEED of all the files within the Catalog
				//delete the files from FILE
				//special conditions taken care of by the DeleteFile service bean
				df.delete(String.valueOf(fe.getId()));
				
			}
			
			//delete the Catalog
			Query q1 = em.createNativeQuery("DELETE from CATALOG where ID="+cid+" ");
			q1.executeUpdate();
			
			return true;
			
		}
		
		
		return false;
		
	}

}
