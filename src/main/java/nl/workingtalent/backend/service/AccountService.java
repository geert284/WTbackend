package nl.workingtalent.backend.service;

import java.util.List;

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
}
