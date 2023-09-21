package nl.workingtalent.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.workingtalent.backend.entity.Book;
import nl.workingtalent.backend.entity.Loan;
import nl.workingtalent.backend.entity.Reservation;
import nl.workingtalent.backend.repository.ILoanRepository;

@Service
public class LoanService {
	
	@Autowired
	private ILoanRepository repository;
	
	public List<Loan> findAllLoans() {
		return repository.findAll();
	}
	
	public List<Loan> findAllActiveLoans(){
		return repository.findByReturnDateIsNull();
	}
	
	public List<Loan> findAllFinishedLoans(){
		return repository.findByReturnDateIsNotNull();
	}
	
	public void create(Loan loan) {
		repository.save(loan);
	}
	
	public void update(Loan loan) {
		repository.save(loan);
	}
	
	public Optional<Loan> findById(long id){
		return repository.findById(id);
	}
	
	public List<Loan> findAllActiveForAccount(long id){
		return repository.findByAccountIdAndReturnDateIsNull(id);
	}
	
	public List<Loan> findAllInActiveForAccount(long id){
		return repository.findByAccountIdAndReturnDateIsNotNull(id);
	}
}
