package nl.workingtalent.backend.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.workingtalent.backend.dto.AwaitingReservationDto;
import nl.workingtalent.backend.dto.CancelReservationDto;
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

	@RequestMapping("awaitingReservation/cancel")
	public void cancelReservation(@RequestBody CancelReservationDto reservationDto) {
		Optional<AwaitingReservation> opAwaitingRes = service.findById(reservationDto.getReservationId());
		System.out.println("test1");
		if (opAwaitingRes.isEmpty()) {
			return;
		}
		System.out.println("test2");
		AwaitingReservation awaitingReservation = opAwaitingRes.get();
		awaitingReservation.setProcessed(true);
		service.update(awaitingReservation);
	}
}
