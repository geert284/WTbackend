package nl.workingtalent.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.workingtalent.backend.entity.Reservation;
import nl.workingtalent.backend.repository.IReservationRepository;

@Service
public class ReservationService {

	@Autowired
	private IReservationRepository repository;
	
	public List<Reservation> findAllReservations() {
		return repository.findAll();
	}

}