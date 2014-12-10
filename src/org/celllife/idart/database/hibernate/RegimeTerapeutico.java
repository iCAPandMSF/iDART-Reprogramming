package org.celllife.idart.database.hibernate;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;

@Entity (name ="regimeterapeutico")
public class RegimeTerapeutico {
	
	@Id
	@GeneratedValue
	private int regimeid;
	private String regimeesquema;
	private boolean active;
	private boolean adult;
	
	@ManyToOne
	@JoinColumn(name = "linhaid")
	private LinhaT linhaT;
	@OneToMany(mappedBy = "regime")
	@Cascade( { org.hibernate.annotations.CascadeType.ALL,
		org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
	private Set<RegimeTerapeuticoDrugs> regimeDrugs;
	
	
	public RegimeTerapeutico(int regimeid, String regimeesquema,
			boolean active, boolean adult) {
		this.regimeid = regimeid;
		this.regimeesquema = regimeesquema;
		this.active = active;
		this.adult = adult;
		this.linhaT = linhaT;
		this.regimeDrugs = regimeDrugs;
	}

	public RegimeTerapeutico() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public boolean isAdult() {
		return adult;
	}
	public void setAdult(boolean adult) {
		this.adult = adult;
	}
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
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public LinhaT getLinhaT() {
		return linhaT;
	}
	public void setLinhaT(LinhaT linhaT) {
		this.linhaT = linhaT;
	}
	public Set<RegimeTerapeuticoDrugs> getRegimeDrugs() {
		return regimeDrugs;
	}
	public void setRegimeDrugs(Set<RegimeTerapeuticoDrugs> regimeDrugs) {
		this.regimeDrugs = regimeDrugs;
	}
}
