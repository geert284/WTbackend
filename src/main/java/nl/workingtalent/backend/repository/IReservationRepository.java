package nl.workingtalent.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.workingtalent.backend.entity.Reservation;

public interface IReservationRepository extends JpaRepository<Reservation, Long>{

	List<Reservation> findByProcessedFalse();
	
	List<Reservation> findByProcessedTrue();
}
