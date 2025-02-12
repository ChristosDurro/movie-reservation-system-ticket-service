package com.cdurro.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cdurro.dto.CheckoutDTO;
import com.cdurro.model.Ticket;
import com.cdurro.repository.TicketRepo;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@Service
public class TicketService {
	
	@Autowired
	TicketRepo repo;

	@Value("${stripe.api.key}")
	private String stripeApiKey;
	
	public ResponseEntity<List<Ticket>> getTickets() {
		
		List<Ticket> tickets = repo.findAll();
		
		return ResponseEntity.ok(tickets);
	}
	
	public ResponseEntity<List<Ticket>> getTicketsByUser(Long userId) {
		
		List<Ticket> tickets = repo.findAllByUserId(userId);
		
		return ResponseEntity.ok(tickets);
	}
	
	public ResponseEntity<List<Ticket>> getTicketsByMovie(Long movieId) {
		
		List<Ticket> tickets = repo.findAllByMovieId(movieId);
		
		return ResponseEntity.ok(tickets);
	}

	public ResponseEntity<List<Ticket>> getTicketsBySchedule(Long scheduleId) {
		
		List<Ticket> tickets = repo.findAllByUserId(scheduleId);
		
		return ResponseEntity.ok(tickets);
	}
	

	public ResponseEntity<Ticket> getTicket(Long id) {
		
		Ticket ticket = repo.findById(id).orElse(null);
		
		return ResponseEntity.ok(ticket);
	}
	

	public ResponseEntity<Ticket> createTicket(Ticket ticket) {
		
		Ticket ticketSaved = repo.save(ticket);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(ticketSaved);
	}
	
	public ResponseEntity<List<Ticket>> createTickets(List<Ticket> tickets) {
		
		List<Long> userIds = tickets.stream().map(Ticket::getUserId).toList();
		List<Long> movieIds = tickets.stream().map(Ticket::getMovieId).toList();
		List<Long> scheduleIds = tickets.stream().map(Ticket::getScheduleId).toList();
		List<Long> seatIds = tickets.stream().map(Ticket::getSeatId).toList();
		
		List<Ticket> existingTickets = repo.findAllByUserIdInAndMovieIdInAndScheduleIdInAndSeatIdIn(
				userIds,
				movieIds,
				scheduleIds,
				seatIds
		);

		if (!existingTickets.isEmpty()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
		}

		List<Ticket> ticketsSaved = repo.saveAll(tickets);

		return ResponseEntity.status(HttpStatus.CREATED).body(ticketsSaved);
	}

	public ResponseEntity<Map<String, String>> createCheckout(CheckoutDTO request) {
		

		Stripe.apiKey = stripeApiKey;
		
		Map<String, String> response = new HashMap<>();
		

		try {
			SessionCreateParams.LineItem.PriceData.ProductData productData =
					SessionCreateParams.LineItem.PriceData.ProductData.builder()
						.setName("Movie Ticket(s)")
						.setDescription("Movie ID: " + request.getMovieId() + 
								", Schedule ID: " + request.getScheduleId() +
								", Seats: " + request.getSelectedSeatsIds())
						.build();
			
			SessionCreateParams.LineItem.PriceData priceData = 
					SessionCreateParams.LineItem.PriceData.builder()
						.setCurrency("eur")
						.setUnitAmount(950L)
						.setProductData(productData)
						.build();
			
			SessionCreateParams.LineItem lineItem =
					SessionCreateParams.LineItem.builder()
						.setQuantity((long) request.getTicketQuantity())
						.setPriceData(priceData)
						.build();
			
			SessionCreateParams params = 
					SessionCreateParams.builder()
						.setMode(SessionCreateParams.Mode.PAYMENT)
						.setSuccessUrl("http://localhost:5173/success?session_id={CHECKOUT_SESSION_ID}&scheduleId=" + request.getScheduleId() + "&seatsSelected=" + request.getSelectedSeatsIds())
						.setCancelUrl("http://localhost:5173/cancel")
						.addLineItem(lineItem)
						.putMetadata("userId", request.getUserId().toString())
			            .putMetadata("movieId", request.getMovieId().toString())
			            .putMetadata("scheduleId", request.getScheduleId().toString())
			            .putMetadata("seatsSelected", request.getSelectedSeatsIds().stream().map(String::valueOf).collect(Collectors.joining(",")))
						.build();
			
			Session session = Session.create(params);
			
			response.put("url", session.getUrl());
			
		} catch (StripeException e) {
			
			e.printStackTrace();
			response.put("error", e.getMessage());
		}
		
		return ResponseEntity.ok(response);
	}

	public Ticket deleteTicket(Long id) {
		
		repo.deleteById(id);
		
		return repo.findById(id).orElse(null);
	}
}
