package org.celllife.idart.database.hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity (name ="sector")
public class Sector {
	
	@Id
	@GeneratedValue
	private int sectorid;
	private String sectorname;
	
	public Sector()
	{
		
	}
	
	public Sector(int sectorid, String sectorname) {
		sectorid = sectorid;
		this.sectorname = sectorname;
	}

	public int getSectorsectorid() {
		return sectorid;
	}

	public void setSectorsectorid(int sectorsectorid) {
		this.sectorid = sectorsectorid;
	}

	public String getSectorname() {
		return sectorname;
	}

	public void setSectorname(String sectorname) {
		this.sectorname = sectorname;
	}
}
