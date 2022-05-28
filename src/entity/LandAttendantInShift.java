package entity;

import enumeration.LandAttendantRole;

public class LandAttendantInShift {
	
	private Shift shift;
	private LandAttendant landAttendant;
	private LandAttendantRole role;
	
	public LandAttendantInShift(Shift shift, LandAttendant landAttendant, LandAttendantRole role) {
		super();
		this.shift = shift;
		this.landAttendant = landAttendant;
		this.role = role;
	}

	public Shift getShift() {
		return shift;
	}

	public void setShift(Shift shift) {
		this.shift = shift;
	}

	public LandAttendant getLandAttendant() {
		return landAttendant;
	}

	public void setLandAttendant(LandAttendant landAttendant) {
		this.landAttendant = landAttendant;
	}

	public LandAttendantRole getRole() {
		return role;
	}

	public void setRole(LandAttendantRole role) {
		this.role = role;
	}
	
	
	
	
	
	
	
	
}
