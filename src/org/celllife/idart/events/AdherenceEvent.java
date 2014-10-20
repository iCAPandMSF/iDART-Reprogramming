package org.celllife.idart.events;

import java.util.Set;

import org.celllife.idart.database.hibernate.PillCount;

public class AdherenceEvent {

	private final Set<PillCount> pcs;

	public AdherenceEvent(Set<PillCount> pcs) {
		this.pcs = pcs;
	}
	
	public Set<PillCount> getPillCounts() {
		return pcs;
	}
}
