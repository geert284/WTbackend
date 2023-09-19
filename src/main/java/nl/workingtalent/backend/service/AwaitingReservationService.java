package nl.workingtalent.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.workingtalent.backend.entity.AwaitingReservation;
import nl.workingtalent.backend.repository.IAwaitingReservationRepository;

@Service
public class AwaitingReservationService {

	@Autowired
	private IAwaitingReservationRepository repository;

	public List<AwaitingReservation> findAllAwaitingReservations() {
		return repository.findAll();
	}
	
	public void create(AwaitingReservation awaitingReservation) {
		repository.save(awaitingReservation);
	}

	public List<AwaitingReservation> findAllUnprocessed(){
		return repository.findByProcessedFalse();
	}
	
	public List<AwaitingReservation> findAllProcessed(){
		return repository.findByProcessedTrue();
	}
	
	public Optional<AwaitingReservation> findFirstByBook(long bookId){
		return repository.findFirstByProcessedFalseAndBookIdOrderByRequestDate(bookId);
	}
	
	public void update(AwaitingReservation awaitingReservation) {
		repository.save(awaitingReservation);
	}
}
