package nl.workingtalent.backend.control;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.workingtalent.backend.dto.FinishedLoansDto;
import nl.workingtalent.backend.dto.LoanDto;
import nl.workingtalent.backend.dto.OpenLoansDto;
import nl.workingtalent.backend.entity.Loan;
import nl.workingtalent.backend.service.LoanService;

@CrossOrigin
@RestController
public class LoanController {

	@Autowired
	private LoanService service;

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
	public List<OpenLoansDto> getActiveLoans() {
		List<OpenLoansDto> dtos = new ArrayList<>();
		List<Loan> loans = service.findAllActiveLoans();
		
		loans.forEach(loan -> {
			OpenLoansDto dto = new OpenLoansDto();
			dto.setLoanDate(loan.getLoanDate());
			dto.setTagNumber(loan.getBookCopy().getTagNumber());
			dto.setTitle(loan.getBookCopy().getBook().getTitle());
			String name = loan.getAccount().getFirstName() + " " + loan.getAccount().getLastName();
			dto.setName(name);
			dtos.add(dto);
		});
		
		
		return dtos;
	}
	
	@RequestMapping("loan/closed")
	public List<FinishedLoansDto> getFinishedLoans() {
		List<FinishedLoansDto> dtos = new ArrayList<>();
		List<Loan> loans = service.findAllFinishedLoans();
		
		loans.forEach(loan -> {
			FinishedLoansDto dto = new FinishedLoansDto();
			dto.setLoanDate(loan.getLoanDate());
			dto.setTagNumber(loan.getBookCopy().getTagNumber());
			dto.setTitle(loan.getBookCopy().getBook().getTitle());
			String name = loan.getAccount().getFirstName() + " " + loan.getAccount().getLastName();
			dto.setName(name);
			dto.setReturnDate(loan.getReturnDate());
			
			dtos.add(dto);
		});
		
		
		return dtos;
	}
	

}
