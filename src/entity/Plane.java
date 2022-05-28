package entity;

public class Plane {
	
	private String tailNumber;
	private int numOfFlightAttendants;
	
	public Plane(String tailNumber, int numOfFlightAttendants) {
		super();
		this.tailNumber = tailNumber;
		this.numOfFlightAttendants = numOfFlightAttendants;
	}
	
	public Plane(String tailNumber) {
		this.tailNumber = tailNumber;
	}

	public String getTailNumber() {
		return tailNumber;
	}

	public void setTailNumber(String tailNumber) {
		this.tailNumber = tailNumber;
	}

	public int getNumOfFlightAttendants() {
		return numOfFlightAttendants;
	}

	public void setNumOfFlightAttendants(int numOfFlightAttendants) {
		this.numOfFlightAttendants = numOfFlightAttendants;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tailNumber == null) ? 0 : tailNumber.hashCode());
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
		Plane other = (Plane) obj;
		if (tailNumber == null) {
			if (other.tailNumber != null)
				return false;
		} else if (!tailNumber.equals(other.tailNumber))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "tailNumber: " + tailNumber + "\n numOfFlightAttendants=" + numOfFlightAttendants;
	}
	

}
