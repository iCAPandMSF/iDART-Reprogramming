package org.celllife.idart.database.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * This class contains the drug stock rupture control 
 * @author Marcel Saraiva (Lunguissa)
 * @since November 2014
 */
@Entity (name ="drugstockcontrol")
public class DrugStockControl {
	
	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "drug"))
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "drugid", unique = true, nullable = false)
	private int drugId;
	
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Drug drug;
	
	private int amc;
	
	private int existingStock;
	
	private int orderQuantity;
	
	private String riskStatus;
	
	public DrugStockControl(){
	}
	
	public DrugStockControl(int drugId) {
		this.drugId = drugId;
		this.riskStatus = "Normal stock";
	}

	public int getDrugId() {
		return drugId;
	}

	public void setDrugId(int drugId) {
		this.drugId = drugId;
	}

	public int getAmc() {
		return amc;
	}

	public void setAmc(int amc) {
		this.amc = amc;
	}

	public String getRiskStatus() {
		return riskStatus;
	}

	public void setRiskStatus(String riskStatus) {
		this.riskStatus = riskStatus;
	}

	public Drug getDrug() {
		return drug;
	}

	public void setDrug(Drug drug) {
		this.drug = drug;
	}

	public int getExistingStock() {
		return existingStock;
	}

	public void setExistingStock(int existingStock) {
		this.existingStock = existingStock;
	}

	public int getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
}
