package org.celllife.idart.database.hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class RegimeTerapeutico {
	
	@Id
	@GeneratedValue
	private int regimeid;
	private String regimeesquema;
	private boolean active;
	public int getRegimeid() {
		return regimeid;
	}
	public void setRegimeid(int regimeid) {
		this.regimeid = regimeid;
	}
	public String getRegimeesquema() {
		return regimeesquema;
	}
	public void setRegimeesquema(String regimeesquema) {
		this.regimeesquema = regimeesquema;
	}
	public RegimeTerapeutico(int regimeid, String regimeesquema, boolean active) {
		super();
		this.regimeid = regimeid;
		this.regimeesquema = regimeesquema;
		this.active=active;
	}
	public RegimeTerapeutico() {
		super();
		// TODO Auto-generated constructor stub
	}

	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	

}
