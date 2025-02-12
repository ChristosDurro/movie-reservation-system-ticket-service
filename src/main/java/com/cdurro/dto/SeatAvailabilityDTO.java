package com.cdurro.dto;

import java.util.List;


public class SeatAvailabilityDTO {
	private List<Long> seatIdsToUpdate;
	private boolean availability;
	
	public List<Long> getSeatIdsToUpdate() {
		return seatIdsToUpdate;
	}
	public void setSeatIdsToUpdate(List<Long> seatIdsToUpdate) {
		this.seatIdsToUpdate = seatIdsToUpdate;
	}
	public boolean getAvailability() {
		return availability;
	}
	public void setAvailability(boolean availability) {
		this.availability = availability;
	}
	@Override
	public String toString() {
		return "SeatAvailabilityDTO [seatsToUpdate=" + seatIdsToUpdate + ", availability=" + availability + "]";
	}
}
