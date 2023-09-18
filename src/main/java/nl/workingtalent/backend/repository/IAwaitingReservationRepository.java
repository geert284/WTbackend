package nl.workingtalent.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.workingtalent.backend.entity.AwaitingReservation;
import nl.workingtalent.backend.entity.Reservation;

public interface IAwaitingReservationRepository extends JpaRepository<AwaitingReservation, Long> {

	List<AwaitingReservation> findByProcessedFalse();
	
	List<AwaitingReservation> findByProcessedTrue();
}
