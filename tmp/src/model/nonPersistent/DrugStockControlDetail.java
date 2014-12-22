package model.nonPersistent;

public class DrugStockControlDetail {

	private String drug;
	
	private String amc;
	
	private String existingstock;
	
	private String orderquantity;
	
	private String riskstatus;

	public DrugStockControlDetail(String drug, Integer amc, Integer existingStock,
			Integer orderQuantity, String riskStatus) {
		super();
		this.drug = drug;
		this.amc = amc.intValue()+"";
		this.existingstock = existingStock.intValue()+"";
		this.orderquantity = orderQuantity.intValue()+"";
		this.riskstatus = riskStatus;
	}

	public String getDrug() {
		return drug;
	}

	public void setDrug(String drug) {
		this.drug = drug;
	}

	public String getAmc() {
		return amc;
	}

	public void setAmc(String amc) {
		this.amc = amc;
	}

	public String getExistingstock() {
		return existingstock;
	}

	public void setExistingstock(String existingstock) {
		this.existingstock = existingstock;
	}

	public String getOrderquantity() {
		return orderquantity;
	}

	public void setOrderquantity(String orderquantity) {
		this.orderquantity = orderquantity;
	}

	public String getRiskstatus() {
		return riskstatus;
	}

	public void setRiskstatus(String riskstatus) {
		this.riskstatus = riskstatus;
	}
}
