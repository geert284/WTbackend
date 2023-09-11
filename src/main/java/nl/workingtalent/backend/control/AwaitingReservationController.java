package nl.workingtalent.backend.control;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.workingtalent.backend.dto.AwaitingReservationDto;
import nl.workingtalent.backend.entity.AwaitingReservation;
import nl.workingtalent.backend.service.AwaitingReservationService;

@CrossOrigin
@RestController
public class AwaitingReservationController {

	@Autowired
	private AwaitingReservationService service;

	@RequestMapping("awaitingreservation/all")
	public List<AwaitingReservationDto> getAwaitingReservation() {
		List<AwaitingReservation> awaitingReservations = service.findAllAwaitingReservations();

		List<AwaitingReservationDto> dtos = new ArrayList<>();

		awaitingReservations.forEach(aRes -> {
			AwaitingReservationDto dto = new AwaitingReservationDto();
			dto.setId(aRes.getId());
			dto.setProcessed(aRes.isProcessed());
			dto.setAccount_id(aRes.getAccount().getId());
			dto.setBook_id(aRes.getBook().getId());
			dto.setRequestDate(aRes.getRequestDate());

			dtos.add(dto);
		});

		return dtos;

	}
}
