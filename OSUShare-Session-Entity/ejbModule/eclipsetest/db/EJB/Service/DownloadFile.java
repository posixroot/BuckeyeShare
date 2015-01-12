package eclipsetest.db.EJB.Service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import eclipsetest.db.EJB.Entity.FeedEntity;
import eclipsetest.db.EJB.Entity.FileEntity;
import eclipsetest.db.EJB.Entity.UserEntity;

@Stateless
public class DownloadFile implements Serializable {

	@PersistenceContext(unitName="eclipsetest")
	EntityManager em;

	private static final int DEFAULT_BUFFER_SIZE = 10240;
	
	public boolean download(String fid, String uname) {
		
		Query q = em.createNativeQuery("SELECT * from NEWFILE where ID="+fid+" ", FileEntity.class);
		List<FileEntity> fel = q.getResultList();
		if(!fel.isEmpty()) {
			FileEntity fe = new FileEntity();
			fe = fel.get(0);
			
			//isVisible check to make sure we are providing undeleted file to the user
			if(!fe.isVisible()) {
				return false; // the file does not get downloaded if it is made hidden. There is no screen for download-fail.
			}
			
			String filePath = fe.getUri();
			boolean success = fetchFile(fe, filePath, fid, uname); //This functon also does: add to the LIVEFEED and updates the download count of the file
			if(success) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean fetchFile(FileEntity fe, String filePath, String fid, String uname) {
		
		FacesContext fc = FacesContext.getCurrentInstance();
	    ExternalContext ec = fc.getExternalContext();

	    File file = new File(filePath);
	    
	    String contentType = "application/octet-stream";
	    int contentLength = Integer.parseInt(String.valueOf(file.length()));
	    
	    ec.responseReset(); 
	    ec.setResponseContentType(contentType); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ExternalContext#getMimeType() for auto-detection based on filename.
	    ec.setResponseContentLength(contentLength); 
	    ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fe.getName() + "\""); // The Save As popup magic is done here. You can give it any file name you want, this only won't work in MSIE, it will use current request URL as file name instead.

	    OutputStream output = null;
	    try {
			output = ec.getResponseOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			return false;
		}
	    // Now you can write the InputStream of the file to the above OutputStream the usual way.
	    // ...
	    
	    BufferedInputStream in = null;  
	    BufferedOutputStream out = null;  
	    
	    try {
			in = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	    out = new BufferedOutputStream(output, DEFAULT_BUFFER_SIZE);  
	    byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];  
	    int length;  
	    try {
			while ((length = in.read(buffer)) > 0) {  
				out.write(buffer, 0, length);  
			}
			in.close();  
		    out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    //update LIVEFEED
	    boolean success = addFeed(fid, uname);
	    
	    boolean success1 = updateCount(fid);
	    
	    
//	    fc.responseComplete(); // Important! Otherwise JSF will attempt to render the response which obviously will fail since it's already written with a file and closed.
	    return true;
	}
	
	public boolean updateCount(String fid) {
		Query q1 = em.createNativeQuery("SELECT * from FILE where ID like "+fid+" ", FileEntity.class);
		List<FileEntity> l1 = q1.getResultList();
		
		if(!l1.isEmpty()) {
			FileEntity f1 = new FileEntity();
			f1 = l1.get(0);
			long count = f1.getDownload_count();
			count = count + 1;
			
			Query q = em.createNativeQuery("UPDATE FILE set DOWNLOAD_COUNT="+count+" where ID="+f1.getId()+" ");
			
			try {
				q.executeUpdate();
				em.flush();
			} catch (Exception e) {
					return false;
			}
			
			return true;
		}
		return false;
		
	}
	
	public boolean addFeed(String fid, String uname) {
		
		Query q = em.createNativeQuery("SELECT * from USER where USERNAME like '"+uname+"' ", UserEntity.class);
		List<UserEntity> uel = q.getResultList(); //might have to allocate space for List<UserEntity>
		
		Query q1 = em.createNativeQuery("SELECT * from FILE where ID like "+fid+" ", FileEntity.class);
		List<FileEntity> l1 = q1.getResultList();	
		
		if(!uel.isEmpty() && !l1.isEmpty()) {
			UserEntity ue = new UserEntity();
			ue = uel.get(0);
			FileEntity f1 = new FileEntity();
			f1 = l1.get(0);
			
			FeedEntity fent = new FeedEntity();
			fent.setFileID(f1.getId());
			fent.setOwnerID(f1.getOwner_id());
			fent.setUserID(ue.getId());
			fent.setTimestamp(new Timestamp(Calendar.getInstance().getTime().getTime()));
			
			try{
				em.flush();
				em.persist(fent);
				em.flush();
			} catch(Exception e) {
				
			}
			
			return true;
		}
		
		return false;
	}

}
