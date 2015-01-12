package eclipsetest.db.EJB.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.Part;

import eclipsetest.db.EJB.Entity.CatalogEntity;
import eclipsetest.db.EJB.Entity.FileEntity;
import eclipsetest.db.EJB.Entity.UserEntity;

@Stateless
public class UploadFile implements Serializable {
	
	@PersistenceContext(unitName="eclipsetest")
	EntityManager em;
	
	
	public boolean upload(String aid, String uname, Part file, String description) {
		
		String fileName = getFileName(file);
		if(fileName==null) {
			return false;
		}
		
		String outPath = "/home/kiran/Desktop/test";
		
		String catName = getCatalogName(aid);
		if(catName==null) {
			return false;
		}
		
		boolean dirSuccess = new File(outPath+File.separator+uname+File.separator+catName).mkdirs();
//		if(!dirSuccess) {
//			return false;
//		}
		
		File outFile = new File(outPath+File.separator+uname+File.separator+catName+File.separator+fileName);
		
		
		
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			inputStream = file.getInputStream();
			outputStream = new FileOutputStream(outFile);
 
			int read = 0;
			final byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
			}
		}
		
		//update the database
		//update file table and catalog table
		
		FileEntity fe = new FileEntity();
		
		Query q1 = em.createNativeQuery("SELECT * from USER where USERNAME = '"+uname+"' ", UserEntity.class);
		List<UserEntity> uel = q1.getResultList();
		
		if(uel.isEmpty()) {
			return false;
		}
		
		UserEntity ue = new UserEntity();
		ue = uel.get(0);
		
		fe.setName(fileName);
		fe.setOwner_id(ue.getId());
		fe.setCatalog_id(Long.parseLong(aid));
		fe.setDescription(description);
		fe.setTimestamp(new Timestamp(Calendar.getInstance().getTime().getTime()));
		fe.setUri(outPath+"/"+uname+"/"+catName+"/"+fileName);
		fe.setDownload_count(0);
		fe.setVisible(true);
		
		try{
			em.persist(fe);
			em.flush();
		} catch(Exception e) {
			return false;
		}
		
		boolean catSuccess = updateCatalogCount(aid);
		
		if(!catSuccess) {
			return false;
		}
		
		
		return true;
	}
	
	public boolean updateCatalogCount(String cid) {
		
		Query q = em.createNativeQuery("SELECT * from FILE where CATALOG_ID="+cid+" ",FileEntity.class);
		List<FileEntity> fel = q.getResultList();
		if(!fel.isEmpty()) {
			long n = fel.size();
			//update timestamp as well
			String time = String.valueOf(new Timestamp(Calendar.getInstance().getTime().getTime()));
			Query query = em.createNativeQuery("UPDATE CATALOG set SIZE = "+n+", DATETIME= '"+time+"' where ID = "+cid+" ");
			try {
			query.executeUpdate();
			em.flush();
			} catch (Exception e) {
				return false;
			}
			return true;
		}
		return false;
	}

	public String getFileName(Part file) {
		
		final String partHeader = file.getHeader("content-disposition");
		
		for (String content : file.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim()
						.replace("\"", "");
			}
		}
		return null;
	}
	
	public String getCatalogName(String cid) {
		
		Query q= em.createNativeQuery("SELECT * from CATALOG where ID="+cid+" ", CatalogEntity.class);
		List<CatalogEntity> cel = q.getResultList();
		CatalogEntity ce ;
		ce = cel.get(0);
		if(!cel.isEmpty()) {
			ce = new CatalogEntity();
			ce = cel.get(0);
			return ce.getName();
		}
		return null;
	}

}
