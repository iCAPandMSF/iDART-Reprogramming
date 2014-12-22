package org.celllife.idart.database.hibernate;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class AtcCode implements Comparable<AtcCode> {

	@Id
	@GeneratedValue
	private Integer id;

	private String name;

	private String code;
	
	private String mims;
	
	@ManyToMany(mappedBy="atccodes")
	private Set<ChemicalCompound> chemicalCompounds = new HashSet<ChemicalCompound>();
	
	public AtcCode() {
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMims() {
		return mims;
	}

	public void setMims(String mims) {
		this.mims = mims;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
	@Override
	public int compareTo(AtcCode a2) {
		return this.getName().compareToIgnoreCase(a2.getName());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AtcCode other = (AtcCode) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	public void setChemicalCompounds(Set<ChemicalCompound> chemicalCompounds) {
		this.chemicalCompounds = chemicalCompounds;
	}

	public Set<ChemicalCompound> getChemicalCompounds() {
		return chemicalCompounds;
	}
	
	public boolean containsExactChemicalCompounds(Collection<ChemicalCompound> cpds){
		if (chemicalCompounds.size() != cpds.size())
			return false;
		
		for (ChemicalCompound ccd : cpds) {
			if (!chemicalCompounds.contains(ccd)){
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AtcCode [name=").append(name).append(", code=")
				.append(code).append("]");
		return builder.toString();
	}
	
	public String getDisplayName(){
		return name + " (" + code + ")";
	}
	
	
}
