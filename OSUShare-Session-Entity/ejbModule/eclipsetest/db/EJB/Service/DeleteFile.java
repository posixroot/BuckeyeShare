package eclipsetest.db.EJB.Service;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import eclipsetest.db.EJB.Entity.FileEntity;

@Stateless
public class DeleteFile implements Serializable {
	
	@PersistenceContext(unitName="eclipsetest")
	EntityManager em;
	
	public boolean delete(String fid) {
		
		//delete the fid from LIVEFEED
		deleteLF(fid);
		
		//set it to hidden so that no new downloads can come in
		setHidden(fid);
		
		//get File Path
		FileEntity fe =new FileEntity();
		fe = getPath(fid);
		
		//delete fid from FILE
		deleteF(fid);
		
		//update size in catalog
		updateSize(fe.getCatalog_id());
		
		//delete the file from hard disk
		boolean result = new File(fe.getUri()).delete();
		
		return true;
	}

	private boolean updateSize(long cid) {
		
		Query q = em.createNativeQuery("SELECT * from FILE where CATALOG_ID="+cid+" ", FileEntity.class);
		
		List<FileEntity> fel = q.getResultList();
		String time;
		
		if(!fel.isEmpty()) {
			long n = fel.size();
			
			//update TimeStamp as well
			time = String.valueOf(new Timestamp(Calendar.getInstance().getTime().getTime()));
			
			Query q1 = em.createNativeQuery("UPDATE CATALOG set SIZE="+n+", DATETIME= '"+time+"' where ID="+cid+" ");
			q1.executeUpdate();
		} else {
			time = String.valueOf(new Timestamp(Calendar.getInstance().getTime().getTime()));
			Query q1 = em.createNativeQuery("UPDATE CATALOG set SIZE=0, DATETIME= '"+time+"' where ID="+cid+" ");
			q1.executeUpdate();
		}
		return false;
	}

	private FileEntity getPath(String fid) {
		
		Query q =  em.createNativeQuery("SELECT * from FILE where ID="+fid+" ", FileEntity.class);
		
		List<FileEntity> fel = q.getResultList();
		
		if(!fel.isEmpty()) {
			FileEntity fe = new FileEntity();
			fe= fel.get(0);
			return fe;
		}
		
		return null;
	}

	private boolean setHidden(String fid) {
		
		Query q = em.createNativeQuery("UPDATE FILE set VISIBLE=FALSE where ID="+fid+" ");
		
		q.executeUpdate();
		
		return true;
		
	}

	private boolean deleteLF(String fid) {
		
		Query q = em.createNativeQuery("DELETE from LIVEFEED where FILE_ID="+fid+" ");
		
		q.executeUpdate();
		
		return true;
	}
	
	private boolean deleteF(String fid) {
		
		Query q = em.createNativeQuery("DELETE from FILE where ID="+fid+" ");
		
		q.executeUpdate();
		
		return true;
	}

}
