package com.cdurro.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cdurro.dto.ReservationRequest;
import com.cdurro.dto.ReservationResponse;

@FeignClient("RESERVATION-SERVICE")
public interface ReservationClient {

	@PostMapping("/reservations/book")
	public ResponseEntity<ReservationResponse> book(@RequestBody ReservationRequest request);
}
