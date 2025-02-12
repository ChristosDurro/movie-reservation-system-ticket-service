package com.cdurro.dto;

import java.util.List;

import com.cdurro.model.Seat;
import com.cdurro.model.Ticket;

public class ReservationRequest {
	
	private Long userId;
	private List<Ticket> ticketsList;
	private List<Seat> seats;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public List<Ticket> getTicketsList() {
		return ticketsList;
	}
	public void setTicketsList(List<Ticket> ticketsList) {
		this.ticketsList = ticketsList;
	}
	public List<Seat> getSeats() {
		return seats;
	}
	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}
	@Override
	public String toString() {
		return "ReservationRequest [userId=" + userId + ", ticketsList=" + ticketsList + ", seats=" + seats + "]";
	}
}
