package com.cdurro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cdurro.model.Ticket;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, Long> {
	public List<Ticket> findAllByUserId(Long userId);
	public List<Ticket> findAllByMovieId(Long movieId);
	public List<Ticket> findAllByScheduleId(Long scheduleId);
	public List<Ticket> findAllByUserIdInAndMovieIdInAndScheduleIdInAndSeatIdIn(
			List<Long> userIds,
			List<Long> movieIds,
			List<Long> scheduleIds,
			List<Long> seatIds
			);
}
