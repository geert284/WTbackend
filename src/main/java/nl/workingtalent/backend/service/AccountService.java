package nl.workingtalent.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.workingtalent.backend.entity.Account;
import nl.workingtalent.backend.repository.IAccountRepository;


@Service
public class AccountService {
	
	@Autowired
	private IAccountRepository repository;
	
	public List<Account> findAllAccounts() {
		return repository.findAll();
	}

	public void create(Account account) {
		repository.save(account);
	}
	
	public void save(Account account) {
		repository.save(account);
	}

	public Optional<Account> findById(long id){
		return repository.findById(id);
	}

	public Optional<Account> findByEmail(String email){
		return repository.findByEmail(email);
	}
	
	public Optional<Account> findByToken(String token){
		return repository.findByToken(token);
	}
}
