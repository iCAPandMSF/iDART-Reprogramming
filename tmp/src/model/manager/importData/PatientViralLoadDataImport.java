package model.manager.importData;

import java.util.Date;

public class PatientViralLoadDataImport {

	private Integer id;
	private Boolean highViralLoad;
	private Date resultDate;
	
	public PatientViralLoadDataImport(Integer id, Boolean highViralLoad,
			Date resultDate) {
		super();
		this.id = id;
		this.highViralLoad = highViralLoad;
		this.resultDate = resultDate;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
		
}
