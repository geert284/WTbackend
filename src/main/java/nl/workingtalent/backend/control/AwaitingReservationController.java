package nl.workingtalent.backend.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.workingtalent.backend.entity.AwaitingReservation;
import nl.workingtalent.backend.service.AwaitingReservationService;

@CrossOrigin
@RestController
public class AwaitingReservationController {

	@Autowired
	private AwaitingReservationService service;

	@RequestMapping("awaitingreservation/all")
	public List<AwaitingReservation> getAwaitingReservation() {
		return service.findAllAwaitingReservations();
	}
}
