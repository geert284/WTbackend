package nl.workingtalent.backend.control;

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
import nl.workingtalent.backend.dto.CreateLoanFromReservationDto;
import nl.workingtalent.backend.dto.FinishLoanDto;
import nl.workingtalent.backend.dto.FinishedLoansDto;
import nl.workingtalent.backend.dto.LoanDto;
import nl.workingtalent.backend.dto.OpenLoansDto;
import nl.workingtalent.backend.entity.Account;
import nl.workingtalent.backend.entity.AwaitingReservation;
import nl.workingtalent.backend.entity.BookCopy;
import nl.workingtalent.backend.entity.Loan;
import nl.workingtalent.backend.entity.Reservation;
import nl.workingtalent.backend.service.AccountService;
import nl.workingtalent.backend.service.AwaitingReservationService;
import nl.workingtalent.backend.service.BookCopyService;
import nl.workingtalent.backend.service.LoanService;
import nl.workingtalent.backend.service.ReservationService;

@CrossOrigin
@RestController
public class LoanController {

	@Autowired
	private LoanService service;

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private AwaitingReservationService awaitingReservationService;

	@Autowired
	private BookCopyService bookCopyService;
	
	@Autowired
	private AccountService accountService;

	@RequestMapping("loan/all")
	public List<LoanDto> getLoans() {
		List<Loan> loans = service.findAllLoans();
		List<LoanDto> dtos = new ArrayList<>();

		loans.forEach(loan -> {
			LoanDto dto = new LoanDto();
			dto.setId(loan.getId());
			dto.setLoanDate(loan.getLoanDate());
			dto.setReturnDate(loan.getReturnDate());
			dto.setAccount_id(loan.getAccount().getId());
			dto.setBookCopy_id(loan.getBookCopy().getId());
			dtos.add(dto);
		});

		return dtos;
	}

	@RequestMapping("loan/open")
	public List<OpenLoansDto> getActiveLoans(HttpServletRequest request) {

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

		List<OpenLoansDto> dtos = new ArrayList<>();
		List<Loan> loans = service.findAllActiveLoans();

		loans.forEach(loan -> {
			OpenLoansDto dto = new OpenLoansDto();
			dto.setLoanDate(loan.getLoanDate());
			dto.setTagNumber(loan.getBookCopy().getBook().getId() + "." + loan.getBookCopy().getTagNumber());
			dto.setTitle(loan.getBookCopy().getBook().getTitle());
			dto.setLoanId(loan.getId());
			String name = loan.getAccount().getFirstName() + " " + loan.getAccount().getLastName();
			dto.setName(name);
			dtos.add(dto);
		});

		return dtos;
	}

	@RequestMapping("loan/closed")
	public List<FinishedLoansDto> getFinishedLoans(HttpServletRequest request) {
		
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
		
		List<FinishedLoansDto> dtos = new ArrayList<>();
		List<Loan> loans = service.findAllFinishedLoans();

		loans.forEach(loan -> {
			FinishedLoansDto dto = new FinishedLoansDto();
			dto.setLoanDate(loan.getLoanDate());
			dto.setTagNumber(loan.getBookCopy().getBook().getId() + "." + loan.getBookCopy().getTagNumber());
			dto.setTitle(loan.getBookCopy().getBook().getTitle());
			String name = loan.getAccount().getFirstName() + " " + loan.getAccount().getLastName();
			dto.setName(name);
			dto.setReturnDate(loan.getReturnDate());

			dtos.add(dto);
		});

		return dtos;
	}

	@PostMapping("loan/fromres")
	public void createLoanFromReservation(HttpServletRequest request,
			@RequestBody CreateLoanFromReservationDto loanFromResDto) {
		
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
		
		Optional<Reservation> opReservation = reservationService.findById(loanFromResDto.getReservationId());
		if (opReservation.isEmpty()) {
			return;
		}
		Reservation reservation = opReservation.get();
		Loan loan = new Loan();
		loan.setAccount(reservation.getAccount());
		loan.setBookCopy(reservation.getBookCopy());
		loan.setLoanDate(LocalDateTime.now());

		service.create(loan);

		reservation.setProcessed(true);
		reservationService.update(reservation);
	}

	@PostMapping("loan/finish")
	public void finishLoan(HttpServletRequest request, @RequestBody FinishLoanDto loandto) {
		
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
		
		Optional<Loan> opLoan = service.findById(loandto.getLoanId());
		if (opLoan.isEmpty()) {
			return;
		}
		// change loan to processed
		Loan loan = opLoan.get();
		loan.setReturnDate(LocalDateTime.now());
		service.update(loan);

		// change bookCopy to be available
		BookCopy bookCopy = loan.getBookCopy();
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

		Reservation reservation = new Reservation();
		reservation.setAccount(awaitingReservation.getAccount());
		reservation.setBookCopy(bookCopy);
		reservation.setProcessed(false);
		reservation.setReservationDate(LocalDateTime.now());
		reservationService.update(reservation);

		bookCopy.setAvailable(false);
		bookCopyService.update(bookCopy);
	}

}
