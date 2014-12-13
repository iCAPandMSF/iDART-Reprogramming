package org.celllife.idart.database.hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity (name ="parameter")
public class Parameter {
	
	@Id
	@GeneratedValue
	private int Id;
	private String parameterName;
	private String parameterValue;
	
	public Parameter()
	{}
	
	public Parameter(int id, String parameterName,
			String parameterValue) {
		this.Id = id;
		this.parameterName = parameterName;
		this.parameterValue = parameterValue;
	}
	
	public int getParameterId() {
		return Id;
	}
	public void setParameterId(int id) {
		this.Id = id;
	}
	public String getParameterName() {
		return parameterName;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	public String getParameterValue() {
		return parameterValue;
	}
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
}
