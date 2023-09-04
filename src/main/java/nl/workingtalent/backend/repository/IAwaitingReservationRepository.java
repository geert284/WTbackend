package nl.workingtalent.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.workingtalent.backend.entity.AwaitingReservation;

public interface IAwaitingReservationRepository extends JpaRepository<AwaitingReservation, Long> {

}
