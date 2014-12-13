package org.celllife.idart.database.hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity (name ="regimeterapeuticodrugs")
public class RegimeTerapeuticoDrugs implements Comparable<RegimeTerapeuticoDrugs>{
	
	@Id
	@GeneratedValue
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "regimeid")
	private RegimeTerapeutico regime;
	
	@ManyToOne
	@JoinColumn(name = "chemicalid")
	private ChemicalCompound chemicalCompound;
	
	public RegimeTerapeuticoDrugs()
	{}
	
	public RegimeTerapeuticoDrugs(RegimeTerapeutico regime,
			ChemicalCompound chemicalCompound) {
		this.id = 0;
		this.regime = regime;
		this.chemicalCompound = chemicalCompound;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public RegimeTerapeutico getRegime() {
		return regime;
	}

	public void setRegime(RegimeTerapeutico regime) {
		this.regime = regime;
	}

	public ChemicalCompound getChemicalCompound() {
		return chemicalCompound;
	}

	public void setChemicalCompound(ChemicalCompound chemicalCompound) {
		this.chemicalCompound = chemicalCompound;
	}

	@Override
	public int compareTo(RegimeTerapeuticoDrugs o) {
	    return this.getChemicalCompound().getAcronym().compareToIgnoreCase(o.getChemicalCompound().getAcronym());
	}
}
