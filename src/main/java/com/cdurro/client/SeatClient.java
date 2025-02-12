package com.cdurro.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cdurro.dto.SeatAvailabilityDTO;
import com.cdurro.model.Seat;

@FeignClient("SEAT-SERVICE")
public interface SeatClient {
	
	@PutMapping("/seats/update/multiple")
	public ResponseEntity<List<Seat>> updateMultipleSeats(@RequestBody SeatAvailabilityDTO body);
}
