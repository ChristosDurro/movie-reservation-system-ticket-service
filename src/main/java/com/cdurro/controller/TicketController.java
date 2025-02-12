package com.cdurro.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.cdurro.dto.CheckoutDTO;
import com.cdurro.model.Ticket;
import com.cdurro.service.TicketService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class TicketController {
	
	@Autowired
	private TicketService ticketService;
	
	@GetMapping("/tickets")
	public ResponseEntity<List<Ticket>> getAllTickets() {
		
		return ticketService.getTickets();
	}
	
	@GetMapping("/tickets/user/{userId}")
	public ResponseEntity<List<Ticket>> getAllTicketsByUser(@PathVariable Long userId) {
		
		return ticketService.getTicketsByUser(userId);
	}

	@GetMapping("/tickets/movie/{movieId}")
	public ResponseEntity<List<Ticket>> getAllTicketsByMovie(@PathVariable Long movieId) {
		
		return ticketService.getTicketsByUser(movieId);
	}

	@GetMapping("/tickets/schedule/{scheduleId}")
	public ResponseEntity<List<Ticket>> getAllTicketsBySchedule(@PathVariable Long scheduleId) {
		
		return ticketService.getTicketsByUser(scheduleId);
	}
	
	@GetMapping("/tickets/{id}") 
	public ResponseEntity<Ticket> getTicket(@PathVariable Long id){
		
		return ticketService.getTicket(id);
	}
	
	@PostMapping("/tickets/create")
	public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
		
		return ticketService.createTicket(ticket);
	}
	
	@PostMapping("/tickets/multiple/create")
	public ResponseEntity<List<Ticket>> createTickets(@RequestBody List<Ticket> tickets) {
		
		return ticketService.createTickets(tickets);
	}
	
	@PostMapping("/tickets/create-checkout-session")
	public ResponseEntity<Map<String, String>> checkoutSession(@RequestBody CheckoutDTO request) {
		
		return ticketService.createCheckout(request);
	}
	
	@DeleteMapping("/tickets/delete/{id}")
	public Ticket deleteTicket(@PathVariable Long id) {
		
		return ticketService.deleteTicket(id);
	}
	
}
