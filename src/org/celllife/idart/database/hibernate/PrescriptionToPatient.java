package org.celllife.idart.database.hibernate;

import java.util.Date;


/**
 * Classe que irá suportar o processo de dispensa para o MS ACCESS
 * @author EdiasJambaia
 *
 */


public class PrescriptionToPatient {
	

	private int  id;
	 private String current;
	 private int duration;
	 private String reasonforupdate;
	 private String notes;
	 private String patientid;
	 private String regimeesquema;
	 private int  idade;
	 private String motivomudanca;
	 private Date dataInicionoutroservico;
	 private String linha;
	 
	
	public PrescriptionToPatient(int id, String current, int duration, String reasonforupdate, String notes,
			String patientid, String regimeesquema, int idade, String motivomudanca, Date dataInicionoutroservico, String linha) {
		super();
		this.id = id;
		this.current = current;
		this.duration = duration;
		this.reasonforupdate = reasonforupdate;
		this.notes = notes;
		this.patientid = patientid;
		this.regimeesquema = regimeesquema;
		this.idade = idade;
		this.motivomudanca = motivomudanca;
		this.dataInicionoutroservico = dataInicionoutroservico;
		this.linha=linha;
	}


	public String getMotivomudanca() {
		return motivomudanca;
	}

	public String getLinha() {
		return linha;
	}
	
	public void setLinha(String linha) {
		this.linha = linha;
	}
	
	
	public void setMotivomudanca(String motivomudanca) {
		this.motivomudanca = motivomudanca;
	}


	public Date getDataInicionoutroservico() {
		return dataInicionoutroservico;
	}


	public void setDataInicionoutroservico(Date dataInicionoutroservico) {
		this.dataInicionoutroservico = dataInicionoutroservico;
	}


	public PrescriptionToPatient() {
	
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCurrent() {
		return current;
	}
	public void setCurrent(String current) {
		this.current = current;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getReasonforupdate() {
		return reasonforupdate;
	}
	public void setReasonforupdate(String reasonforupdate) {
		this.reasonforupdate = reasonforupdate;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getPatientid() {
		return patientid;
	}
	public void setPatientid(String patientid) {
		this.patientid = patientid;
	}
	public String getRegimeesquema() {
		return regimeesquema;
	}
	public void setRegimeesquema(String regimeesquema) {
		this.regimeesquema = regimeesquema;
	}
	public int getIdade() {
		return idade;
	}
	public void setIdade(int idade) {
		this.idade = idade;
	}
	
	

}
