package nl.workingtalent.backend.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.BCrypt.Result;
import nl.workingtalent.backend.dto.AccountDto;
import nl.workingtalent.backend.dto.LoginRequestDto;
import nl.workingtalent.backend.dto.LoginResponseDto;
import nl.workingtalent.backend.dto.SaveAccountDto;
import nl.workingtalent.backend.dto.SavePersonalInfoDto;
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
			accountDto.setPassword(account.getPassword());

			dtos.add(accountDto);
		});

		return dtos;
	}
	
	@RequestMapping(value="account/create", method=RequestMethod.POST)
	public void createUser(@RequestBody SaveAccountDto saveAccountDto) {
		Account account = new Account();
		account.setEmail(saveAccountDto.getEmail());
		account.setAdmin(saveAccountDto.isAdmin());
		account.setPassword(encryptPassword("WTRegister_123"));
		service.create(account);
		
	}
	
	
//	// Use POST instead ?
	@RequestMapping(value="account/saveInfo/{id}", method=RequestMethod.POST)
	public void saveInfo(@PathVariable long id, @RequestBody SavePersonalInfoDto dto) {
		Optional<Account> optionalAccount = service.findById(id);
		if (optionalAccount.isEmpty()) {
			return;
		}
		Account account = optionalAccount.get();
		
		account.setFirstName(dto.getFirstName());
		account.setLastName(dto.getLastName());
		account.setPassword(encryptPassword(dto.getPassword()));
		service.save(account);
		
	}
	
	
	@PostMapping("account/login")
	public LoginResponseDto login(@RequestBody LoginRequestDto dto) {
		Optional<Account> optionalAccount = service.findByEmail(dto.getEmail());
		if (optionalAccount.isEmpty()) {
			LoginResponseDto responseDto = new LoginResponseDto();
			responseDto.setSuccess(false);
			return responseDto;
		}

		Account account = optionalAccount.get();

		// Verificatie
		Result result = BCrypt.verifyer().verify(dto.getPassword().toCharArray(), account.getPassword());
		if (result.verified) {
			// Token aanmaken
			String newToken = generateToken();

			// Token opslaan in de database
			account.setToken(newToken);

			service.create(account);

			LoginResponseDto responseDto = new LoginResponseDto();
			responseDto.setSuccess(true);
			responseDto.setAdmin(account.isAdmin());
			responseDto.setLastName(account.getLastName());
			responseDto.setFirstName(account.getFirstName());
			responseDto.setId(account.getId());
			responseDto.setToken(newToken);
			return responseDto;

		}

		LoginResponseDto responseDto = new LoginResponseDto();
		responseDto.setSuccess(false);
		return responseDto;
	}

	public String generateToken() {
		int leftLimit = 97; // letter 'a'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 50;
	    Random random = new Random();

	    String generatedString = random.ints(leftLimit, rightLimit + 1)
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();

	    return generatedString;
	}
	
	
	public String encryptPassword(String myPassword) {
		String pwHash = BCrypt.withDefaults().hashToString(12, myPassword.toCharArray());
		return pwHash;
	}
	
}












