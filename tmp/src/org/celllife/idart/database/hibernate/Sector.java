package org.celllife.idart.database.hibernate;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity (name ="sector")
public class Sector implements Comparable<Sector>{
	
	@Id
	private int sectorid;
	private String sectorname;
	
	public Sector()
	{
		
	}
	
	public Sector(int sectorid, String sectorname) {
		this.sectorid = sectorid;
		this.sectorname = sectorname;
	}

	public int getSectorid() {
		return sectorid;
	}

	public void setSectorid(int sectorsectorid) {
		this.sectorid = sectorsectorid;
	}

	public String getSectorname() {
		return sectorname;
	}

	public void setSectorname(String sectorname) {
		this.sectorname = sectorname;
	}
	

	@Override
	public int compareTo(Sector o) {
		final int BEFORE = -1;
	    final int EQUAL = 0;
	    final int AFTER = 1;

	    //this optimization is usually worthwhile, and can
	    //always be added
	    if (this.getSectorid() == o.getSectorid()) return EQUAL;

	    //primitive numbers follow this form
	    if (this.getSectorid() < o.getSectorid()) return BEFORE;
	    if (this.getSectorid() > o.getSectorid()) return AFTER;
	    return EQUAL;
	}

	
	
}
