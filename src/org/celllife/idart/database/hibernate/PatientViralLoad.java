package org.celllife.idart.database.hibernate;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity (name ="patientviralload")
public class PatientViralLoad {

		@Id
		@GeneratedValue
		private Integer id;
		@ManyToOne
		@JoinColumn(name = "patient_id")
		private Patient patient;
		private Boolean highViralLoad;
		private Date resultDate;
		private Boolean recommendedToCounselor;
		private Date counselingDate;
		private Boolean belongsGaac;
		private Integer gaacNumber;
		
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public Patient getPatient() {
			return patient;
		}
		public void setPatient(Patient patient) {
			this.patient = patient;
		}
		public Boolean getHighViralLoad() {
			return highViralLoad;
		}
		public void setHighViralLoad(Boolean highViralLoad) {
			this.highViralLoad = highViralLoad;
		}
		public Date getResultDate() {
			return resultDate;
		}
		public void setResultDate(Date resultDate) {
			this.resultDate = resultDate;
		}
		public Boolean getRecommendedToCounselor() {
			return recommendedToCounselor;
		}
		public void setRecommendedToCounselor(Boolean recommendedToCounselor) {
			this.recommendedToCounselor = recommendedToCounselor;
		}
		public Date getCounselingDate() {
			return counselingDate;
		}
		public void setCounselingDate(Date counselingDate) {
			this.counselingDate = counselingDate;
		}
		public Boolean getBelongsGaac() {
			return belongsGaac;
		}
		public void setBelongsGaac(Boolean belongsGaac) {
			this.belongsGaac = belongsGaac;
		}
		public Integer getGaacNumber() {
			return gaacNumber;
		}
		public void setGaacNumber(Integer gaacNumber) {
			this.gaacNumber = gaacNumber;
		}
		
}
