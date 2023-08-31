package nl.workingtalent.backend.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.workingtalent.backend.entity.Account;
import nl.workingtalent.backend.service.AccountService;

@CrossOrigin
@RestController
public class AccountController {
	

	@Autowired
	private AccountService service;
	
	@RequestMapping("account/all")
	public List<Account> getAccounts() {
		return service.findAllAccounts();
	}
	

}
