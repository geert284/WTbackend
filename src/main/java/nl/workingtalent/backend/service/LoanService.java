package nl.workingtalent.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.workingtalent.backend.entity.Loan;
import nl.workingtalent.backend.repository.ILoanRepository;

@Service
public class LoanService {
	
	@Autowired
	private ILoanRepository repository;
	
	public List<Loan> findAllLoans() {
		return repository.findAll();
	}
}
