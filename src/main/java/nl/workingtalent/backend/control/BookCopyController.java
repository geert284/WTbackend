package nl.workingtalent.backend.control;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import nl.workingtalent.backend.dto.BookCopyDto;
import nl.workingtalent.backend.dto.BookCopyUpdateDto;
import nl.workingtalent.backend.entity.Account;
import nl.workingtalent.backend.service.AccountService;
import nl.workingtalent.backend.entity.AwaitingReservation;
import nl.workingtalent.backend.entity.Book;
import nl.workingtalent.backend.entity.BookCopy;
import nl.workingtalent.backend.entity.Reservation;
import nl.workingtalent.backend.service.AwaitingReservationService;

import nl.workingtalent.backend.service.BookCopyService;
import nl.workingtalent.backend.service.BookService;
import nl.workingtalent.backend.service.ReservationService;

@CrossOrigin
@RestController
public class BookCopyController {

	@Autowired
	private BookCopyService service;

	@Autowired
	private BookService bookService;
	
	@Autowired
	private AccountService accountService;

	private AwaitingReservationService awaitingReservationService;
	
	@Autowired
	private ReservationService reservationService;


	@RequestMapping("bookcopy/all")
	public List<BookCopyDto> getBookCopys(HttpServletRequest request) {
		// authentication part
		String authHeader = request.getHeader("Authorization");
		if (authHeader == null || authHeader.isBlank()) {
			System.out.println("Geen header meegegeven");
			return null;
		}
		Optional<Account> optional = accountService.findByToken(authHeader);
		if (optional.isEmpty()) {
			System.out.println("Account niet gevonden");
			return null;
		}
		System.out.println("Account is gevonden met naam " + optional.get().getEmail());
		Account thisAccount = optional.get();
		// end authentication part
		if (!thisAccount.isAdmin()) {
			System.out.println("Account is geen admin");
			return null;
		}
		// end authentication part with admin check
		
		List<BookCopy> bookcopys = service.findAllBookCopys();
		List<BookCopyDto> dtos = new ArrayList<>();

		bookcopys.forEach(bookcopy -> {
			BookCopyDto bookcopyDto = new BookCopyDto();
			bookcopyDto.setStatus(bookcopy.getStatus());
			bookcopyDto.setAvailable(bookcopy.isAvailable());
			bookcopyDto.setBookId(bookcopy.getBook().getId());
			bookcopyDto.setId(bookcopy.getId());
			bookcopyDto.setOutOfUse(bookcopy.isOutOfUse());
			bookcopyDto.setTagNumber(bookcopy.getTagNumber());

			dtos.add(bookcopyDto);
		});

		return dtos;
	}

	@RequestMapping("bookcopy/{id}")
	public Optional<BookCopy> findBookCopy(@PathVariable long id) {
		return service.findById(id);
	}

	@RequestMapping(value = "bookcopy/create", method = RequestMethod.POST)
	// @PostMapping("book/create")
	public void createBook(HttpServletRequest request, @RequestBody BookCopyDto bookcopyDto) {
		// authentication part
		String authHeader = request.getHeader("Authorization");
		if (authHeader == null || authHeader.isBlank()) {
			System.out.println("Geen header meegegeven");
			return;
		}
		Optional<Account> optional = accountService.findByToken(authHeader);
		if (optional.isEmpty()) {
			System.out.println("Account niet gevonden");
			return;
		}
		System.out.println("Account is gevonden met naam " + optional.get().getEmail());
		Account thisAccount = optional.get();
		// end authentication part
		if (!thisAccount.isAdmin()) {
			System.out.println("Account is geen admin");
			return;
		}
		// end authentication part with admin check
				
		// SavebookDTo controller toevoegen
		BookCopy bookCopy = new BookCopy();

		Optional<BookCopy> OpHighestBookCopy = service.findHighestBookCopyByTagNumberDesc(bookcopyDto.getBookId());
		if (OpHighestBookCopy.isEmpty()) {
			bookCopy.setTagNumber(1);
		} else {
			bookCopy.setTagNumber(OpHighestBookCopy.get().getTagNumber() + 1);
		}

		bookCopy.setStatus(bookcopyDto.getStatus());
		bookCopy.setAvailable(bookcopyDto.isAvailable());
		bookCopy.setOutOfUse(bookcopyDto.isOutOfUse());
		Optional<Book> opBook = bookService.findById(bookcopyDto.getBookId());
		if (opBook.isEmpty()) {
			return;
		}
		bookCopy.setBook(opBook.get());

		service.create(bookCopy);

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
		reservationService.update(newReservation);

		bookCopy.setAvailable(false);
		service.update(bookCopy);

	}

	// It also needs to check if it has a child row
	@PostMapping("bookcopy/delete/{id}")
	public void deleteBook(@PathVariable long id) {

		service.deleteBookCopy(id);
	}

	@RequestMapping(method = RequestMethod.POST, value = "bookcopy/update/{id}")
	public void updateBook(HttpServletRequest request, @PathVariable long id, @RequestBody BookCopyUpdateDto bookCopyUpdateDto) {
		// authentication part
		String authHeader = request.getHeader("Authorization");
		if (authHeader == null || authHeader.isBlank()) {
			System.out.println("Geen header meegegeven");
			return;
		}
		Optional<Account> optional = accountService.findByToken(authHeader);
		if (optional.isEmpty()) {
			System.out.println("Account niet gevonden");
			return;
		}
		System.out.println("Account is gevonden met naam " + optional.get().getEmail());
		Account thisAccount = optional.get();
		// end authentication part
		if (!thisAccount.isAdmin()) {
			System.out.println("Account is geen admin");
			return;
		}
		// end authentication part with admin check
		
		Optional<BookCopy> optionalCopy = service.findById(id);
		if (optionalCopy.isEmpty()) {
			return;
		}
		BookCopy dbBookCopy = optionalCopy.get();

		dbBookCopy.setStatus(bookCopyUpdateDto.getStatus());
		dbBookCopy.setAvailable(bookCopyUpdateDto.isAvailable());
		dbBookCopy.setOutOfUse(bookCopyUpdateDto.isOutOfUse());

		service.update(dbBookCopy);

	}

}
