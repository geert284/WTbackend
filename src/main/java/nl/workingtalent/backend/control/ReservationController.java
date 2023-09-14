package nl.workingtalent.backend.control;

import java.io.Console;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.workingtalent.backend.dto.CreateReservationDto;
import nl.workingtalent.backend.dto.ReservationDto;
import nl.workingtalent.backend.entity.Account;
import nl.workingtalent.backend.entity.AwaitingReservation;
import nl.workingtalent.backend.entity.Book;
import nl.workingtalent.backend.entity.BookCopy;
import nl.workingtalent.backend.entity.Reservation;
import nl.workingtalent.backend.service.AccountService;
import nl.workingtalent.backend.service.AwaitingReservationService;
import nl.workingtalent.backend.service.BookCopyService;
import nl.workingtalent.backend.service.BookService;
import nl.workingtalent.backend.service.ReservationService;

@CrossOrigin
@RestController
public class ReservationController {

	@Autowired
	private ReservationService service;

	@Autowired
	private BookCopyService bookCopyService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private AwaitingReservationService awaitingReservationService;

	@RequestMapping("reservation/all")
	public List<ReservationDto> getReservations() {
		List<Reservation> reservations = service.findAllReservations();

		List<ReservationDto> dtos = new ArrayList<>();

		reservations.forEach(reservation -> {
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

	@PostMapping("reservation/create")
	public void createReservation(@RequestBody CreateReservationDto dto) {
		Optional<Book> opBook = bookService.findById(dto.getBookId());
		if (opBook.isEmpty())
			return;
		
		// find available bookCopy with correct id
		Optional<BookCopy> opbookCopy = bookCopyService.vindEerstBeschikbaarBookCopy(opBook.get());
		if (opbookCopy.isEmpty())
			return;

		// get account	
		Optional<Account> opAccount = accountService.findById(dto.getAccountId());
		
		if (bookCopy != null) {
			System.out.println(bookCopy.getId());
			Reservation reservation = new Reservation();
					
			reservation.setAccount(opAccount.get());
			reservation.setBookCopy(bookCopy);
			reservation.setProcessed(false);
			reservation.setReservationDate(java.sql.Timestamp.valueOf(LocalDateTime.now()));
			
			service.create(reservation);
		}
		else {
			Optional<Book> book = bookService.findById(dto.getBookId());
			
			AwaitingReservation awaitingReservation = new AwaitingReservation();
			
			awaitingReservation.setAccount(opAccount.get());
			awaitingReservation.setBook(book.get());
			awaitingReservation.setProcessed(false);
			awaitingReservation.setRequestDate(java.sql.Timestamp.valueOf(LocalDateTime.now()));
			
			awaitingReservationService.create(awaitingReservation);
		}
		// create reservation if found

		// create awaiting reservation if not found

	}

}
