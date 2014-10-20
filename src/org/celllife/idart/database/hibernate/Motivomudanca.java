package org.celllife.idart.database.hibernate;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "motivomudanca")
public class Motivomudanca {
	
	@Id
	@GeneratedValue
	private int idmotivo;
	private String motivo;
	private boolean active;

	public Motivomudanca() {
		super();
		// TODO Auto-generated constructor stub
	}


	public int getIdmotivo() {
		return idmotivo;
	}


	public void setIdmotivo(int idmotivo) {
		this.idmotivo = idmotivo;
	}


	public String getMotivo() {
		return motivo;
	}


	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}


	public Motivomudanca(int idmotivo, String motivo, boolean active) {
		super();
		this.idmotivo = idmotivo;
		this.motivo = motivo;
		this.active=active;
	}


	public boolean isActive() {
		// TODO Auto-generated method stub
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


	
	

}
