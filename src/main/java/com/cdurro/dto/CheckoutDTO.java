package com.cdurro.dto;

import java.util.List;

public class CheckoutDTO {
	private Long userId;
	private Long movieId;
	private Long scheduleId;
	private List<Long> selectedSeatsIds;
	private int ticketQuantity;

	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getMovieId() {
		return movieId;
	}
	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}
	public Long getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}
	public List<Long> getSelectedSeatsIds() {
		return selectedSeatsIds;
	}
	public void setSelectedSeatsIds(List<Long> selectedSeatsIds) {
		this.selectedSeatsIds = selectedSeatsIds;
	}
	public int getTicketQuantity() {
		return ticketQuantity;
	}
	public void setTicketQuantity(int ticketQuantity) {
		this.ticketQuantity = ticketQuantity;
	}
	@Override
	public String toString() {
		return "CheckoutDTO [userId=" + userId + ", movieId=" + movieId + ", scheduleId=" + scheduleId
				+ ", selectedSeatsIds=" + selectedSeatsIds + ", ticketQuantity=" + ticketQuantity + "]";
	}
}
