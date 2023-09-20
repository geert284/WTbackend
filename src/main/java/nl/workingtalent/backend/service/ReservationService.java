package nl.workingtalent.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.workingtalent.backend.entity.Book;
import nl.workingtalent.backend.entity.Loan;
import nl.workingtalent.backend.entity.Reservation;
import nl.workingtalent.backend.repository.IReservationRepository;

@Service
public class ReservationService {

	@Autowired
	private IReservationRepository repository;
	
	public List<Reservation> findAllReservations() {
		return repository.findAll();
	}
	
	public void create(Reservation reservation) {
		repository.save(reservation);
	}
	
	public void update(Reservation reservation) {
		repository.save(reservation);
	}

	public List<Reservation> findAllUnprocessed(){
		return repository.findByProcessedFalse();
	}
	
	public List<Reservation> findAllProcessed(){
		return repository.findByProcessedTrue();
	}
	
	public Optional<Reservation> findById(long id){
		return repository.findById(id);
	}
	
	public List<Reservation> findAllForAccount(long id){
		return repository.findByAccountIdAndProcessedFalse(id);
	}
}
