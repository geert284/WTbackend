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
import jakarta.servlet.http.HttpServletRequest;

import nl.workingtalent.backend.dto.CancelReservationDto;
import nl.workingtalent.backend.dto.CreateReservationDto;
import nl.workingtalent.backend.dto.ReservationDto;
import nl.workingtalent.backend.dto.ReservationHistoryDto;
import nl.workingtalent.backend.dto.UnprocessedReservationsDto;
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
	public void createReservation(HttpServletRequest request, @RequestBody CreateReservationDto dto) {

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

		Optional<Book> opBook = bookService.findById(dto.getBookId());
		if (opBook.isEmpty()) {
			return;
		}

		// find available bookCopy with correct id
		Optional<BookCopy> opBookCopy = bookCopyService.findFirstAvailableBookCopy(opBook.get());

		// If there is no available copy, make an awaiting reservation, if there is,
		// reserve that book
		if (opBookCopy.isEmpty()) {
			AwaitingReservation awaitingReservation = new AwaitingReservation();

			awaitingReservation.setAccount(account);
			awaitingReservation.setBook(opBook.get());
			awaitingReservation.setProcessed(false);
			awaitingReservation.setRequestDate(LocalDateTime.now());

			awaitingReservationService.create(awaitingReservation);
		} else {
			Reservation reservation = new Reservation();
			BookCopy bookCopy = opBookCopy.get();

			reservation.setAccount(account);
			reservation.setBookCopy(bookCopy);
			reservation.setProcessed(false);
			reservation.setReservationDate(LocalDateTime.now());

			// change bookcopy.available to false
			bookCopy.setAvailable(false);
			bookCopyService.update(bookCopy);

			service.create(reservation);
		}
	}

	@RequestMapping("reservation/unprocessed/available")
	public List<UnprocessedReservationsDto> getUnproccesedReservations(HttpServletRequest request) {

		// authentication part
		String authHeader = request.getHeader("Authorization");
		if (authHeader == null || authHeader.isBlank()) {
			System.out.println("Geen header mee gegeven");
			return null;
		}
		Optional<Account> optional = accountService.findByToken(authHeader);
		if (optional.isEmpty()) {
			System.out.println("Account niet gevonden");
			return null;
		}
		System.out.println("Account is gevonden met naam " + optional.get().getEmail());
		Account account = optional.get();
		// end authentication part
		if (!account.isAdmin()) {
			System.out.println("Account is geen admin");
			return null;
		}
		// end authentication part with admin check

		List<UnprocessedReservationsDto> dtos = new ArrayList<>();
		// find all reservations and awaitingRes which are unprocessed
		List<Reservation> reservations = service.findAllUnprocessed();

		reservations.forEach(reservation -> {
			UnprocessedReservationsDto dto = new UnprocessedReservationsDto();
			String name = reservation.getAccount().getFirstName() + " " + reservation.getAccount().getLastName();
			dto.setAccountName(name);
			dto.setReservationDate(reservation.getReservationDate());
			dto.setTagNumber(
					reservation.getBookCopy().getBook().getId() + "." + reservation.getBookCopy().getTagNumber());
			dto.setTitle(reservation.getBookCopy().getBook().getTitle());
			dto.setAvailable(true);
			dto.setId(reservation.getId());

			dtos.add(dto);
		});
		return dtos;
	}

	@RequestMapping("reservation/unprocessed/awaiting")
	public List<UnprocessedReservationsDto> getUnproccesedAwaitingReservations(HttpServletRequest request) {

		// authentication part
		String authHeader = request.getHeader("Authorization");
		if (authHeader == null || authHeader.isBlank()) {
			System.out.println("Geen header mee gegeven");
			return null;
		}
		Optional<Account> optional = accountService.findByToken(authHeader);
		if (optional.isEmpty()) {
			System.out.println("Account niet gevonden");
			return null;
		}
		System.out.println("Account is gevonden met naam " + optional.get().getEmail());
		Account account = optional.get();
		// end authentication part
		if (!account.isAdmin()) {
			System.out.println("Account is geen admin");
			return null;
		}
		// end authentication part with admin check

		List<UnprocessedReservationsDto> dtos = new ArrayList<>();
		// find all reservations and awaitingRes which are unprocessed
		List<AwaitingReservation> awaitingReservations = awaitingReservationService.findAllUnprocessed();

		awaitingReservations.forEach(reservation -> {
			UnprocessedReservationsDto dto = new UnprocessedReservationsDto();
			String name = reservation.getAccount().getFirstName() + " " + reservation.getAccount().getLastName();
			dto.setAccountName(name);
			dto.setReservationDate(reservation.getRequestDate());
			dto.setTitle(reservation.getBook().getTitle());
			dto.setAvailable(false);
			dto.setId(reservation.getId());

			dtos.add(dto);
		});

		return dtos;
	}

	@RequestMapping("reservation/processed")
	public List<ReservationHistoryDto> getProcessedReservations(HttpServletRequest request) {
		// authentication part
		String authHeader = request.getHeader("Authorization");
		if (authHeader == null || authHeader.isBlank()) {
			System.out.println("Geen header mee gegeven");
			return null;
		}
		Optional<Account> optional = accountService.findByToken(authHeader);
		if (optional.isEmpty()) {
			System.out.println("Account niet gevonden");
			return null;
		}
		System.out.println("Account is gevonden met naam " + optional.get().getEmail());
		Account account = optional.get();
		// end authentication part
		if (!account.isAdmin()) {
			System.out.println("Account is geen admin");
			return null;
		}
		// end authentication part with admin check

		List<ReservationHistoryDto> dtos = new ArrayList<>();

		List<Reservation> reservations = service.findAllProcessed();
		List<AwaitingReservation> awaitingReservations = awaitingReservationService.findAllProcessed();

		reservations.forEach(reservation -> {
			ReservationHistoryDto dto = new ReservationHistoryDto();
			String name = reservation.getAccount().getFirstName() + " " + reservation.getAccount().getLastName();
			dto.setAccountName(name);
			dto.setReservationDate(reservation.getReservationDate());
			dto.setTagNumber(
					reservation.getBookCopy().getBook().getId() + "." + reservation.getBookCopy().getTagNumber());
			dto.setTitle(reservation.getBookCopy().getBook().getTitle());
			dto.setType("Reservering van beschikbaar boek");

			dtos.add(dto);
		});

		awaitingReservations.forEach(reservation -> {
			ReservationHistoryDto dto = new ReservationHistoryDto();
			String name = reservation.getAccount().getFirstName() + " " + reservation.getAccount().getLastName();
			dto.setAccountName(name);
			dto.setReservationDate(reservation.getRequestDate());
			dto.setTitle(reservation.getBook().getTitle());
			dto.setType("Reservering van uitgeleend boek");

			dtos.add(dto);
		});

		return dtos;
	}

	@RequestMapping("reservation/cancel")
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

		// get res form db
		Optional<Reservation> opReservation = service.findById(reservationDto.getReservationId());
		if (opReservation.isEmpty()) {
			return;
		}
		Reservation reservation = opReservation.get();

		if (account != reservation.getAccount()) {
			return;
		}

		// get bookCopy from db
		BookCopy bookCopy = reservation.getBookCopy();

		// set res to processed
		reservation.setProcessed(true);
		service.update(reservation);

		// set bookCopy to available
		bookCopy.setAvailable(true);
		bookCopyService.update(bookCopy);

		// check if there is a awaiting reservation, if there is create reservation
		Optional<AwaitingReservation> opAwaitingRes = awaitingReservationService
				.findFirstByBook(bookCopy.getBook().getId());
		if (opAwaitingRes.isEmpty()) {
			return;
		}
		AwaitingReservation awaitingReservation = opAwaitingRes.get();
		awaitingReservation.setProcessed(true);
		awaitingReservationService.update(awaitingReservation);

		Reservation newReservation = new Reservation();
		newReservation.setAccount(awaitingReservation.getAccount());
		newReservation.setBookCopy(bookCopy);
		newReservation.setProcessed(false);
		newReservation.setReservationDate(LocalDateTime.now());
		service.update(newReservation);

		bookCopy.setAvailable(false);
		bookCopyService.update(bookCopy);
	}

	@RequestMapping("reservation/canceladmin")
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
		
		// get res form db
		Optional<Reservation> opReservation = service.findById(reservationDto.getReservationId());
		if (opReservation.isEmpty()) {
			System.out.println("Geen res gevonden");
			return;
		}
		Reservation reservation = opReservation.get();

		// get bookCopy from db
		BookCopy bookCopy = reservation.getBookCopy();

		// set res to processed
		reservation.setProcessed(true);
		service.update(reservation);

		// set bookCopy to available
		bookCopy.setAvailable(true);
		bookCopyService.update(bookCopy);

		// check if there is a awaiting reservation, if there is create reservation
		Optional<AwaitingReservation> opAwaitingRes = awaitingReservationService
				.findFirstByBook(bookCopy.getBook().getId());
		if (opAwaitingRes.isEmpty()) {
			System.out.println("Geen awaiting res gevonden");
			return;
		}
		AwaitingReservation awaitingReservation = opAwaitingRes.get();
		awaitingReservation.setProcessed(true);
		awaitingReservationService.update(awaitingReservation);

		Reservation newReservation = new Reservation();
		newReservation.setAccount(awaitingReservation.getAccount());
		newReservation.setBookCopy(bookCopy);
		newReservation.setProcessed(false);
		newReservation.setReservationDate(LocalDateTime.now());
		service.update(newReservation);

		bookCopy.setAvailable(false);
		bookCopyService.update(bookCopy);
	}

}
