package nl.workingtalent.backend.service;

import java.util.List;

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

}
