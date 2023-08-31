package nl.workingtalent.backend.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.workingtalent.backend.entity.Loan;
import nl.workingtalent.backend.service.LoanService;

@CrossOrigin
@RestController
public class LoanController {
	
	@Autowired
	private LoanService service;
	
	@RequestMapping("loan/all")
	public List<Loan> getLoans() {
		return service.findAllLoans();
	}

}
