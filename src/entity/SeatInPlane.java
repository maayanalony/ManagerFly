package entity;

import java.util.Objects;

import enumeration.SeatClass;

public class SeatInPlane {
	
	private String tailNumber;
	private int lineNumber;
	private String seatNumber;
	private SeatClass seatClass;
	
	public SeatInPlane(int lineNumber, String seatNumber,String tailNumber, SeatClass seatClass) {
		super();
		this.tailNumber = tailNumber;
		this.lineNumber = lineNumber;
		this.seatNumber = seatNumber;
		this.seatClass = seatClass;
	}

	public String getTailNumber() {
		return tailNumber;
	}

	public void setTailNumber(String tailNumber) {
		this.tailNumber = tailNumber;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public SeatClass getSeatClass() {
		return seatClass;
	}

	public void setSeatClass(SeatClass seatClass) {
		this.seatClass = seatClass;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(lineNumber, seatNumber, tailNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SeatInPlane other = (SeatInPlane) obj;
		return lineNumber == other.lineNumber && Objects.equals(seatNumber, other.seatNumber)
				&& Objects.equals(tailNumber, other.tailNumber);
	}

	@Override
	public String toString() {
		return "" + lineNumber + " " + seatNumber+ " seatClass=" + seatClass;
	}
	
	

}
