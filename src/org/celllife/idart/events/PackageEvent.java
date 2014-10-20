package org.celllife.idart.events;

import org.celllife.idart.database.hibernate.Packages;

public class PackageEvent {
	
	private final Type type;
	private final Packages pack;

	public static enum Type {
		PACKAGE_FOR_LATER,
		PACKAGE_AND_PICKUP,
		PICKUP_ONLY,
		RETURN,
		DELETE
	}
	
	public PackageEvent(Type type, Packages pack) {
		this.type = type;
		this.pack = pack;
	}

	public Type getType() {
		return type;
	}
	
	public Packages getPack() {
		return pack;
	}
}
