package com.cdurro.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cdurro.client.ReservationClient;
import com.cdurro.client.SeatClient;
import com.cdurro.dto.ReservationRequest;
import com.cdurro.dto.ReservationResponse;
import com.cdurro.dto.SeatAvailabilityDTO;
import com.cdurro.model.Seat;
import com.cdurro.model.Ticket;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;

@Service
public class StripeWebhookService {
	
	@Value("${stripe.webhook.secret}")
	private String endpointSecret;

	@Autowired
	private TicketService ticketService;

	@Autowired
	private SeatClient seatClient;
	
	@Autowired
	private ReservationClient reservationClient;
	
	public ResponseEntity<ReservationResponse> handleWebhook(String payload, String signature) {
		
		try {
			Event event = Webhook.constructEvent(payload, signature, endpointSecret);
			
			if ("checkout.session.completed".equals(event.getType())) {

				Session session = (Session) event.getDataObjectDeserializer().deserializeUnsafe();
				
				if (session != null) {
					Long userId = Long.parseLong(session.getMetadata().get("userId"));
					Long movieId = Long.parseLong(session.getMetadata().get("movieId"));
					Long scheduleId = Long.parseLong(session.getMetadata().get("scheduleId"));
					String[] seatsSelected = session.getMetadata().get("seatsSelected").split(",");

					List<Long> seatIds = new ArrayList<>();
					
					for (String seatId : seatsSelected) {
						seatIds.add(Long.parseLong(seatId));
					}

					List<Ticket> tickets = new ArrayList<>();
					
					for (Long seat : seatIds) {

						Ticket ticket = new Ticket();

						ticket.setUserId(userId);
						ticket.setMovieId(movieId);
						ticket.setScheduleId(scheduleId);
						ticket.setSeatId(seat);
						
						tickets.add(ticket);
					}
					
					
					tickets = ticketService.createTickets(tickets).getBody();

					// 2. Update Seat Availability
		            SeatAvailabilityDTO seatAvailabilityDTO = new SeatAvailabilityDTO();

		            seatAvailabilityDTO.setSeatIdsToUpdate(seatIds);
		            seatAvailabilityDTO.setAvailability(false);

		            List<Seat> seats = seatClient.updateMultipleSeats(seatAvailabilityDTO).getBody();

		            ReservationRequest request = new ReservationRequest();
		            
		            request.setTicketsList(tickets);
		            request.setSeats(seats);
		            request.setUserId(userId);
		            
		            ReservationResponse res = reservationClient.book(request).getBody();

		            return ResponseEntity.ok(res);
		        }

		        return ResponseEntity.badRequest().body(
		        		new ReservationResponse(
								false,
								"Unhandled event type: " + event.getType()
								)
		        		);
			}
			return ResponseEntity.ok(new ReservationResponse(
					false,
					"received"
					)
    		);
		} catch (SignatureVerificationException e) {
			return ResponseEntity.status(400).body(new ReservationResponse(
					false,
					"Invalid Signature"
					)
    		);
		} catch (Exception e) {
			return ResponseEntity.status(500).body(new ReservationResponse(
					false,
					"Error proccessing webhook!"
					)
    		);
		}
	}
}
