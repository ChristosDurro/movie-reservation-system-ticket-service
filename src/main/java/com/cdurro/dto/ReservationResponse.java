package com.cdurro.dto;

import java.util.List;

public class ReservationResponse {

	private boolean success;
	private String message;
	private List<Long> ticketIds;
	private List<Long> seatIds;
	private Long userId;

	public ReservationResponse(boolean success, String message) {
		this.success = success;
		this.message = message;
	}
	public ReservationResponse() {
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<Long> getTicketIds() {
		return ticketIds;
	}
	public void setTicketIds(List<Long> ticketIds) {
		this.ticketIds = ticketIds;
	}
	public List<Long> getSeatIds() {
		return seatIds;
	}
	public void setSeatIds(List<Long> seatIds) {
		this.seatIds = seatIds;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "ReservationResponse [success=" + success + ", message=" + message + ", ticketIds=" + ticketIds
				+ ", seatIds=" + seatIds + ", userId=" + userId + "]";
	}
}
