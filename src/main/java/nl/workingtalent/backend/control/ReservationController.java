package nl.workingtalent.backend.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.workingtalent.backend.entity.Reservation;
import nl.workingtalent.backend.service.ReservationService;

@CrossOrigin
@RestController
public class ReservationController {
	
	@Autowired
	private ReservationService service;
	
	@RequestMapping("reservation/all")
	public List<Reservation> getReservations() {
		return service.findAllReservations();
	}
}
