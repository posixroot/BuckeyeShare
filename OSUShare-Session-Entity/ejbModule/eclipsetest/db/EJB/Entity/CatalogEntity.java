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
@Table(name = "CATALOG")
public class CatalogEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private long id;
	public void setId(long id) {
		this.id = id;
	}
	public long getId() {
		return id;
	}
	
	@Column(name = "NAME")
	private String name;
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	@Column(name = "OWNER_ID")
	private long owner_id;
	public void setOwner_id(long owner_id) {
		this.owner_id = owner_id;
	}
	public long getOwner_id() {
		return owner_id;
	}
	
	@Column(name = "DESCRIPTION")
	private String description;
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	
	@Column(name = "SIZE")
	private long size;
	public void setSize(long size) {
		this.size = size;
	}
	public long getSize() {
		return size;
	}

	
	@Column(name = "DATETIME")
	private Timestamp timestamp;
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	
	@Column(name = "VISIBLE")
	private boolean visible;
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public boolean isVisible() {
		return visible;
	}
	
//	@Column(name = "URI")
//	private String uri;
//	public void setUri(String uri) {
//		this.uri = uri;
//	}
//	public String getUri() {
//		return uri;
//	}	
	
}
