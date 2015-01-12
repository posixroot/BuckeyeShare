package eclipsetest.db.EJB.Entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name="USER")
public class UserEntity implements Serializable {
	
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
	
	@Column(name="FIRSTNAME")
	private String fname;
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getFname() {
		return fname;
	}

	@Column(name="LASTNAME")
	private String lname;
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getLname() {
		return lname;
	}
	
	@Column(name="USERNAME")
	private String uname;
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getUname() {
		return uname;
	}

	@Column(name="EMAIL")
	private String email;
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return email;
	}

	@Column(name="PASSWORD")
	private String password;
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	
	@Column(name="DATETIME")
	private Timestamp ts;
	public void setTs(Timestamp ts) {
		this.ts = ts;
	}
	public Timestamp getTs() {
		return ts;
	}

	@Column(name="PRIVILEGEACCESS")
	private boolean privaccess;
	public void setPrivaccess(boolean privaccess) {
		this.privaccess = privaccess;
	}
	public boolean getPrivaccess() {
		return privaccess;
	}
	

}
