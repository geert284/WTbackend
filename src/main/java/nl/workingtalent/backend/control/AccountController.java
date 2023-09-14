package nl.workingtalent.backend.control;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nl.workingtalent.backend.dto.AccountDto;
import nl.workingtalent.backend.dto.SaveAccountDto;
import nl.workingtalent.backend.entity.Account;
import nl.workingtalent.backend.service.AccountService;

@CrossOrigin
@RestController
public class AccountController {

	@Autowired
	private AccountService service;

	@RequestMapping("account/all")
	public List<AccountDto> getAccounts() {
		List<Account> accounts = service.findAllAccounts();

		List<AccountDto> dtos = new ArrayList<>();

		accounts.forEach(account -> {
			AccountDto accountDto = new AccountDto();
			accountDto.setId(account.getId());
			accountDto.setEmail(account.getEmail());
			accountDto.setFirstName(account.getFirstName());
			accountDto.setLastName(account.getLastName());
			accountDto.setAdmin(account.isAdmin());

			dtos.add(accountDto);
		});

		return dtos;
	}
	
	@RequestMapping(value="account/create", method=RequestMethod.POST)
	public void createUser(@RequestBody SaveAccountDto saveAccountDto) {
		Account account = new Account();
		account.setEmail(saveAccountDto.getEmail());
		account.setAdmin(saveAccountDto.isAdmin());
		
		service.create(account);
		
	}
}












