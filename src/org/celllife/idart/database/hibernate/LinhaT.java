
package org.celllife.idart.database.hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class LinhaT {
	
	@Id
	@GeneratedValue
	private int linhaid;
	private String linhanome;
	private boolean active;
	


	public LinhaT(int linhaid, String linhanome, boolean active) {
		super();
		this.linhaid = linhaid;
		this.linhanome = linhanome;
		this.active = active;
	}

	public int getLinhaid() {
		return linhaid;
	}

	public void setLinhaid(int linhaid) {
		this.linhaid = linhaid;
	}

	public String getLinhanome() {
		return linhanome;
	}

	public void setLinhanome(String linhanome) {
		this.linhanome = linhanome;
	}

	public LinhaT() {
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
