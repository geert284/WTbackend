package nl.workingtalent.backend.control;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.workingtalent.backend.dto.ReservationDto;
import nl.workingtalent.backend.entity.Reservation;
import nl.workingtalent.backend.service.ReservationService;

@CrossOrigin
@RestController
public class ReservationController {
	
	@Autowired
	private ReservationService service;
	
	@RequestMapping("reservation/all")
	public List<ReservationDto> getReservations() {
		List<Reservation> reservations = service.findAllReservations();
		
		List<ReservationDto> dtos = new ArrayList<>();
		
		reservations.forEach(reservation ->{
			ReservationDto dto = new ReservationDto();
			dto.setId(reservation.getId());
			dto.setProcessed(reservation.isProcessed());
			dto.setReservationDate(reservation.getReservationDate());
			dto.setAccount_id(reservation.getAccount().getId());
			dto.setBookCopy_id(reservation.getBookCopy().getId());
			
			dtos.add(dto);
		});
		
		return dtos;
	}
}
