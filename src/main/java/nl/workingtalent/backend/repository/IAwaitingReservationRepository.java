package nl.workingtalent.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.workingtalent.backend.entity.AwaitingReservation;

public interface IAwaitingReservationRepository extends JpaRepository<AwaitingReservation, Long> {

	List<AwaitingReservation> findByProcessedFalse();
	
	List<AwaitingReservation> findByProcessedTrue();
	
	Optional<AwaitingReservation> findFirstByProcessedFalseAndBookIdOrderByRequestDate(long bookId);
}
