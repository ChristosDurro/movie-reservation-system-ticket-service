package com.cdurro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdurro.dto.ReservationResponse;
import com.cdurro.service.StripeWebhookService;

@RestController
@RequestMapping("/webhook")
public class StripeWebhookController {
	
	@Autowired
	private StripeWebhookService stripeWebhookService;
	
	@PostMapping
	public ResponseEntity<ReservationResponse> handleStripeWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String signature) {
		
		return stripeWebhookService.handleWebhook(payload, signature);
	}
}
