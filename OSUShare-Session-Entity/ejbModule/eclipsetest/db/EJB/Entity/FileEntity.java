package eclipsetest.db.EJB.Entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FILE")
public class FileEntity implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private long id;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="NAME")
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="OWNER_ID")
	private long owner_id;
	public long getOwner_id() {
		return owner_id;
	}
	public void setOwner_id(long owner_id) {
		this.owner_id = owner_id;
	}
	
	@Column(name="DESCRIPTION")
	private String description;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name="DATETIME")
	private Timestamp timestamp;
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	@Column(name="VISIBLE")
	private boolean visible;
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	@Column(name="URI")
	private String uri;
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	@Column(name="CATALOG_ID")
	private long catalog_id;
	public long getCatalog_id() {
		return catalog_id;
	}
	public void setCatalog_id(long catalog_id) {
		this.catalog_id = catalog_id;
	}
	
	@Column(name="DOWNLOAD_COUNT")
	private long download_count;
	public long getDownload_count() {
		return download_count;
	}
	public void setDownload_count(long download_count) {
		this.download_count = download_count;
	}
	
}
