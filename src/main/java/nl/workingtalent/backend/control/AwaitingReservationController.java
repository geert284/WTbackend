package nl.workingtalent.backend.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import nl.workingtalent.backend.dto.AwaitingReservationDto;
import nl.workingtalent.backend.dto.CancelReservationDto;
import nl.workingtalent.backend.entity.Account;
import nl.workingtalent.backend.entity.AwaitingReservation;
import nl.workingtalent.backend.service.AccountService;
import nl.workingtalent.backend.service.AwaitingReservationService;

@CrossOrigin
@RestController
public class AwaitingReservationController {

	@Autowired
	private AwaitingReservationService service;
	
	@Autowired
	private AccountService accountService;

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
	public void cancelReservation(HttpServletRequest request, @RequestBody CancelReservationDto reservationDto) {
		// authentication part
		String authHeader = request.getHeader("Authorization");
		if (authHeader == null || authHeader.isBlank()) {
			System.out.println("Geen header mee gegeven");
			return;
		}
		Optional<Account> optional = accountService.findByToken(authHeader);
		if (optional.isEmpty()) {
			System.out.println("Account niet gevonden");
			return;
		}
		System.out.println("Account is gevonden met naam " + optional.get().getEmail());
		Account account = optional.get();
		// end authentication part

		Optional<AwaitingReservation> opAwaitingRes = service.findById(reservationDto.getReservationId());

		if (opAwaitingRes.isEmpty()) {
			System.out.println("Awaiting reservering niet gevonden");
			return;
		}
		AwaitingReservation awaitingReservation = opAwaitingRes.get();
		
		if(account != awaitingReservation.getAccount()) {
			System.out.println("Je kan alleen je eigen reserveringen cancelen");
			return;
		}
		
		awaitingReservation.setProcessed(true);
		service.update(awaitingReservation);
	}
	
	@RequestMapping("awaitingReservation/canceladmin")
	public void cancelReservationAdmin(HttpServletRequest request, @RequestBody CancelReservationDto reservationDto) {
		// authentication part
		String authHeader = request.getHeader("Authorization");
		if (authHeader == null || authHeader.isBlank()) {
			System.out.println("Geen header mee gegeven");
			return;
		}
		Optional<Account> optional = accountService.findByToken(authHeader);
		if (optional.isEmpty()) {
			System.out.println("Account niet gevonden");
			return;
		}
		System.out.println("Account is gevonden met naam " + optional.get().getEmail());
		Account account = optional.get();
		// end authentication part
		if (!account.isAdmin()) {
			System.out.println("Account is geen admin");
			return;
		}
		// end authentication part with admin check
		
		Optional<AwaitingReservation> opAwaitingRes = service.findById(reservationDto.getReservationId());

		if (opAwaitingRes.isEmpty()) {
			System.out.println("Awaiting reservering niet gevonden");
			return;
		}		
		
		AwaitingReservation awaitingReservation = opAwaitingRes.get();		
		awaitingReservation.setProcessed(true);
		service.update(awaitingReservation);
	}
}
